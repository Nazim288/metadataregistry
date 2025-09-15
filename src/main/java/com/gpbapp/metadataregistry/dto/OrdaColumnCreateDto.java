package com.gpbapp.metadataregistry.dto;

public class OrdaColumnCreateDto {
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDataType() {
        return dataType;
    }

    public void setDataType(String dataType) {
        this.dataType = dataType;
    }

    public String getConstraint() {
        return constraint;
    }

    public void setConstraint(String constraint) {
        this.constraint = constraint;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    private String name;         // имя колонки
    private String dataType;     // тип данных (INT, VARCHAR и т.п.)
    private String constraint;   // PRIMARY_KEY, UNIQUE, NOT_NULL, NULL (опционально)
    private String description;  // описание (опционально)

    public OrdaColumnCreateDto() {
    }
}
