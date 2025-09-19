package com.gpbapp.metadataregistry.dto.orda;

import java.util.List;

public class OrdaDatabaseResponseDto {
    private List<OrdaDbDto> data;
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

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
