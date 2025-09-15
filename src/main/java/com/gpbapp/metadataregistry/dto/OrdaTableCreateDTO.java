package com.gpbapp.metadataregistry.dto;

import java.util.List;

public class OrdaTableCreateDTO {
    private String name;                         // имя таблицы
    private List<OrdaColumnCreateDto> columns;       // список колонок
    private List<OrdaTableConstraintDto> tableConstraints; // ограничения (например FK)
    private String databaseSchema;               // FQN схемы (service.database.schema)

    public OrdaTableCreateDTO() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<OrdaColumnCreateDto> getColumns() {
        return columns;
    }

    public void setColumns(List<OrdaColumnCreateDto> columns) {
        this.columns = columns;
    }

    public List<OrdaTableConstraintDto> getTableConstraints() {
        return tableConstraints;
    }

    public void setTableConstraints(List<OrdaTableConstraintDto> tableConstraints) {
        this.tableConstraints = tableConstraints;
    }

    public String getDatabaseSchema() {
        return databaseSchema;
    }

    public void setDatabaseSchema(String databaseSchema) {
        this.databaseSchema = databaseSchema;
    }
}
