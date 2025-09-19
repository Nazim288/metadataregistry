package com.gpbapp.metadataregistry.dto.orda;

public class OrdaSchemaCreateDTO {
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDatabase() {
        return database;
    }

    public void setDatabase(String database) {
        this.database = database;
    }

    private String name;
    private String database;

    public OrdaSchemaCreateDTO() {
    }
}
