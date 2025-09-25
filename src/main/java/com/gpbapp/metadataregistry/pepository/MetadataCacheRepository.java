package com.gpbapp.metadataregistry.pepository;

import com.gpbapp.metadataregistry.dto.metadata.MetadataCacheDto;
import com.gpbapp.metadataregistry.enums.ObjectCacheType;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;


@Repository
public class MetadataCacheRepository {

    private final JdbcTemplate jdbcTemplate;

    public MetadataCacheRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private String table(String schema) {
        return schema + ".metadata_cache";
    }

    // ---- Одиночные операции
    public void upsert(String schema, MetadataCacheDto dto) {
        String sql = """
            INSERT INTO %s (fqn, object_type, service_name, hash_data)
            VALUES (?, ?, ?, ?)
            ON CONFLICT (fqn) DO UPDATE
                SET object_type = EXCLUDED.object_type,
                    service_name = EXCLUDED.service_name,
                    hash_data = EXCLUDED.hash_data
        """.formatted(table(schema));

        jdbcTemplate.update(sql,
                dto.getFqn(),
                dto.getObjectType().name(),
                dto.getServiceName(),
                dto.getHashData()
        );
    }

    public void delete(String schema, String fqn) {
        String sql = "DELETE FROM %s WHERE fqn = ?".formatted(table(schema));
        jdbcTemplate.update(sql, fqn);
    }

    // ---- Batch операции
    public void batchUpsert(String schema, List<MetadataCacheDto> dtos) {
        String sql = """
            INSERT INTO %s (fqn, object_type, service_name, hash_data)
            VALUES (?, ?, ?, ?)
            ON CONFLICT (fqn) DO UPDATE
                SET object_type = EXCLUDED.object_type,
                    service_name = EXCLUDED.service_name,
                    hash_data = EXCLUDED.hash_data
        """.formatted(table(schema));

        jdbcTemplate.batchUpdate(sql, dtos, dtos.size(), (ps, dto) -> {
            ps.setString(1, dto.getFqn());
            ps.setString(2, dto.getObjectType().name());
            ps.setString(3, dto.getServiceName());
            ps.setString(4, dto.getHashData());
        });
    }

    public void batchDelete(String schema, List<String> fqns) {
        String sql = "DELETE FROM %s WHERE fqn = ?".formatted(table(schema));
        jdbcTemplate.batchUpdate(sql, fqns, fqns.size(), (ps, fqn) -> ps.setString(1, fqn));
    }

    // ---- Чтение
    public List<MetadataCacheDto> findByType(String schema, ObjectCacheType type) {
        String sql = """
            SELECT fqn, object_type, service_name, hash_data
            FROM %s
            WHERE object_type = ?
        """.formatted(table(schema));

        return jdbcTemplate.query(
                sql,
                ps -> ps.setString(1, type.name()),
                (rs, rowNum) -> mapRow(rs)
        );
    }

    public List<MetadataCacheDto> findAll(String schema) {
        String sql = "SELECT fqn, object_type, service_name, hash_data FROM %s".formatted(table(schema));
        return jdbcTemplate.query(sql, (rs, rowNum) -> mapRow(rs));
    }

    private MetadataCacheDto mapRow(ResultSet rs) throws SQLException {
        MetadataCacheDto dto = new MetadataCacheDto();
        dto.setFqn(rs.getString("fqn"));
        dto.setObjectType(ObjectCacheType.valueOf(rs.getString("object_type")));
        dto.setServiceName(rs.getString("service_name"));
        dto.setHashData(rs.getString("hash_data"));
        return dto;
    }
}

