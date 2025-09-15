package com.gpbapp.metadataregistry.dto;

import java.util.List;

public class OrdaTablesResponseDto {
    public List<OrdaTableDto> getData() {
        return data;
    }

    public void setData(List<OrdaTableDto> data) {
        this.data = data;
    }

    private List<OrdaTableDto> data;

    public OrdaTablesResponseDto(List<OrdaTableDto> data) {
        this.data = data;
    }

    public OrdaTablesResponseDto() {
    }
}
