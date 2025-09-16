package com.gpbapp.metadataregistry.service.impl;

import com.gpbapp.metadataregistry.common.MetadataKey;
import com.gpbapp.metadataregistry.common.TableColumnMetadata;
import com.gpbapp.metadataregistry.dto.MetadataColumnDTO;
import com.gpbapp.metadataregistry.dto.MetadataTableDto;
import com.gpbapp.metadataregistry.enums.OrdaBaseType;
import com.gpbapp.metadataregistry.pepository.MetadataJdbcRepository;

import com.gpbapp.metadataregistry.properties.MetadataSchemasProperties;
import com.gpbapp.metadataregistry.service.MetadataCacheService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@Service
public class MetadataCacheServiceImpl implements MetadataCacheService {
    private static final Logger log = LoggerFactory.getLogger(MetadataCacheServiceImpl.class);

    private final MetadataJdbcRepository metadataJdbcRepository;
    private final MetadataSchemasProperties schemasProperties;

    public MetadataCacheServiceImpl(MetadataJdbcRepository metadataJdbcRepository, MetadataSchemasProperties schemasProperties) {
        this.metadataJdbcRepository = metadataJdbcRepository;
        this.schemasProperties = schemasProperties;
    }

    /**
     * Загружает метаданные из указанной схемы и возвращает кэш.
     *
     * @param type тип базы данных для синхронизации с Orda (например POSTGRES, MSSQL, ORACLE)
     * @return кэш в формате:
     * dataSource -> (MetadataKey -> MetadataColumnDTO)
     */
    public Map<String, Map<MetadataKey, MetadataColumnDTO>> getMetadataCacheByDbType(OrdaBaseType type) {
        String schemaName = getSchemaName(type);
        log.info("Starting metadata cache refresh for dbType={} schema={}", type, schemaName);

        Map<String, Map<MetadataKey, MetadataColumnDTO>> cacheByDataSource = new HashMap<>();

        try {
            TableColumnMetadata tableColumnMetadata = metadataJdbcRepository.findMetadataBySchema(schemaName);

            for (MetadataTableDto table : tableColumnMetadata.getDataSource()) {
                Map<MetadataKey, MetadataColumnDTO> dataSourceCache =
                        cacheByDataSource.computeIfAbsent(table.getDataSource(), k -> new HashMap<>());

                for (MetadataColumnDTO column : table.getColumns()) {
                    MetadataKey key = new MetadataKey(
                            table.getDataSource(),
                            table.getDbName(),
                            table.getSchemaName(),
                            table.getTableName()
                    );
                    dataSourceCache.put(key, column);
                }
            }

            log.info("Metadata cache refreshed successfully for dbType={} schema={}. Total data sources: {}",
                    type, schemaName, cacheByDataSource.size());
        } catch (Exception e) {
            log.error("Failed to refresh metadata cache for dbType={} schema={}", type, schemaName, e);
            return Collections.emptyMap();
        }

        return cacheByDataSource;
    }

    public String getSchemaName(OrdaBaseType type) {
        return switch (type) {
            case POSTGRES -> schemasProperties.getPostgres();
            case MSSQL -> schemasProperties.getMssql();
            case ORACLE -> schemasProperties.getOracle();
            default -> throw new IllegalArgumentException("Unsupported OrdaBaseType: " + type);
        };
    }
}
