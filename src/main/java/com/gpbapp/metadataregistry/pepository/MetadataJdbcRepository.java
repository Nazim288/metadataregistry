package com.gpbapp.metadataregistry.pepository;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gpbapp.metadataregistry.common.TableColumnMetadata;
import com.gpbapp.metadataregistry.dto.MetadataColumnDTO;
import com.gpbapp.metadataregistry.dto.MetadataTableDto;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Repository
public class MetadataJdbcRepository {

    public MetadataJdbcRepository(JdbcTemplate jdbcTemplate, ObjectMapper objectMapper) {
        this.jdbcTemplate = jdbcTemplate;
        this.objectMapper = objectMapper;
    }

    private final JdbcTemplate jdbcTemplate;
    private final ObjectMapper objectMapper;

    private static final String TABLE_METADATA_QUERY_TEMPLATE = """
        SELECT data_source,
               db_name,
               schema_name,
               table_name,
               db_type,
               data
        FROM %s.metadata
        ORDER BY schema_name, table_name
    """;

    /**
     * Универсальный метод для получения метаданных из нужной схемы
     *
     * @param schemaName имя схемы (например: postgres_metadata, oracle_metadata, mssql_metadata)
     * @return объект TableColumnMetadata со всеми таблицами и колонками
     */
    public TableColumnMetadata findMetadataBySchema(String schemaName) {
        String sql = String.format(TABLE_METADATA_QUERY_TEMPLATE, schemaName);

        List<MetadataTableDto> tables = jdbcTemplate.query(
                sql,
                (rs, rowNum) -> mapRow(rs)
        );

        return new TableColumnMetadata(tables);
    }

    private MetadataTableDto mapRow(ResultSet rs) throws SQLException {
        String jsonData = rs.getString("data");
        List<MetadataColumnDTO> columns;
        try {
            JsonNode node = objectMapper.readTree(jsonData);
            if (node.isArray()) {
                columns = Arrays.asList(objectMapper.treeToValue(node, MetadataColumnDTO[].class));
            } else {
                columns = Collections.singletonList(objectMapper.treeToValue(node, MetadataColumnDTO.class));
            }
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Failed to parse metadata JSON for table: "
                    + rs.getString("table_name"), e);
        }

        MetadataTableDto tableDto = new MetadataTableDto();
        tableDto.setDataSource(rs.getString("data_source"));
        tableDto.setDbName(rs.getString("db_name"));
        tableDto.setSchemaName(rs.getString("schema_name"));
        tableDto.setTableName(rs.getString("table_name"));
        tableDto.setDbType(rs.getString("db_type"));
        tableDto.setColumns(columns);
        return tableDto;
    }
}

