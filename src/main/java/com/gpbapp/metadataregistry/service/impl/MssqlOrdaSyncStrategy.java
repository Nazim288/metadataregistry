package com.gpbapp.metadataregistry.service.impl;

import com.gpbapp.metadataregistry.common.MetadataKey;
import com.gpbapp.metadataregistry.dto.MetadataColumnDTO;
import com.gpbapp.metadataregistry.enums.OrdaBaseType;
import com.gpbapp.metadataregistry.service.MetadataCacheService;
import com.gpbapp.metadataregistry.service.OrdaSyncStrategy;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class MssqlOrdaSyncStrategy implements OrdaSyncStrategy {
    private final MetadataCacheService metadataCacheService;

    public MssqlOrdaSyncStrategy(MetadataCacheService metadataCacheService) {
        this.metadataCacheService = metadataCacheService;
    }

    @Override
    public OrdaBaseType getType() {
        return OrdaBaseType.MSSQL;
    }

    @Override
    public void sync() {
        Map<String, Map<MetadataKey, MetadataColumnDTO>> cache =
                metadataCacheService.getMetadataCacheByDbType(OrdaBaseType.MSSQL);
        // логика отправки в Orda
        System.out.println("Synced MSSQL metadata, total sources: " + cache.size());
    }
}
