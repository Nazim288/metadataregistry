package com.gpbapp.metadataregistry.service;

import com.gpbapp.metadataregistry.common.MetadataKey;
import com.gpbapp.metadataregistry.dto.MetadataColumnDTO;
import com.gpbapp.metadataregistry.enums.OrdaBaseType;

import java.util.Map;

public interface MetadataCacheService {
    Map<String, Map<MetadataKey, MetadataColumnDTO>> getMetadataCacheByDbType(OrdaBaseType type);
}
