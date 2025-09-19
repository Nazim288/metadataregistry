package com.gpbapp.metadataregistry.dto.metadata;

import java.time.LocalDateTime;

public class DatabaseMetadataDto {
    /**
     * Уникальный идентификатор записи
     */
    private Long id;

    /**
     * Полное имя (FQN), например: service_name.database_name
     */
    private String fqn;
   /**
     * Имя db
     */
    private String name;
   /**
     * parent_fqn: service_name.database_name.name
     */
    private String parent_fqn;
    /**
     * Имя сервиса
     */
    private String serviceName;
    /**
     * Хэш от JSON-данных для проверки изменений
     */
    private String hashData;

    /**
     * Дата создания записи
     */
    private LocalDateTime createdAt;

    public DatabaseMetadataDto() {}

    public DatabaseMetadataDto(Long id, String fqn, String name, String parent_fqn, String serviceName, String hashData, LocalDateTime createdAt) {
        this.id = id;
        this.fqn = fqn;
        this.name = name;
        this.parent_fqn = parent_fqn;
        this.serviceName = serviceName;
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

    public String getParent_fqn() {
        return parent_fqn;
    }

    public void setParent_fqn(String parent_fqn) {
        this.parent_fqn = parent_fqn;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
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
