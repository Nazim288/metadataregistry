package com.gpbapp.metadataregistry.service;

import com.gpbapp.metadataregistry.dto.metadata.DatabaseMetadataDto;
import com.gpbapp.metadataregistry.dto.metadata.SchemaMetadataDto;
import com.gpbapp.metadataregistry.dto.metadata.TableMetadataDto;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class MetadataPgCache {
    private final Map<String, DatabaseMetadataDto> databases = new ConcurrentHashMap<>();
    private final Map<String, SchemaMetadataDto> schemas = new ConcurrentHashMap<>();
    private final Map<String, TableMetadataDto> tables = new ConcurrentHashMap<>();

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

    public String getSize() {

        return String.format(
                "{ \"bases\": %d, \"schemas\": %d, \"tables\": %d }",
                databases.size(), schemas.size(), tables.size()
        );

    }
}

