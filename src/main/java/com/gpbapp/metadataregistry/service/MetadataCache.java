package com.gpbapp.metadataregistry.service;

import com.gpbapp.metadataregistry.dto.metadata.DatabaseMetadataDto;
import com.gpbapp.metadataregistry.dto.metadata.SchemaMetadataDto;
import com.gpbapp.metadataregistry.dto.metadata.TableMetadataDto;
import com.gpbapp.metadataregistry.pepository.MetadataBasesRepository;
import com.gpbapp.metadataregistry.pepository.MetadataSchemasRepository;
import com.gpbapp.metadataregistry.pepository.MetadataTablesRepository;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class MetadataCache {

    private final MetadataBasesRepository basesRepository;
    private final MetadataSchemasRepository schemasRepository;
    private final MetadataTablesRepository tablesRepository;

    private final Map<String, DatabaseMetadataDto> databases = new ConcurrentHashMap<>();
    private final Map<String, SchemaMetadataDto> schemas = new ConcurrentHashMap<>();
    private final Map<String, TableMetadataDto> tables = new ConcurrentHashMap<>();

    public MetadataCache(MetadataBasesRepository basesRepository,
                         MetadataSchemasRepository schemasRepository,
                         MetadataTablesRepository tablesRepository) {
        this.basesRepository = basesRepository;
        this.schemasRepository = schemasRepository;
        this.tablesRepository = tablesRepository;
    }

    public void putDatabase(DatabaseMetadataDto db) {
        databases.put(db.getFqn(), db);
    }

    public DatabaseMetadataDto getDatabase(String fqn) {
        return databases.get(fqn);
    }

    public Map<String, DatabaseMetadataDto> getDatabases() {
        return databases;
    }

    public void putSchema(SchemaMetadataDto schema) {
        schemas.put(schema.getFqn(), schema);
    }

    public SchemaMetadataDto getSchema(String fqn) {
        return schemas.get(fqn);
    }

    public Map<String, SchemaMetadataDto> getSchemas() {
        return schemas;
    }

    public void putTable(TableMetadataDto table) {
        tables.put(table.getFqn(), table);
    }

    public TableMetadataDto getTable(String fqn) {
        return tables.get(fqn);
    }

    public Map<String, TableMetadataDto> getTables() {
        return tables;
    }

    public void clearAll() {
        databases.clear();
        schemas.clear();
        tables.clear();
    }

    /**
     * Полная загрузка кеша из БД
     */
    public String loadAll(String schemaName) {
        clearAll();
        basesRepository.findAllBySchema(schemaName)
                .forEach(this::putDatabase);
        schemasRepository.findAllBySchema(schemaName)
                .forEach(this::putSchema);
        tablesRepository.findAllBySchema(schemaName)
                .forEach(this::putTable);
       return getSize();
    }

    public String getSize() {

        return String.format(
                "{ \"bases\": %d, \"schemas\": %d, \"tables\": %d }",
                databases.size(), schemas.size(), tables.size()
        );

    }
}

