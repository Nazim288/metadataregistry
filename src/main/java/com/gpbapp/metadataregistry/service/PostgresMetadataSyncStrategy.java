package com.gpbapp.metadataregistry.service;

import com.gpbapp.metadataregistry.dto.metadata.DatabaseMetadataDto;
import com.gpbapp.metadataregistry.dto.metadata.MetadataCacheDto;
import com.gpbapp.metadataregistry.dto.metadata.SchemaMetadataDto;
import com.gpbapp.metadataregistry.dto.metadata.TableMetadataDto;
import com.gpbapp.metadataregistry.dto.orda.*;
import com.gpbapp.metadataregistry.enums.ObjectCacheType;
import com.gpbapp.metadataregistry.enums.OrdaBaseType;
import com.gpbapp.metadataregistry.enums.OrdaColumnType;
import com.gpbapp.metadataregistry.pepository.MetadataCacheRepository;
import com.gpbapp.metadataregistry.properties.MetadataSchemasProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.*;
import java.util.function.Consumer;
import java.util.stream.Collectors;

@Service
public class PostgresMetadataSyncStrategy implements MetaSyncStrategy {
    private static final Logger log = LoggerFactory.getLogger(PostgresMetadataSyncStrategy.class);

    private final MetadataService metadataService;
    private final OrdaService ordaService;
    private final MetadataPgCache metadataPgCache;
    private final MetadataSchemasProperties schemasProperties;
    private final MetadataCacheRepository metadataCacheRepository;

    public PostgresMetadataSyncStrategy(MetadataService metadataService,
                                        OrdaService ordaService,
                                        MetadataPgCache metadataPgCache,
                                        MetadataSchemasProperties schemasProperties,
                                        MetadataCacheRepository metadataCacheRepository) {
        this.metadataService = metadataService;
        this.ordaService = ordaService;
        this.metadataPgCache = metadataPgCache;
        this.schemasProperties = schemasProperties;
        this.metadataCacheRepository = metadataCacheRepository;
    }

    @Override
    public OrdaBaseType getType() {
        return OrdaBaseType.POSTGRES;
    }

    @Override
    public ResponseEntity<String> sync(String source) {
        long start = System.currentTimeMillis();
        log.info("Start syncing Postgres service {} metadata with Orda...", source);
        final String postgresSchema = schemasProperties.getPostgres();

        List<MetadataCacheDto> toUpsertCache = new ArrayList<>();
        List<String> toDeleteCache = new ArrayList<>();

        // шаги синхронизации
        Map<String, DatabaseMetadataDto> freshDbs =
                syncDatabases(postgresSchema, source, toUpsertCache, toDeleteCache);
        Map<String, SchemaMetadataDto> freshSchemas =
                syncSchemas(postgresSchema, source, toUpsertCache, toDeleteCache);
        Map<String, TableMetadataDto> freshTables =
                syncTables(postgresSchema, source, toUpsertCache, toDeleteCache);

        // применяем изменения в кэше
        applyCacheChanges(toUpsertCache, toDeleteCache, postgresSchema);

        String message = buildResultMessage(source, freshDbs, freshSchemas, freshTables, start);
        log.info(message);
        return ResponseEntity.ok(message);
    }

    // --- Databases ---
    private Map<String, DatabaseMetadataDto> syncDatabases(
            String schema, String source,
            List<MetadataCacheDto> toUpsert, List<String> toDelete
    ) {
        Map<String, DatabaseMetadataDto> freshDbs =
                metadataService.getAllDatabasesBySchemaAndService(schema, source);

        if (freshDbs.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    "Нет баз для сервиса " + source + " в схеме " + schema);
        }

        // upsert
        freshDbs.values().forEach(db -> {
            DatabaseMetadataDto cached = metadataPgCache.getDatabases().get(db.getFqn());
            if (cached == null || !Objects.equals(cached.getHashData(), db.getHashData())) {
                OrdaBaseCreateDto dto = new OrdaBaseCreateDto(db.getName(), db.getServiceName());
                ordaService.updateDatabase(dto);
                metadataPgCache.putDatabase(db);
                log.info("Upsert database {} in service {}", db.getName(), db.getServiceName());
                toUpsert.add(new MetadataCacheDto(db.getFqn(),
                        ObjectCacheType.DATABASE, db.getServiceName(), db.getHashData()));
            }
        });

        // delete
        cleanupStaleEntries(freshDbs.keySet(), metadataPgCache.getDatabases(), fqn -> {
            ordaService.deleteDatabaseSoftRecursive(fqn);
            log.info("Soft recursive delete database {}", fqn);
            toDelete.add(fqn);
        });

        return freshDbs;
    }

    // --- Schemas ---
    private Map<String, SchemaMetadataDto> syncSchemas(
            String schema, String source,
            List<MetadataCacheDto> toUpsert, List<String> toDelete
    ) {
        Map<String, SchemaMetadataDto> freshSchemas =
                metadataService.getAllSchemasBySchemaAndService(schema, source);

        freshSchemas.values().forEach(sc -> {
            SchemaMetadataDto cached = metadataPgCache.getSchemas().get(sc.getFqn());
            if (cached == null || !Objects.equals(cached.getHashData(), sc.getHashData())) {
                OrdaSchemaCreateDTO dto = new OrdaSchemaCreateDTO(sc.getName(), sc.getParent_fqn());
                ordaService.updateSchema(dto);
                metadataPgCache.putSchema(sc);
                log.info("Upsert schema {} in database {}", sc.getName(), sc.getDbName());
                toUpsert.add(new MetadataCacheDto(sc.getFqn(),
                        ObjectCacheType.SCHEMA, sc.getServiceName(), sc.getHashData()));
            }
        });

        cleanupStaleEntries(freshSchemas.keySet(), metadataPgCache.getSchemas(), fqn -> {
            ordaService.deleteSchemaSoftRecursive(fqn);
            log.info("Soft recursive delete schema {}", fqn);
            toDelete.add(fqn);
        });

        return freshSchemas;
    }

    // --- Tables ---
    private Map<String, TableMetadataDto> syncTables(
            String schema, String source,
            List<MetadataCacheDto> toUpsert, List<String> toDelete
    ) {
        Map<String, TableMetadataDto> freshTables =
                metadataService.getAllTablesBySchemaAndService(schema, source);

        freshTables.values().forEach(tbl -> {
            TableMetadataDto cached = metadataPgCache.getTables().get(tbl.getFqn());
            if (cached == null || !Objects.equals(cached.getHashData(), tbl.getHashData())) {
                OrdaTableCreateDTO tableDto = buildTableDto(tbl);
                ordaService.updateTable(tableDto);
                metadataPgCache.putTable(tbl);
                log.info("Upsert table {} in schema {}", tbl.getName(), tbl.getSchemaName());
                toUpsert.add(new MetadataCacheDto(tbl.getFqn(),
                        ObjectCacheType.TABLE, tbl.getServiceName(), tbl.getHashData()));
            }
        });

        cleanupStaleEntries(freshTables.keySet(), metadataPgCache.getTables(), fqn -> {
            ordaService.deleteTableSoftRecursive(fqn);
            log.info("Soft recursive delete table {}", fqn);
            toDelete.add(fqn);
        });

        return freshTables;
    }

    private OrdaTableCreateDTO buildTableDto(TableMetadataDto tbl) {
        OrdaTableCreateDTO dto = new OrdaTableCreateDTO();
        dto.setName(tbl.getName());
        dto.setDatabaseSchema(tbl.getParentFqn());
        dto.setDescription(tbl.getDescription());

        dto.setColumns(tbl.getData().getColumns().stream()
                .map(c -> {
                    OrdaColumnCreateDto col = new OrdaColumnCreateDto();
                    col.setName(c.getName()); // короткое имя
                    col.setDataType(OrdaColumnType.map(c.getDataType()));
                    col.setConstraint(c.getNullable() != null && !c.getNullable() ? "NOT_NULL" : "NULL");
                    col.setDescription(c.getDescription());
                    col.setDataLength(c.getDataLength() != null ? c.getDataLength() : 0);

                    return col;
                })
                .toList()
        );

        return dto;
    }

    // --- Общие утилиты ---
    private <T> void cleanupStaleEntries(
            Set<String> freshFqns,
            Map<String, T> cachedMap,
            Consumer<String> deleteAction
    ) {
        Set<String> staleFqns = new HashSet<>(cachedMap.keySet());
        staleFqns.removeAll(freshFqns);
        staleFqns.forEach(fqn -> {
            cachedMap.remove(fqn);
            deleteAction.accept(fqn);
        });
    }

    private void applyCacheChanges(List<MetadataCacheDto> toUpsert, List<String> toDelete, String postgresSchema) {
        if (!toUpsert.isEmpty()) {
            metadataCacheRepository.batchUpsert(postgresSchema, toUpsert);
            log.info("Upsert {} records into metadata_cache", toUpsert.size());
        }
        if (!toDelete.isEmpty()) {
            metadataCacheRepository.batchDelete(postgresSchema, toDelete);
            log.info("Delete {} records from metadata_cache", toDelete.size());
        }
    }

    private String buildResultMessage(String source,
                                      Map<String, DatabaseMetadataDto> freshDbs,
                                      Map<String, SchemaMetadataDto> freshSchemas,
                                      Map<String, TableMetadataDto> freshTables,
                                      long start) {
        int dbsCount = freshDbs.size();
        int schemasCount = freshSchemas.size();
        int tablesCount = freshTables.size();
        int servicesCount = freshDbs.values().stream()
                .map(DatabaseMetadataDto::getServiceName)
                .collect(Collectors.toSet())
                .size();

        long duration = System.currentTimeMillis() - start;
        return String.format(
                "Синхронизация завершена успешно для сервиса %s: сервисов=%d, баз=%d, схем=%d, таблиц=%d (время=%d мс)",
                source, servicesCount, dbsCount, schemasCount, tablesCount, duration
        );
    }
}