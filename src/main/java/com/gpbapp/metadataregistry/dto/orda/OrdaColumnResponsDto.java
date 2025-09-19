package com.gpbapp.metadataregistry.dto.orda;

public class OrdaColumnResponsDto {
    public OrdaColumnResponsDto(String name) {
        this.name = name;
    }

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

    public String getDataTypeDisplay() {
        return dataTypeDisplay;
    }

    public void setDataTypeDisplay(String dataTypeDisplay) {
        this.dataTypeDisplay = dataTypeDisplay;
    }

    public String getFullyQualifiedName() {
        return fullyQualifiedName;
    }

    public void setFullyQualifiedName(String fullyQualifiedName) {
        this.fullyQualifiedName = fullyQualifiedName;
    }

    private String name;
    private String dataType;
    private String dataTypeDisplay;
    private String fullyQualifiedName;
}
