package com.gpbapp.metadataregistry.pepository;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gpbapp.metadataregistry.dto.metadata.DatabaseMetadataDto;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;

@Component
public class MetadataBasesRepository {

    private final JdbcTemplate jdbcTemplate;
    private final ObjectMapper objectMapper;

    public MetadataBasesRepository(JdbcTemplate jdbcTemplate, ObjectMapper objectMapper) {
        this.jdbcTemplate = jdbcTemplate;
        this.objectMapper = objectMapper;
    }

    private static final String BASE_METADATA_QUERY_TEMPLATE = """
        SELECT id,
               fqn,
               name,
               parent_fqn,
               service_name,
               hash_data,
               created_at
        FROM %s.database_metadata
        ORDER BY created_at DESC
    """;

    /**
     * Получение всех записей из таблицы database_metadata по схеме
     *
     * @param schemaName имя схемы (например: postgres_metadata)
     * @return список объектов DatabaseMetadataDto
     */
    public List<DatabaseMetadataDto> findAllBySchema(String schemaName) {
        String sql = String.format(BASE_METADATA_QUERY_TEMPLATE, schemaName);
        return jdbcTemplate.query(sql, (rs, rowNum) -> mapRow(rs));
    }

    private DatabaseMetadataDto mapRow(ResultSet rs) throws SQLException {
        return new DatabaseMetadataDto(
                rs.getLong("id"),
                rs.getString("fqn"),
                rs.getString("name"),
                rs.getString("parent_fqn"),
                rs.getString("service_name"),
                rs.getString("hash_data"),
                rs.getObject("created_at") != null
                        ? rs.getTimestamp("created_at").toLocalDateTime()
                        : null
        );
    }
}

