package com.gpbapp.metadataregistry.dto;

public class ServiceDto {
    public ServiceDto() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href;
    }

    private String id;
    private String type;
    private String name;
    private String fullyQualifiedName;
    private String description;
    private boolean deleted;
    private String href;

    public ServiceDto(String id, String type, String name, String fullyQualifiedName, String description, boolean deleted, String href) {
        this.id = id;
        this.type = type;
        this.name = name;
        this.fullyQualifiedName = fullyQualifiedName;
        this.description = description;
        this.deleted = deleted;
        this.href = href;
    }
}
