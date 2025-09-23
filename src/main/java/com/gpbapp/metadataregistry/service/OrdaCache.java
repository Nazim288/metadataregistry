package com.gpbapp.metadataregistry.service;

import com.gpbapp.metadataregistry.dto.orda.OrdaDatabaseSchemaDto;
import com.gpbapp.metadataregistry.dto.orda.OrdaDbDto;
import com.gpbapp.metadataregistry.dto.orda.OrdaServiceDto;
import com.gpbapp.metadataregistry.dto.orda.OrdaTableDto;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


@Service
public class OrdaCache {

    private final Map<String, OrdaServiceDto> services = new ConcurrentHashMap<>();
    private final Map<String, OrdaDbDto> databases = new ConcurrentHashMap<>();
    private final Map<String, OrdaDatabaseSchemaDto> schemas = new ConcurrentHashMap<>();
    private final Map<String, OrdaTableDto> tables = new ConcurrentHashMap<>();

    // --- Services ---
    public void putService(OrdaServiceDto s) {
        services.put(s.getFullyQualifiedName(), s);
    }

    public OrdaServiceDto getService(String fqn) {
        return services.get(fqn);
    }

    public Map<String, OrdaServiceDto> getServices() {
        return services;
    }

    // --- Databases ---
    public void putDatabase(OrdaDbDto d) {
        databases.put(d.getFullyQualifiedName(), d);
    }

    public OrdaDbDto getDatabase(String fqn) {
        return databases.get(fqn);
    }

    public Map<String, OrdaDbDto> getDatabases() {
        return databases;
    }

    // --- Schemas ---
    public void putSchema(OrdaDatabaseSchemaDto s) {
        schemas.put(s.getFullyQualifiedName(), s);
    }

    public OrdaDatabaseSchemaDto getSchema(String fqn) {
        return schemas.get(fqn);
    }

    public Map<String, OrdaDatabaseSchemaDto> getSchemas() {
        return schemas;
    }

    // --- Tables ---
    public void putTable(OrdaTableDto t) {
        tables.put(t.getFullyQualifiedName(), t);
    }

    public OrdaTableDto getTable(String fqn) {
        return tables.get(fqn);
    }

    public Map<String, OrdaTableDto> getTables() {
        return tables;
    }
}

