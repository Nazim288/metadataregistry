package com.gpbapp.metadataregistry.dto.metadata;

public class SchemaDataDto {
    /**
     * Имя схемы
     */
    private String name;

    /**
     * Полное имя (Fully Qualified Name), например: service_name.database_example.schema_name
     */
    private String fqn;

    /**
     * Полное имя родителя (сервис), например: service_name.database_example
     */
    private String parentFqn;



    public SchemaDataDto(String name, String fqn, String parentFqn) {
        this.name = name;
        this.fqn = fqn;
        this.parentFqn = parentFqn;
    }

    public SchemaDataDto() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFqn() {
        return fqn;
    }

    public void setFqn(String fqn) {
        this.fqn = fqn;
    }

    public String getParentFqn() {
        return parentFqn;
    }

    public void setParentFqn(String parentFqn) {
        this.parentFqn = parentFqn;
    }
}
