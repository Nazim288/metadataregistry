package com.gpbapp.metadataregistry.dto.metadata;

public class BaseDataDto {
    /**
     * Имя базы данных
     */
    private String name;

    /**
     * Полное имя (Fully Qualified Name), например: service_name.database_example
     */
    private String fqn;

    /**
     * Полное имя родителя (сервис), например: service_name
     */
    private String parentFqn;

    public BaseDataDto() {
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
