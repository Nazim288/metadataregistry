package com.gpbapp.metadataregistry.dto;

import java.util.List;

public class OrdaDatabaseResponseDto {
    private List<OrdaDbDto> data;

    public OrdaDatabaseResponseDto(List<OrdaDbDto> data) {
        this.data = data;
    }

    public List<OrdaDbDto> getData() {
        return data;
    }

    public void setData(List<OrdaDbDto> data) {
        this.data = data;
    }
}
