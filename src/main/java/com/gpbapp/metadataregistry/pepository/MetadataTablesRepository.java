package com.gpbapp.metadataregistry.pepository;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gpbapp.metadataregistry.dto.metadata.TableDataDto;
import com.gpbapp.metadataregistry.dto.metadata.TableMetadataDto;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Service
public class MetadataTablesRepository {

    private final JdbcTemplate jdbcTemplate;
    private final ObjectMapper objectMapper;

    public MetadataTablesRepository(JdbcTemplate jdbcTemplate, ObjectMapper objectMapper) {
        this.jdbcTemplate = jdbcTemplate;
        this.objectMapper = objectMapper;
    }

    private static final String TABLE_METADATA_QUERY_TEMPLATE = """
        SELECT id,
               fqn,
               name,
               parent_fqn,
               service_name,
               db_name,
               schema_name,
               description,
               data,
               hash_data,
               created_at
        FROM %s.table_metadata
        ORDER BY created_at DESC
    """;

    /**
     * Получение всех таблиц по схеме
     */
    public List<TableMetadataDto> findAllBySchema(String schemaName) {
        String sql = String.format(TABLE_METADATA_QUERY_TEMPLATE, schemaName);
        return jdbcTemplate.query(sql, (rs, rowNum) -> mapRow(rs));
    }

    /**
     * Пагинация по таблицам из schemaName
     */
    public List<TableMetadataDto> findPageBySchema(String schemaName, int limit, int offset) {
        String sql = String.format(TABLE_METADATA_QUERY_TEMPLATE, schemaName) + " LIMIT ? OFFSET ?";
        return jdbcTemplate.query(sql, (rs, rowNum) -> mapRow(rs), limit, offset);
    }

    /**
     * Получение всех таблиц по схеме и сервису
     */
    public List<TableMetadataDto> findAllBySchemaAndService(String schemaName, String serviceName) {
        String sql = String.format("""
            SELECT id,
                   fqn,
                   name,
                   parent_fqn,
                   service_name,
                   db_name,
                   schema_name,
                   description,
                   data,
                   hash_data,
                   created_at
            FROM %s.table_metadata
            WHERE service_name = ?
            ORDER BY created_at DESC
        """, schemaName);

        return jdbcTemplate.query(sql, ps -> ps.setString(1, serviceName), (rs, rowNum) -> mapRow(rs));
    }

    private TableMetadataDto mapRow(ResultSet rs) throws SQLException {
        String jsonData = rs.getString("data");
        TableDataDto data = null;

        try {
            if (jsonData != null) {
                data = objectMapper.readValue(jsonData, TableDataDto.class);
            }
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Failed to parse table metadata JSON for fqn: "
                    + rs.getString("fqn"), e);
        }

        TableMetadataDto dto = new TableMetadataDto();
        dto.setId(rs.getLong("id"));
        dto.setFqn(rs.getString("fqn"));
        dto.setName(rs.getString("name"));
        dto.setParentFqn(rs.getString("parent_fqn"));
        dto.setServiceName(rs.getString("service_name"));
        dto.setDbName(rs.getString("db_name"));
        dto.setSchemaName(rs.getString("schema_name"));
        dto.setDescription(rs.getString("description"));
        dto.setData(data);
        dto.setHashData(rs.getString("hash_data"));
        dto.setCreatedAt(rs.getObject("created_at") != null
                ? rs.getTimestamp("created_at").toLocalDateTime()
                : null);

        return dto;
    }
}
