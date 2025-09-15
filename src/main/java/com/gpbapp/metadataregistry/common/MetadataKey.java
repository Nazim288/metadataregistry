package com.gpbapp.metadataregistry.common;


import java.io.Serializable;


public class MetadataKey implements Serializable {
    public MetadataKey(String dataSource, String dbName,String schemaName, String tableName) {
        this.dataSource = dataSource;
        this.dbName = dbName;
        this.schemaName = schemaName;
        this.tableName = tableName;
    }


    private String dataSource;
    private String dbName;
    private String schemaName;
    private String tableName;

    public MetadataKey() {
    }

    public String getDbName() {
        return dbName;
    }

    public void setDbName(String dbName) {
        this.dbName = dbName;
    }


    public String getDataSource() {
        return dataSource;
    }

    public void setDataSource(String dataSource) {
        this.dataSource = dataSource;
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

}
