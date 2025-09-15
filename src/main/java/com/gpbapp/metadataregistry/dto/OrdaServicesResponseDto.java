package com.gpbapp.metadataregistry.dto;

import java.util.List;

public class OrdaServicesResponseDto {
    public List<DatabaseDto> getData() {
        return data;
    }

    public void setData(List<DatabaseDto> data) {
        this.data = data;
    }

    private List<DatabaseDto> data;

    public OrdaServicesResponseDto(List<DatabaseDto> data) {
        this.data = data;
    }
}
