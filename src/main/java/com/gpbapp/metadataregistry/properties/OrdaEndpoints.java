package com.gpbapp.metadataregistry.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@ConfigurationProperties(prefix = "orda.endpoints")
@Component
public class OrdaEndpoints {
    private String databases;
    private String services;
    private String schemas;
    private String tables;
    private String columns;

    public String getServices() {
        return services;
    }

    public void setServices(String services) {
        this.services = services;
    }
    public String getColumns() {
        return columns;
    }

    public void setColumns(String columns) {
        this.columns = columns;
    }
    public String getDatabases() {
        return databases;
    }

    public void setDatabases(String databases) {
        this.databases = databases;
    }

    public String getSchemas() {
        return schemas;
    }

    public void setSchemas(String schemas) {
        this.schemas = schemas;
    }

    public String getTables() {
        return tables;
    }

    public void setTables(String tables) {
        this.tables = tables;
    }

    public OrdaEndpoints() {
    }
}
