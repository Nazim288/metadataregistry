package com.gpbapp.metadataregistry.service.impl;

import com.gpbapp.metadataregistry.common.MetadataKey;
import com.gpbapp.metadataregistry.dto.*;
import com.gpbapp.metadataregistry.enums.OrdaBaseType;
import com.gpbapp.metadataregistry.enums.OrdaColumnType;
import com.gpbapp.metadataregistry.service.MetadataCacheService;
import com.gpbapp.metadataregistry.service.OrdaService;
import com.gpbapp.metadataregistry.service.OrdaSyncStrategy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class PostgresOrdaSyncStrategy implements OrdaSyncStrategy {
    private static final Logger log = LoggerFactory.getLogger(PostgresOrdaSyncStrategy.class);

    private final MetadataCacheService metadataCacheService;
    private final OrdaService ordaService;

    public PostgresOrdaSyncStrategy(MetadataCacheService metadataCacheService, OrdaService ordaService) {
        this.metadataCacheService = metadataCacheService;
        this.ordaService = ordaService;
    }

    @Override
    public OrdaBaseType getType() {
        return OrdaBaseType.POSTGRES;
    }

    @Override
    public void sync() {
        Map<String, Map<MetadataKey, MetadataColumnDTO>> cache =
                metadataCacheService.getMetadataCacheByDbType(OrdaBaseType.POSTGRES);

        log.info("Start syncing Postgres metadata to Orda. DataSources={}", cache.size());

        Map<String, DatabaseDto> existingServices =
                ordaService.getServices().stream()
                        .collect(Collectors.toMap(DatabaseDto::getName, s -> s, (a, b) -> a));

        Map<String, OrdaTableDto> existingDatabases =
                ordaService.getDatabase().stream()
                        .collect(Collectors.toMap(OrdaTableDto::getName, d -> d, (a, b) -> a));

        Map<String, OrdaDatabaseSchemaDto> existingSchemas =
                ordaService.getSchema().stream()
                        .collect(Collectors.toMap(OrdaDatabaseSchemaDto::getName, s -> s, (a, b) -> a));

        for (String dataSource : cache.keySet()) {
            if (!existingServices.containsKey(dataSource)) {
                OrdaServiceCreateDto dto = new OrdaServiceCreateDto();
                dto.setName(dataSource);
                dto.setServiceType("Postgres");
                dto.setDescription("Auto-synced Postgres service " + dataSource);
                ordaService.createService(dto);
                log.info("Created service {}", dataSource);
            }
        }

        for (Map.Entry<String, Map<MetadataKey, MetadataColumnDTO>> entry : cache.entrySet()) {
            String dataSource = entry.getKey();

            Set<String> dbNames = entry.getValue().keySet().stream()
                    .map(MetadataKey::getDbName)
                    .collect(Collectors.toSet());

            for (String dbName : dbNames) {
                if (!existingDatabases.containsKey(dbName)) {
                    OrdaBaseCreateDto dto = new OrdaBaseCreateDto();
                    dto.setName(dbName);
                    dto.setService(dataSource); // сервис берём из ключа Map
                    ordaService.createDatabase(dto);
                    log.info("Created database {} in service {}", dbName, dataSource);
                }
            }
        }

        for (Map.Entry<String, Map<MetadataKey, MetadataColumnDTO>> entry : cache.entrySet()) {
            String dataSource = entry.getKey();

            Map<String, Set<String>> dbSchemas = entry.getValue().keySet().stream()
                    .collect(Collectors.groupingBy(MetadataKey::getDbName,
                            Collectors.mapping(MetadataKey::getSchemaName, Collectors.toSet())));

            for (Map.Entry<String, Set<String>> dbSchemasEntry : dbSchemas.entrySet()) {
                String dbName = dbSchemasEntry.getKey();

                for (String schemaName : dbSchemasEntry.getValue()) {
                    if (!existingSchemas.containsKey(schemaName)) {
                        OrdaSchemaCreateDTO dto = new OrdaSchemaCreateDTO();
                        dto.setName(schemaName);
                        // FQN базы: service.db
                        dto.setDatabase(dataSource + "." + dbName);

                        ordaService.createSchema(dto);
                        log.info("Created schema {} in database {}", schemaName, dbName);
                    }
                }
            }
        }

        // 4. Таблицы
        for (Map.Entry<String, Map<MetadataKey, MetadataColumnDTO>> entry : cache.entrySet()) {
            String dataSource = entry.getKey();

            // группируем колонки по таблицам
            Map<String, List<MetadataColumnDTO>> grouped =
                    entry.getValue().values().stream()
                            .collect(Collectors.groupingBy(MetadataColumnDTO::getTableName));

            for (Map.Entry<String, List<MetadataColumnDTO>> tableEntry : grouped.entrySet()) {
                String tableName = tableEntry.getKey();
                List<MetadataColumnDTO> columns = tableEntry.getValue();

                OrdaTableCreateDTO tableDto = new OrdaTableCreateDTO();
                tableDto.setName(tableName);

                String dbName = columns.getFirst().getDbName();
                String schemaName = columns.getFirst().getSchemaName();

                tableDto.setDatabaseSchema(dataSource + "." + dbName + "." + schemaName);

                tableDto.setColumns(
                        columns.stream()
                                .map(c -> {
                                    OrdaColumnCreateDto col = new OrdaColumnCreateDto();
                                    col.setName(c.getColumnName());

                                    // нормализуем тип
                                    String normalizedType = OrdaColumnType.map(c.getDataType());
                                    col.setDataType(normalizedType);

                                    // dataLength только для текстовых/двоичных типов
                                    if ("VARCHAR".equalsIgnoreCase(normalizedType)
                                            || "CHAR".equalsIgnoreCase(normalizedType)
                                            || "VARBINARY".equalsIgnoreCase(normalizedType)
                                            || "BINARY".equalsIgnoreCase(normalizedType)) {
                                        col.setDataLength(255);
                                    }

                                    return col;
                                })
                                .toList()
                );

                ordaService.createOrUpdateTable(tableDto);
                log.info("Created/Updated table {}.{} in {}", schemaName, tableName, dataSource);
            }
        }

        log.info("Postgres sync finished");
    }
}