package com.gpbapp.metadataregistry.dto;

import java.util.List;

public class DatabaseDto {
    private String id;
    private String name;
    private String fullyQualifiedName;
    private List<String> tags;
    private double version;
    private long updatedAt;
    private String updatedBy;
    private String href;
    private ServiceDto service;
    private String serviceType;
    private UsageSummaryDto usageSummary;
    private List<DatabaseSchemaDto> databaseSchemas;
    private boolean isDefault;
    private boolean deleted;
    private String sourceHash;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFullyQualifiedName() {
        return fullyQualifiedName;
    }

    public void setFullyQualifiedName(String fullyQualifiedName) {
        this.fullyQualifiedName = fullyQualifiedName;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    public double getVersion() {
        return version;
    }

    public void setVersion(double version) {
        this.version = version;
    }

    public long getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(long updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href;
    }

    public ServiceDto getService() {
        return service;
    }

    public void setService(ServiceDto service) {
        this.service = service;
    }

    public String getServiceType() {
        return serviceType;
    }

    public void setServiceType(String serviceType) {
        this.serviceType = serviceType;
    }

    public UsageSummaryDto getUsageSummary() {
        return usageSummary;
    }

    public void setUsageSummary(UsageSummaryDto usageSummary) {
        this.usageSummary = usageSummary;
    }

    public List<DatabaseSchemaDto> getDatabaseSchemas() {
        return databaseSchemas;
    }

    public void setDatabaseSchemas(List<DatabaseSchemaDto> databaseSchemas) {
        this.databaseSchemas = databaseSchemas;
    }

    public boolean isDefault() {
        return isDefault;
    }

    public void setDefault(boolean aDefault) {
        isDefault = aDefault;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    public String getSourceHash() {
        return sourceHash;
    }

    public void setSourceHash(String sourceHash) {
        this.sourceHash = sourceHash;
    }

    public DatabaseDto() {
    }

    public DatabaseDto(String id, String name, String fullyQualifiedName, List<String> tags, double version, long updatedAt, String updatedBy, String href, ServiceDto service, String serviceType, UsageSummaryDto usageSummary, List<DatabaseSchemaDto> databaseSchemas, boolean isDefault, boolean deleted, String sourceHash) {
        this.id = id;
        this.name = name;
        this.fullyQualifiedName = fullyQualifiedName;
        this.tags = tags;
        this.version = version;
        this.updatedAt = updatedAt;
        this.updatedBy = updatedBy;
        this.href = href;
        this.service = service;
        this.serviceType = serviceType;
        this.usageSummary = usageSummary;
        this.databaseSchemas = databaseSchemas;
        this.isDefault = isDefault;
        this.deleted = deleted;
        this.sourceHash = sourceHash;
    }
}
