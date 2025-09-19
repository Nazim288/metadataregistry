package com.gpbapp.metadataregistry.service.impl;

import com.gpbapp.metadataregistry.dto.metadata.DatabaseMetadataDto;
import com.gpbapp.metadataregistry.dto.metadata.SchemaMetadataDto;
import com.gpbapp.metadataregistry.dto.metadata.TableMetadataDto;
import com.gpbapp.metadataregistry.dto.orda.*;
import com.gpbapp.metadataregistry.enums.OrdaBaseType;
import com.gpbapp.metadataregistry.enums.OrdaColumnType;
import com.gpbapp.metadataregistry.service.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class PostgresOrdaSyncStrategy implements OrdaSyncStrategy {
    private static final Logger log = LoggerFactory.getLogger(PostgresOrdaSyncStrategy.class);

    private final MetadataService metadataService;
    private final OrdaService ordaService;
    private final OrdaCache ordaCache;

    public PostgresOrdaSyncStrategy(MetadataService metadataService,
                                    OrdaService ordaService,
                                    OrdaCache ordaCache) {
        this.metadataService = metadataService;
        this.ordaService = ordaService;
        this.ordaCache = ordaCache;
    }

    @Override
    public OrdaBaseType getType() {
        return OrdaBaseType.POSTGRES;
    }

    @Override
    public void sync() {
        log.info("Start syncing Postgres metadata to Orda...");
        Map<String, DatabaseMetadataDto> dbs = metadataService.getAllDatabasesBySchema("postgres_metadata");
         // --- сервисы сперва
        Set<String> serviceNames = dbs.values().stream()
                .map(DatabaseMetadataDto::getServiceName)
                .collect(Collectors.toSet());

        serviceNames.forEach(serviceName -> {
            if (!ordaCache.getServices().containsKey(serviceName)) {
                OrdaServiceCreateDto dto = new OrdaServiceCreateDto();
                dto.setName(serviceName);
                dto.setServiceType("Postgres");
                dto.setDescription("Auto-synced Postgres service " + serviceName);

                OrdaServiceDto created = ordaService.createService(dto);
                ordaCache.putService(created);
                log.info("Created service {}", serviceName);
            }
        });

        // --- 1. Базы ---
        dbs.values().forEach(db -> {
            if (!ordaCache.getDatabases().containsKey(db.getFqn())) {
                OrdaBaseCreateDto dto = new OrdaBaseCreateDto();
                dto.setName(db.getName());
                dto.setService(db.getServiceName());

                OrdaDbDto created = ordaService.createDatabase(dto);
                ordaCache.putDatabase(created);
                log.info("Created database {} in service {}", db.getName(), db.getServiceName());
            }
        });

        // --- 2. Схемы ---
        Map<String, SchemaMetadataDto> schemas = metadataService.getAllSchemasBySchema("postgres_metadata");
        schemas.values().forEach(schema -> {
            if (!ordaCache.getSchemas().containsKey(schema.getFqn())) {
                OrdaSchemaCreateDTO dto = new OrdaSchemaCreateDTO();
                dto.setName(schema.getName());
                dto.setDatabase(schema.getParent_fqn());

                OrdaDatabaseSchemaDto created = ordaService.createSchema(dto);
                ordaCache.putSchema(created);
                log.info("Created schema {} in database {}", schema.getName(), schema.getDbName());
            }
        });

        // --- 3. Таблицы ---
        Map<String, TableMetadataDto> tables = metadataService.getAllTablesBySchema("postgres_metadata");
        tables.values().forEach(table -> {
            if (!ordaCache.getTables().containsKey(table.getFqn())) {
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

                OrdaTableDto created = ordaService.createTable(tableDto);
                ordaCache.putTable(created);
                log.info("Created table {} in schema {}", table.getName(), table.getSchemaName());
            }
        });

        log.info(" Postgres sync finished");
    }
}

