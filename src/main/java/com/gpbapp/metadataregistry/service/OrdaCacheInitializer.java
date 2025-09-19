package com.gpbapp.metadataregistry.service;

import com.gpbapp.metadataregistry.dto.orda.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class OrdaCacheInitializer implements ApplicationRunner {
    private static final Logger log = LoggerFactory.getLogger(OrdaCacheInitializer.class);

    private final OrdaService ordaService;
    private final OrdaCache ordaCache;

    public OrdaCacheInitializer(OrdaService ordaService, OrdaCache ordaCache) {
        this.ordaService = ordaService;
        this.ordaCache = ordaCache;
    }

    @Override
    public void run(ApplicationArguments args) {
        initializeCache();
    }

    /**
     * Инициализация вручную (например, через контроллер).
     * Срабатывает только если кэш ещё пустой.
     */
    public void init() {
        if (ordaCache.getServices().isEmpty()) {
            initializeCache();
        }
    }

    public synchronized void initializeCache() {
        log.info("Initializing Orda cache...");

        loadServices();
        loadDatabases();
        loadSchemas();
        loadTables();

        log.info("✅ Orda cache initialized: {} services, {} dbs, {} schemas, {} tables",
                ordaCache.getServices().size(),
                ordaCache.getDatabases().size(),
                ordaCache.getSchemas().size(),
                ordaCache.getTables().size());
    }

    private void loadServices() {
        List<OrdaServiceDto> services = ordaService.getServices();
        services.forEach(ordaCache::putService);
        log.info("Loaded {} services into cache", services.size());
    }

    private void loadDatabases() {
        List<OrdaDbDto> databases = ordaService.getDatabases();
        databases.forEach(ordaCache::putDatabase);
        log.info("Loaded {} databases into cache", databases.size());
    }

    private void loadSchemas() {
        List<OrdaDatabaseSchemaDto> schemas = ordaService.getSchemas();
        schemas.forEach(ordaCache::putSchema);
        log.info("Loaded {} schemas into cache", schemas.size());
    }

    private void loadTables() {
        List<OrdaTableDto> tables = new ArrayList<>();
        String after = null;
        int pageSize = 10000; // можно вынести в application.yml

        while (true) {
            OrdaTablesResponseDto page = ordaService.getTablesPage(pageSize, after);
            if (page == null || page.getData() == null || page.getData().isEmpty()) {
                break;
            }
            tables.addAll(page.getData());

            after = (page.getPaging() != null) ? page.getPaging().getAfter() : null;
            if (after == null) break;
        }

        tables.forEach(ordaCache::putTable);
        log.info("Loaded {} tables into cache", tables.size());
    }
}



