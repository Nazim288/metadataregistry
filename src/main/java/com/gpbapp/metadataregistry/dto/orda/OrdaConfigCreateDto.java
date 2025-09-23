package com.gpbapp.metadataregistry.dto.orda;

public class OrdaConfigCreateDto {
    private ConfigDto config = new ConfigDto();

    public OrdaConfigCreateDto(ConfigDto config) {
        this.config = config;
    }

    public OrdaConfigCreateDto() {
    }

    public ConfigDto getConfig() {
        return config;
    }

    public void setConfig(ConfigDto config) {
        this.config = config;
    }
}
