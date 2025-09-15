package com.gpbapp.metadataregistry.dto;

public class OrdaServiceCreateDto {
    public OrdaServiceCreateDto(String name, String service) {
        this.name = name;
        this.service = service;
    }

    private String name;
    private String service;


    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
