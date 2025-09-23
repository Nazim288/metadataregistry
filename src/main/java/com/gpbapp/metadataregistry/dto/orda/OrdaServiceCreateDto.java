package com.gpbapp.metadataregistry.dto.orda;

public class OrdaServiceCreateDto {

    private String name;
    private String serviceType;
    private OrdaConfigCreateDto connection = new OrdaConfigCreateDto();
    private String description;
    private String displayName;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getServiceType() {
        return serviceType;
    }

    public void setServiceType(String serviceType) {
        this.serviceType = serviceType;
    }

    public OrdaConfigCreateDto getConnection() {
        return connection;
    }

    public void setConnection(OrdaConfigCreateDto connection) {
        this.connection = connection;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public OrdaServiceCreateDto() {
    }


}
