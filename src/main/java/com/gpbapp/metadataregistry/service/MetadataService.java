package com.gpbapp.metadataregistry.service;

import com.gpbapp.metadataregistry.dto.metadata.DatabaseMetadataDto;
import com.gpbapp.metadataregistry.dto.metadata.SchemaMetadataDto;
import com.gpbapp.metadataregistry.dto.metadata.TableMetadataDto;
import com.gpbapp.metadataregistry.pepository.MetadataBasesRepository;
import com.gpbapp.metadataregistry.pepository.MetadataSchemasRepository;
import com.gpbapp.metadataregistry.pepository.MetadataTablesRepository;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.stream.Collectors;

@Service
public class MetadataService {

    public MetadataService(MetadataBasesRepository basesRepository, MetadataSchemasRepository schemasRepository, MetadataTablesRepository tablesRepository) {
        this.basesRepository = basesRepository;
        this.schemasRepository = schemasRepository;
        this.tablesRepository = tablesRepository;
    }

    private final MetadataBasesRepository basesRepository;
    private final MetadataSchemasRepository schemasRepository;
    private final MetadataTablesRepository tablesRepository;

    /**
     * Получение всех баз данных из указанной схемы
     */
    public Map<String, DatabaseMetadataDto> getAllDatabasesBySchema(String schemaName) {
        return basesRepository.findAllBySchema(schemaName).stream()
                .collect(Collectors.toMap(DatabaseMetadataDto::getFqn, d -> d, (a, b) -> a));
    }
    /**
     * Получение всех баз данных из указанной схемы и источника
     */
    public Map<String, DatabaseMetadataDto> getAllDatabasesBySchemaAndService(String schemaName, String source) {
        return basesRepository.findAllBySchemaAndService(schemaName, source).stream()
                .collect(Collectors.toMap(DatabaseMetadataDto::getFqn, d -> d, (a, b) -> a));
    }

    /**
     * Получение всех схем из указанной схемы
     */
    public Map<String, SchemaMetadataDto> getAllSchemasBySchema(String schemaName) {
        return schemasRepository.findAllBySchema(schemaName).stream()
                .collect(Collectors.toMap(SchemaMetadataDto::getFqn, s -> s, (a, b) -> a));
    }
  /**
     * Получение всех схем из указанной схемы и источника
     */
    public Map<String, SchemaMetadataDto> getAllSchemasBySchemaAndService(String schemaName, String source) {
        return schemasRepository.findAllBySchemaAndService(schemaName, source).stream()
                .collect(Collectors.toMap(SchemaMetadataDto::getFqn, s -> s, (a, b) -> a));
    }

    /**
     * Получение всех таблиц из указанной схемы
     */
    public Map<String, TableMetadataDto> getAllTablesBySchema(String schemaName) {
        return tablesRepository.findAllBySchema(schemaName).stream()
                .collect(Collectors.toMap(TableMetadataDto::getFqn, t -> t, (a, b) -> a));
    }
   /**
     * Получение всех таблиц из указанной схемы
     */
    public Map<String, TableMetadataDto> getAllTablesBySchemaAndService(String schemaName, String source) {
        return tablesRepository.findAllBySchemaAndService(schemaName, source).stream()
                .collect(Collectors.toMap(TableMetadataDto::getFqn, t -> t, (a, b) -> a));
    }

    /**
     * Комплексный метод: получить всё (базы, схемы, таблицы)
     * создал для теста , чтоб проверить берутся ли нормально данные  из целевой бд
     */
    public void getAllMetadata(String schemaName) {
        Map<String, DatabaseMetadataDto> allDatabasesBySchema = getAllDatabasesBySchema(schemaName);
        Map<String, SchemaMetadataDto> allSchemasBySchema = getAllSchemasBySchema(schemaName);
        Map<String, TableMetadataDto> allTablesBySchema = getAllTablesBySchema(schemaName);
    }
   /**
     * Комплексный метод: получить всё (базы, схемы, таблицы)
     * создал для теста , чтоб проверить берутся ли нормально данные  из целевой бд
     */
    public void getAllMetadata(String schemaName, String source) {
        Map<String, DatabaseMetadataDto> allDatabasesBySchema = getAllDatabasesBySchemaAndService(schemaName, source);
        Map<String, SchemaMetadataDto> allSchemasBySchema = getAllSchemasBySchemaAndService(schemaName, source);
        Map<String, TableMetadataDto> allTablesBySchema = getAllTablesBySchemaAndService(schemaName, source);
    }
}
