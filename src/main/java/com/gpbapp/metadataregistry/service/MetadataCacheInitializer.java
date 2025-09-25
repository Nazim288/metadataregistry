package com.gpbapp.metadataregistry.service;

import com.gpbapp.metadataregistry.dto.metadata.DatabaseMetadataDto;
import com.gpbapp.metadataregistry.dto.metadata.MetadataCacheDto;
import com.gpbapp.metadataregistry.dto.metadata.SchemaMetadataDto;
import com.gpbapp.metadataregistry.dto.metadata.TableMetadataDto;
import com.gpbapp.metadataregistry.pepository.MetadataCacheRepository;
import com.gpbapp.metadataregistry.properties.MetadataSchemasProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MetadataCacheInitializer {

    private static final Logger log = LoggerFactory.getLogger(MetadataCacheInitializer.class);

    private final MetadataCacheRepository metadataCacheRepository;
    private final MetadataPgCache metadataPgCache;
    private final MetadataSchemasProperties properties;

    public MetadataCacheInitializer(MetadataCacheRepository metadataCacheRepository,
                                    MetadataPgCache metadataPgCache,
                                    MetadataSchemasProperties properties) {
        this.metadataCacheRepository = metadataCacheRepository;
        this.metadataPgCache = metadataPgCache;
        this.properties = properties;
    }

    @EventListener(ApplicationReadyEvent.class)
    public void loadCacheOnStartup() {
        String schema = properties.getPostgres();

        log.info("Loading metadata cache from schema '{}' ...", schema);

        List<MetadataCacheDto> cacheDtos = metadataCacheRepository.findAll(schema);

        cacheDtos.forEach(dto -> {
            switch (dto.getObjectType()) {
                case DATABASE -> {
                    DatabaseMetadataDto db = new DatabaseMetadataDto();
                    db.setFqn(dto.getFqn());
                    db.setServiceName(dto.getServiceName());
                    db.setHashData(dto.getHashData());
                    metadataPgCache.putDatabase(db);
                }
                case SCHEMA -> {
                    SchemaMetadataDto schemaDto = new SchemaMetadataDto();
                    schemaDto.setFqn(dto.getFqn());
                    schemaDto.setServiceName(dto.getServiceName());
                    schemaDto.setHashData(dto.getHashData());
                    metadataPgCache.putSchema(schemaDto);
                }
                case TABLE -> {
                    TableMetadataDto tableDto = new TableMetadataDto();
                    tableDto.setFqn(dto.getFqn());
                    tableDto.setServiceName(dto.getServiceName());
                    tableDto.setHashData(dto.getHashData());
                    metadataPgCache.putTable(tableDto);
                }
                default -> log.warn("Unknown object type {}", dto.getObjectType());
            }
        });

        log.info("Loaded {} objects into in-memory cache", cacheDtos.size());
    }
}

