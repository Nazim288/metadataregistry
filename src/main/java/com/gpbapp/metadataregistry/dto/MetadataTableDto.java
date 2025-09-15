package com.gpbapp.metadataregistry.dto;

import java.io.Serializable;
import java.util.List;

public class MetadataTableDto implements Serializable {

    public MetadataTableDto() {
    }

    public MetadataTableDto(String dataSource, String dbName, String schemaName, String tableName, String dbType, List<MetadataColumnDTO> columns) {
        this.dataSource = dataSource;
        this.dbName = dbName;
        this.schemaName = schemaName;
        this.tableName = tableName;
        this.dbType = dbType;
        this.columns = columns;
    }

    public String getDataSource() {
        return dataSource;
    }

    public void setDataSource(String dataSource) {
        this.dataSource = dataSource;
    }

    public String getDbName() {
        return dbName;
    }

    public void setDbName(String dbName) {
        this.dbName = dbName;
    }

    public String getSchemaName() {
        return schemaName;
    }

    public void setSchemaName(String schemaName) {
        this.schemaName = schemaName;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getDbType() {
        return dbType;
    }

    public void setDbType(String dbType) {
        this.dbType = dbType;
    }

    public List<MetadataColumnDTO> getColumns() {
        return columns;
    }

    public void setColumns(List<MetadataColumnDTO> columns) {
        this.columns = columns;
    }

    private String dataSource;

    private String dbName;

    private String schemaName;

    private String tableName;

    private String dbType;

    private List<MetadataColumnDTO> columns;

}
