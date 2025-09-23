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
    private final MetadataCache metadataCache;
    private final OrdaCache ordaCache;
    private final MetadataSchemasProperties properties;

    public PostgresMetadataSyncStrategy(MetadataService metadataService, OrdaService ordaService, MetadataCache metadataCache, OrdaCache ordaCache, MetadataSchemasProperties properties) {
        this.metadataService = metadataService;
        this.ordaService = ordaService;
        this.metadataCache = metadataCache;
        this.ordaCache = ordaCache;
        this.properties = properties;
    }

    @Override
    public OrdaBaseType getType() {
        return OrdaBaseType.POSTGRES;
    }

    @Override
    public ResponseEntity<String> sync(String source) {
        log.info(String.format("Start syncing Postgres service %s metadata with Orda...", source));
        final String postgresSchema = properties.getPostgres();

        // --- 1. Сервисы ---
        Map<String, DatabaseMetadataDto> dbs = metadataService.getAllDatabasesBySchemaAndService(postgresSchema, source);
        if (dbs.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Нет баз для сервиса " + source + " в схеме " + postgresSchema);
        }

        Set<String> serviceNames = dbs.values().stream()
                .map(DatabaseMetadataDto::getServiceName)
                .collect(Collectors.toSet());

        serviceNames.forEach(serviceName -> {
            OrdaServiceDto cached = ordaCache.getService(serviceName);
            if (cached == null) {
                OrdaServiceCreateDto dto = new OrdaServiceCreateDto();
                dto.setName(serviceName);
                dto.setServiceType(OrdaBaseType.POSTGRES.getServiceType());
                dto.setDescription("Auto-synced Postgres service " + serviceName);

                OrdaServiceDto created = ordaService.createService(dto);
                ordaCache.putService(created);
                log.info("Created service {}", serviceName);
            } else {
                log.debug("Service {} already exists, skip", serviceName);
            }
        });

        // --- 2. Базы ---
        Map<String, DatabaseMetadataDto> freshDbs = dbs;
        freshDbs.values().forEach(db -> {
            DatabaseMetadataDto cached = metadataCache.getDatabases().get(db.getFqn());
            if (cached == null || !Objects.equals(cached.getHashData(), db.getHashData())) {
                OrdaBaseCreateDto dto = new OrdaBaseCreateDto();
                dto.setName(db.getName());
                dto.setService(db.getServiceName());

                ordaService.updateDatabase(dto);
                metadataCache.putDatabase(db);
                log.info("Upsert database {} in service {}", db.getName(), db.getServiceName());
            }
        });
        // Удаление баз
        Set<String> freshDbFqns = freshDbs.keySet();
        Set<String> cachedDbFqns = new HashSet<>(metadataCache.getDatabases().keySet());
        cachedDbFqns.removeAll(freshDbFqns);
        cachedDbFqns.forEach(fqn -> {
            ordaService.deleteDatabaseSoftRecursive(fqn);
            metadataCache.getDatabases().remove(fqn);
            log.info("Soft recursive delete database {}", fqn);
        });

        // --- 3. Схемы ---
        Map<String, SchemaMetadataDto> freshSchemas = metadataService.getAllSchemasBySchemaAndService(postgresSchema, source);
        freshSchemas.values().forEach(schema -> {
            SchemaMetadataDto cached = metadataCache.getSchemas().get(schema.getFqn());
            if (cached == null || !Objects.equals(cached.getHashData(), schema.getHashData())) {
                OrdaSchemaCreateDTO dto = new OrdaSchemaCreateDTO();
                dto.setName(schema.getName());
                dto.setDatabase(schema.getParent_fqn());

                ordaService.updateSchema(dto);
                metadataCache.putSchema(schema);
                log.info("Upsert schema {} in database {}", schema.getName(), schema.getDbName());
            }
        });
        // Удаление схем
        Set<String> freshSchemaFqns = freshSchemas.keySet();
        Set<String> cachedSchemaFqns = new HashSet<>(metadataCache.getSchemas().keySet());
        cachedSchemaFqns.removeAll(freshSchemaFqns);
        cachedSchemaFqns.forEach(fqn -> {
            ordaService.deleteSchemaSoftRecursive(fqn);
            metadataCache.getSchemas().remove(fqn);
            log.info("Soft recursive delete schema {}", fqn);
        });

        // --- 4. Таблицы ---
        Map<String, TableMetadataDto> freshTables = metadataService.getAllTablesBySchemaAndService(postgresSchema, source);
        freshTables.values().forEach(table -> {
            TableMetadataDto cached = metadataCache.getTables().get(table.getFqn());
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
                metadataCache.putTable(table);
                log.info("Upsert table {} in schema {}", table.getName(), table.getSchemaName());
            }
        });
        // Удаление таблиц
        Set<String> freshTableFqns = freshTables.keySet();
        Set<String> cachedTableFqns = new HashSet<>(metadataCache.getTables().keySet());
        cachedTableFqns.removeAll(freshTableFqns);
        cachedTableFqns.forEach(fqn -> {
            ordaService.deleteTableSoftRecursive(fqn);
            metadataCache.getTables().remove(fqn);
            log.info("Soft recursive delete table {}", fqn);
        });

        int servicesCount = serviceNames.size();
        int dbsCount = freshDbs.size();
        int schemasCount = freshSchemas.size();
        int tablesCount = freshTables.size();

        String message = String.format(
                "Синхронизация завершена успешно для сервиса %s: " +
                        "сервисов=%d, баз=%d, схем=%d, таблиц=%d",
                source, servicesCount, dbsCount, schemasCount, tablesCount
        );

        log.info(message);
        return ResponseEntity.ok(message);
    }
}
