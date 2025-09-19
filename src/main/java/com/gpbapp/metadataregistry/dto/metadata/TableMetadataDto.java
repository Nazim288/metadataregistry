package com.gpbapp.metadataregistry.dto.metadata;

import java.time.LocalDateTime;

public class TableMetadataDto {
    /**
     * Уникальный идентификатор записи
     */
    private Long id;

    /**
     * Полное имя (FQN), например: service_name.database_name.schema_name.table_name
     */
    private String fqn;
    /**
     * Имя table
     */
    private String name;
    /**
     * parent_fqn: service_name.database_name.name
     */
    private String parentFqn;
    /**
     * Имя сервиса
     */
    private String serviceName;

    /**
     * Имя базы данных, к которой относится таблица
     */
    private String dbName;

    /**
     * Имя схемы, к которой относится таблица
     */
    private String schemaName;

    /**
     * JSON-данные (структурированное описание таблицы: имя, колонки, parent_fqn)
     */
    private TableDataDto data;
   /**
     * Description
     */
    private String description;

    /**
     * Хэш от JSON-данных для проверки изменений
     */
    private String hashData;

    /**
     * Дата создания записи
     */
    private LocalDateTime createdAt;

    public TableMetadataDto() {}

    public TableMetadataDto(Long id, String fqn, String name, String parentFqn, String serviceName, String dbName, String schemaName, TableDataDto data, String description, String hashData, LocalDateTime createdAt) {
        this.id = id;
        this.fqn = fqn;
        this.name = name;
        this.parentFqn = parentFqn;
        this.serviceName = serviceName;
        this.dbName = dbName;
        this.schemaName = schemaName;
        this.data = data;
        this.description = description;
        this.hashData = hashData;
        this.createdAt = createdAt;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFqn() {
        return fqn;
    }

    public void setFqn(String fqn) {
        this.fqn = fqn;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getParentFqn() {
        return parentFqn;
    }

    public void setParentFqn(String parentFqn) {
        this.parentFqn = parentFqn;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getDbName() {
        return dbName;
    }

    public void setDbName(String dbName) {
        this.dbName = dbName;
    }

    public String getSchemaName() {
        return schemaName;
    }

    public void setSchemaName(String schemaName) {
        this.schemaName = schemaName;
    }

    public TableDataDto getData() {
        return data;
    }

    public void setData(TableDataDto data) {
        this.data = data;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getHashData() {
        return hashData;
    }

    public void setHashData(String hashData) {
        this.hashData = hashData;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
