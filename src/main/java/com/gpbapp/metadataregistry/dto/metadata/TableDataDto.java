package com.gpbapp.metadataregistry.dto.metadata;

import java.util.List;

public class TableDataDto {
    private List<TableColumnDto> columns;



    public TableDataDto(List<TableColumnDto> columns) {
        this.columns = columns;
    }

    public TableDataDto() {
    }

    public List<TableColumnDto> getColumns() {
        return columns;
    }

    public void setColumns(List<TableColumnDto> columns) {
        this.columns = columns;
    }
}

