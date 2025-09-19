package com.gpbapp.metadataregistry.dto.metadata;

public class TableColumnDto {
    /**
     * Полное имя колонки (FQN)
     */
    private String fqn;

    /**
     * Тип данных колонки (например: varchar, int4)
     */
    private String dtype;

    /**
     * Длина данных (для varchar, char и т.д.), может быть null
     */
    private Integer dataLength;

    /**
     * Описание колонки
     */
    private String description;

    public TableColumnDto() {
    }

    public String getFqn() {
        return fqn;
    }

    public void setFqn(String fqn) {
        this.fqn = fqn;
    }

    public String getDtype() {
        return dtype;
    }

    public void setDtype(String dtype) {
        this.dtype = dtype;
    }

    public Integer getDataLength() {
        return dataLength;
    }

    public void setDataLength(Integer dataLength) {
        this.dataLength = dataLength;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
