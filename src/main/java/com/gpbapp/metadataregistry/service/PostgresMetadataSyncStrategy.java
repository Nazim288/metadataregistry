package com.gpbapp.metadataregistry.service;

import com.gpbapp.metadataregistry.dto.metadata.DatabaseMetadataDto;
import com.gpbapp.metadataregistry.dto.metadata.SchemaMetadataDto;
import com.gpbapp.metadataregistry.dto.metadata.TableMetadataDto;
import com.gpbapp.metadataregistry.dto.orda.*;
import com.gpbapp.metadataregistry.enums.OrdaBaseType;
import com.gpbapp.metadataregistry.enums.OrdaColumnType;
import com.gpbapp.metadataregistry.properties.MetadataSchemasProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class PostgresMetadataSyncStrategy implements MetaSyncStrategy {
    private static final Logger log = LoggerFactory.getLogger(PostgresMetadataSyncStrategy.class);

    private final MetadataService metadataService;
    private final OrdaService ordaService;
    private final MetadataPgCache metadataPgCache;
    private final MetadataSchemasProperties properties;

    public PostgresMetadataSyncStrategy(MetadataService metadataService, OrdaService ordaService, MetadataPgCache metadataPgCache, MetadataSchemasProperties properties) {
        this.metadataService = metadataService;
        this.ordaService = ordaService;
        this.metadataPgCache = metadataPgCache;
        this.properties = properties;
    }

    @Override
    public OrdaBaseType getType() {
        return OrdaBaseType.POSTGRES;
    }

    @Override
    public ResponseEntity<String> sync(String source) {
        long start = System.currentTimeMillis();
        log.info("Start syncing Postgres service {} metadata with Orda...", source);
        final String postgresSchema = properties.getPostgres();

        // --- 1. Базы ---
        Map<String, DatabaseMetadataDto> freshDbs = metadataService.getAllDatabasesBySchemaAndService(postgresSchema, source);
        if (freshDbs.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Нет баз для сервиса " + source + " в схеме " + postgresSchema);
        }

        // upsert dbs
        freshDbs.values().forEach(db -> {
            DatabaseMetadataDto cached = metadataPgCache.getDatabases().get(db.getFqn());
            if (cached == null || !Objects.equals(cached.getHashData(), db.getHashData())) {
                OrdaBaseCreateDto dto = new OrdaBaseCreateDto();
                dto.setName(db.getName());
                dto.setService(db.getServiceName());

                ordaService.updateDatabase(dto);
                metadataPgCache.putDatabase(db);
                log.info("Upsert database {} in service {}", db.getName(), db.getServiceName());
            }
        });
        // delete dbs
        Set<String> freshDbFqns = freshDbs.keySet();
        Set<String> cachedDbFqns = new HashSet<>(metadataPgCache.getDatabases().keySet());
        cachedDbFqns.removeAll(freshDbFqns);
        cachedDbFqns.forEach(fqn -> {
            ordaService.deleteDatabaseSoftRecursive(fqn);
            metadataPgCache.getDatabases().remove(fqn);
            log.info("Soft recursive delete database {}", fqn);
        });

        // --- 2. Схемы ---
        Map<String, SchemaMetadataDto> freshSchemas = metadataService.getAllSchemasBySchemaAndService(postgresSchema, source);
        freshSchemas.values().forEach(schema -> {
            SchemaMetadataDto cached = metadataPgCache.getSchemas().get(schema.getFqn());
            if (cached == null || !Objects.equals(cached.getHashData(), schema.getHashData())) {
                OrdaSchemaCreateDTO dto = new OrdaSchemaCreateDTO();
                dto.setName(schema.getName());
                dto.setDatabase(schema.getParent_fqn());

                ordaService.updateSchema(dto);
                metadataPgCache.putSchema(schema);
                log.info("Upsert schema {} in database {}", schema.getName(), schema.getDbName());
            }
        });
        Set<String> freshSchemaFqns = freshSchemas.keySet();
        Set<String> cachedSchemaFqns = new HashSet<>(metadataPgCache.getSchemas().keySet());
        cachedSchemaFqns.removeAll(freshSchemaFqns);
        cachedSchemaFqns.forEach(fqn -> {
            ordaService.deleteSchemaSoftRecursive(fqn);
            metadataPgCache.getSchemas().remove(fqn);
            log.info("Soft recursive delete schema {}", fqn);
        });

        // --- 3. Таблицы ---
        Map<String, TableMetadataDto> freshTables = metadataService.getAllTablesBySchemaAndService(postgresSchema, source);
        freshTables.values().forEach(table -> {
            TableMetadataDto cached = metadataPgCache.getTables().get(table.getFqn());
            if (cached == null || !Objects.equals(cached.getHashData(), table.getHashData())) {
                OrdaTableCreateDTO tableDto = new OrdaTableCreateDTO();
                tableDto.setName(table.getName());
                tableDto.setDatabaseSchema(table.getParentFqn());
                tableDto.setDescription(table.getDescription());

                tableDto.setColumns(
                        table.getData().getColumns().stream().map(c -> {
                            OrdaColumnCreateDto col = new OrdaColumnCreateDto();
                            col.setName(c.getFqn().substring(c.getFqn().lastIndexOf('.') + 1));
                            col.setDataType(OrdaColumnType.map(c.getDtype()));
                            col.setDataLength(c.getDataLength());
                            col.setDescription(c.getDescription());
                            return col;
                        }).toList()
                );

                ordaService.updateTable(tableDto);
                metadataPgCache.putTable(table);
                log.info("Upsert table {} in schema {}", table.getName(), table.getSchemaName());
            }
        });
        Set<String> freshTableFqns = freshTables.keySet();
        Set<String> cachedTableFqns = new HashSet<>(metadataPgCache.getTables().keySet());
        cachedTableFqns.removeAll(freshTableFqns);
        cachedTableFqns.forEach(fqn -> {
            ordaService.deleteTableSoftRecursive(fqn);
            metadataPgCache.getTables().remove(fqn);
            log.info("Soft recursive delete table {}", fqn);
        });

        int dbsCount = freshDbs.size();
        int schemasCount = freshSchemas.size();
        int tablesCount = freshTables.size();
        int servicesCount = freshDbs.values().stream()
                .map(DatabaseMetadataDto::getServiceName)
                .collect(Collectors.toSet())
                .size();

        long duration = System.currentTimeMillis() - start;
        String message = String.format(
                "Синхронизация завершена успешно для сервиса %s: сервисов=%d, баз=%d, схем=%d, таблиц=%d (время=%d мс)",
                source, servicesCount, dbsCount, schemasCount, tablesCount, duration
        );

        log.info(message);
        return ResponseEntity.ok(message);
    }
}
