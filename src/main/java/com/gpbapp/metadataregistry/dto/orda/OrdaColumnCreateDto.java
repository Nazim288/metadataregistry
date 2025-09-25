package com.gpbapp.metadataregistry.dto.orda;

public class OrdaColumnCreateDto {

    private String name;         // имя колонки
    private String dataType;     // тип данных (INT, VARCHAR и т.п.)
    private String constraint;   // PRIMARY_KEY, UNIQUE, NOT_NULL, NULL (опционально)
    private String description;  // описание (опционально)
    private Integer dataLength = 0;  // описание (опционально)


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


    public Integer getDataLength() {
        return dataLength;
    }

    public void setDataLength(Integer dataLength) {
        this.dataLength = dataLength;
    }

    public OrdaColumnCreateDto() {
    }

    public OrdaColumnCreateDto(String name, String dataType, String constraint, String description, Integer dataLength) {
        this.name = name;
        this.dataType = dataType;
        this.constraint = constraint;
        this.description = description;
        this.dataLength = dataLength;
    }

    public OrdaColumnCreateDto(String dataType, String constraint, String description, Integer dataLength) {
        this.dataType = dataType;
        this.constraint = constraint;
        this.description = description;
        this.dataLength = dataLength;
    }
}
