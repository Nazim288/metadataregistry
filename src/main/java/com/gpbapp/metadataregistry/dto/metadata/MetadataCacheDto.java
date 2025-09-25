package com.gpbapp.metadataregistry.dto.metadata;

import com.gpbapp.metadataregistry.enums.ObjectCacheType;

public class MetadataCacheDto {
    public MetadataCacheDto(String fqn, ObjectCacheType objectType, String serviceName, String hashData) {
        this.fqn = fqn;
        this.objectType = objectType;
        this.serviceName = serviceName;
        this.hashData = hashData;
    }

    private String fqn;
    private ObjectCacheType objectType;
    private String serviceName;
    private String hashData;

    public MetadataCacheDto() {
    }

    public String getFqn() {
        return fqn;
    }

    public void setFqn(String fqn) {
        this.fqn = fqn;
    }

    public ObjectCacheType getObjectType() {
        return objectType;
    }

    public void setObjectType(ObjectCacheType objectType) {
        this.objectType = objectType;
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
}
