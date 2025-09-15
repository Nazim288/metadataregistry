package com.gpbapp.metadataregistry.dto;

import java.util.List;

public class OrdaSchemasResponseDto {
    private List<OrdaDatabaseSchemaDto> data;


    public OrdaSchemasResponseDto() {
    }

    public List<OrdaDatabaseSchemaDto> getData() {
        return data;
    }

    public void setData(List<OrdaDatabaseSchemaDto> data) {
        this.data = data;
    }
}
