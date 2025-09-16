package com.gpbapp.metadataregistry.service.impl;

import com.gpbapp.metadataregistry.common.MetadataKey;
import com.gpbapp.metadataregistry.dto.MetadataColumnDTO;
import com.gpbapp.metadataregistry.enums.OrdaBaseType;
import com.gpbapp.metadataregistry.service.MetadataCacheService;
import com.gpbapp.metadataregistry.service.OrdaSyncStrategy;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class OracleOrdaSyncStrategy implements OrdaSyncStrategy {
    private final MetadataCacheService metadataCacheService;

    public OracleOrdaSyncStrategy(MetadataCacheService metadataCacheService) {
        this.metadataCacheService = metadataCacheService;
    }

    @Override
    public OrdaBaseType getType() {
        return OrdaBaseType.ORACLE;
    }

    @Override
    public void sync() {
        Map<String, Map<MetadataKey, MetadataColumnDTO>> cache =
                metadataCacheService.getMetadataCacheByDbType(OrdaBaseType.ORACLE);
        System.out.println("Synced Oracle metadata, total sources: " + cache.size());
    }
}
