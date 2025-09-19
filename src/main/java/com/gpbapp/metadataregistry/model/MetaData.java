package com.gpbapp.metadataregistry.model;

import com.fasterxml.jackson.databind.JsonNode;
import com.gpbapp.metadataregistry.common.JsonNodeConverter;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "metadata", schema = "postgres_metadata")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MetaData{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "data_source", nullable = false)
    private String dataSource;

    @Column(name = "table_name", nullable = false)
    private String tableName;

    @Column(columnDefinition = "jsonb", nullable = false)
    @Convert(converter = JsonNodeConverter.class)
    private JsonNode data;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "record_key", nullable = false)
    private String recordKey;

    @Column(name = "data_hash", nullable = false)
    private String dataHash;
}
