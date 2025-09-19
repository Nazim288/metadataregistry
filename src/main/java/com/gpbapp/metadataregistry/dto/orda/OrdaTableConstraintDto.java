package com.gpbapp.metadataregistry.dto.orda;

import java.util.List;

public class OrdaTableConstraintDto {
    public String getConstraintType() {
        return constraintType;
    }

    public void setConstraintType(String constraintType) {
        this.constraintType = constraintType;
    }

    public List<String> getColumns() {
        return columns;
    }

    public void setColumns(List<String> columns) {
        this.columns = columns;
    }

    private String constraintType;  // FOREIGN_KEY, CHECK и т.п.
    private List<String> columns;   // список колонок, на которые действует ограничение

    public OrdaTableConstraintDto() {
    }
}
