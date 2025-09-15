package com.gpbapp.metadataregistry.service;

import com.gpbapp.metadataregistry.common.MetadataKey;
import com.gpbapp.metadataregistry.common.TableColumnMetadata;
import com.gpbapp.metadataregistry.dto.MetadataColumnDTO;
import com.gpbapp.metadataregistry.dto.MetadataTableDto;
import com.gpbapp.metadataregistry.pepository.MetadataJdbcRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class MetadataCacheService {
    private static final Logger log = LoggerFactory.getLogger(MetadataCacheService.class);

    private final MetadataJdbcRepository metadataJdbcRepository;

    public MetadataCacheService(MetadataJdbcRepository metadataJdbcRepository) {
        this.metadataJdbcRepository = metadataJdbcRepository;
    }

    public void refreshTableMetadataCache() {
        log.info("Starting metadata cache refresh");

        // Верхнеуровневая мапа по dataSource
        Map<String, Map<MetadataKey, MetadataColumnDTO>> cacheByDataSource = new HashMap<>();

        try {
            TableColumnMetadata tableColumnMetadata = metadataJdbcRepository.findTableColumnMetadata();

            for (MetadataTableDto table : tableColumnMetadata.getDataSource()) {
                // Получаем или создаем мапу для текущего dataSource
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

            log.info("Metadata cache refreshed successfully. Total data sources: {}", cacheByDataSource.size());
        } catch (Exception e) {
            log.error("Failed to refresh metadata cache", e);
        }
    }
}
