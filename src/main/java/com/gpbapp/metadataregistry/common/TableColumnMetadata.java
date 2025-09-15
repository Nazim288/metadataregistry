package com.gpbapp.metadataregistry.common;

import com.gpbapp.metadataregistry.dto.MetadataTableDto;

import java.io.Serializable;
import java.util.List;


public class TableColumnMetadata implements Serializable {


    private List<MetadataTableDto> dataSource;
    public TableColumnMetadata(List<MetadataTableDto> dataSource) {
        this.dataSource = dataSource;
    }

    public List<MetadataTableDto> getDataSource() {
        return dataSource;
    }

    public void setDataSource(List<MetadataTableDto> dataSource) {
        this.dataSource = dataSource;
    }
}
