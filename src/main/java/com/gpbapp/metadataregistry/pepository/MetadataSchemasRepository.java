package com.gpbapp.metadataregistry.pepository;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gpbapp.metadataregistry.dto.metadata.SchemaMetadataDto;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Service
public class MetadataSchemasRepository {

    private final JdbcTemplate jdbcTemplate;
    private final ObjectMapper objectMapper;

    public MetadataSchemasRepository(JdbcTemplate jdbcTemplate, ObjectMapper objectMapper) {
        this.jdbcTemplate = jdbcTemplate;
        this.objectMapper = objectMapper;
    }

    private static final String SCHEMA_METADATA_QUERY_TEMPLATE = """
        SELECT id,
               fqn,
               name,
               parent_fqn,
               service_name,
               db_name,
               hash_data,
               created_at
        FROM %s.schema_metadata
        ORDER BY created_at DESC
    """;

    /**
     * Получение всех записей из таблицы schema_metadata по схеме
     */
    public List<SchemaMetadataDto> findAllBySchema(String schemaName) {
        String sql = String.format(SCHEMA_METADATA_QUERY_TEMPLATE, schemaName);
        return jdbcTemplate.query(sql, (rs, rowNum) -> mapRow(rs));
    }

    /**
     * Получение всех записей из таблицы schema_metadata по схеме и service_name
     */
    public List<SchemaMetadataDto> findAllBySchemaAndService(String schemaName, String serviceName) {
        String sql = String.format("""
            SELECT id,
                   fqn,
                   name,
                   parent_fqn,
                   service_name,
                   db_name,
                   hash_data,
                   created_at
            FROM %s.schema_metadata
            WHERE service_name = ?
            ORDER BY created_at DESC
        """, schemaName);

        return jdbcTemplate.query(sql, ps -> ps.setString(1, serviceName), (rs, rowNum) -> mapRow(rs));
    }

    private SchemaMetadataDto mapRow(ResultSet rs) throws SQLException {
        return new SchemaMetadataDto(
                rs.getLong("id"),
                rs.getString("fqn"),
                rs.getString("name"),
                rs.getString("parent_fqn"),
                rs.getString("service_name"),
                rs.getString("db_name"),
                rs.getString("hash_data"),
                rs.getObject("created_at") != null
                        ? rs.getTimestamp("created_at").toLocalDateTime()
                        : null
        );
    }
}
