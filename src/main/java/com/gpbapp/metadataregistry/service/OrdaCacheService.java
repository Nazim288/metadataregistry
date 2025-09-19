package com.gpbapp.metadataregistry.service;

import org.springframework.stereotype.Service;

@Service
public class OrdaCacheService {
    public OrdaCacheService(OrdaCache ordaCache) {
        this.ordaCache = ordaCache;
    }

    private final OrdaCache ordaCache;

    public String getSize(){

        int baseSize = ordaCache.getDatabases().size();
        int serviceSize = ordaCache.getServices().size();
        int schemasSize = ordaCache.getSchemas().size();
        int tablesSize = ordaCache.getTables().size();

        return String.format(
                "{ \"services\": %d, \"bases\": %d, \"schemas\": %d, \"tables\": %d }",
                serviceSize, baseSize, schemasSize, tablesSize
        );
    }

}
