package com.gpbapp.metadataregistry.service;

import com.gpbapp.metadataregistry.dto.orda.*;
import com.gpbapp.metadataregistry.properties.OrdaEndpoints;

import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Service
public class OrdaService {
    private final RestTemplate restTemplate;
    private final OrdaEndpoints ordaEndpoints;

    public OrdaService(RestTemplate restTemplate,
                       OrdaEndpoints ordaEndpoints) {
        this.restTemplate = restTemplate;
        this.ordaEndpoints = ordaEndpoints;
    }

    // ------------------- CREATE -------------------

    public OrdaServiceDto createService(OrdaServiceCreateDto dto) {
        ResponseEntity<OrdaServiceDto> response =
                restTemplate.postForEntity(
                        ordaEndpoints.getServices(),
                        dto,
                        OrdaServiceDto.class
                );
        OrdaServiceDto body = response.getBody();
        if (body == null) {
            throw new IllegalStateException("Создание сервиса: пустой ответ от Orda");
        }
        return body;
    }

    public OrdaDbDto createDatabase(OrdaBaseCreateDto requestDto) {
        ResponseEntity<OrdaDbDto> response =
                restTemplate.postForEntity(
                        ordaEndpoints.getDatabases(),
                        requestDto,
                        OrdaDbDto.class
                );
        OrdaDbDto body = response.getBody();
        if (body == null) {
            throw new IllegalStateException("Создание базы: пустой ответ от Orda");
        }
        return body;
    }

    public OrdaDatabaseSchemaDto createSchema(OrdaSchemaCreateDTO dto) {
        ResponseEntity<OrdaDatabaseSchemaDto> response =
                restTemplate.postForEntity(
                        ordaEndpoints.getSchemas(),
                        dto,
                        OrdaDatabaseSchemaDto.class
                );
        OrdaDatabaseSchemaDto body = response.getBody();
        if (body == null) {
            throw new IllegalStateException("Создание схемы: пустой ответ от Orda");
        }
        return body;
    }

    public OrdaTableDto createTable(OrdaTableCreateDTO dto) {
        ResponseEntity<OrdaTableDto> response =
                restTemplate.postForEntity(
                        ordaEndpoints.getTables(),
                        dto,
                        OrdaTableDto.class
                );
        OrdaTableDto body = response.getBody();
        if (body == null) {
            throw new IllegalStateException("Создание таблицы: пустой ответ от Orda");
        }
        return body;
    }

    // ------------------- UPDATE -------------------

    public OrdaTableDto updateTable(OrdaTableCreateDTO dto) {
        ResponseEntity<OrdaTableDto> response = restTemplate.exchange(
                ordaEndpoints.getTables(),
                HttpMethod.PUT,
                new HttpEntity<>(dto),
                OrdaTableDto.class
        );
        OrdaTableDto body = response.getBody();
        if (body == null) {
            throw new IllegalStateException("Обновление таблицы: пустой ответ от Orda");
        }
        return body;
    }

    public OrdaServiceDto updateService(OrdaServiceCreateDto dto) {
        ResponseEntity<OrdaServiceDto> response =
                restTemplate.exchange(
                        ordaEndpoints.getServices(),
                        HttpMethod.PUT,
                        new HttpEntity<>(dto),
                        OrdaServiceDto.class
                );
        OrdaServiceDto body = response.getBody();
        if (body == null) {
            throw new IllegalStateException("Обновление сервиса: пустой ответ от Orda");
        }
        return body;
    }

    public OrdaDbDto updateDatabase(OrdaBaseCreateDto dto) {
        ResponseEntity<OrdaDbDto> response =
                restTemplate.exchange(
                        ordaEndpoints.getDatabases(),
                        HttpMethod.PUT,
                        new HttpEntity<>(dto),
                        OrdaDbDto.class
                );
        OrdaDbDto body = response.getBody();
        if (body == null) {
            throw new IllegalStateException("Обновление базы: пустой ответ от Orda");
        }
        return body;
    }

    public OrdaDatabaseSchemaDto updateSchema(OrdaSchemaCreateDTO dto) {
        ResponseEntity<OrdaDatabaseSchemaDto> response =
                restTemplate.exchange(
                        ordaEndpoints.getSchemas(),
                        HttpMethod.PUT,
                        new HttpEntity<>(dto),
                        OrdaDatabaseSchemaDto.class
                );
        OrdaDatabaseSchemaDto body = response.getBody();
        if (body == null) {
            throw new IllegalStateException("Обновление схемы: пустой ответ от Orda");
        }
        return body;
    }

    // ------------------- GET -------------------

    public OrdaServicesResponseDto getServices() {
        return restTemplate.getForObject(ordaEndpoints.getServices(), OrdaServicesResponseDto.class);
    }

    public OrdaTablesResponseDto getTables() {
        UriComponentsBuilder builder = UriComponentsBuilder
                .fromUriString(ordaEndpoints.getTables())
                .queryParam("limit", 1000000);
        return restTemplate.getForObject(builder.toUriString(), OrdaTablesResponseDto.class);
    }

    public OrdaSchemasResponseDto getSchemas() {
        return restTemplate.getForObject(ordaEndpoints.getSchemas(), OrdaSchemasResponseDto.class);
    }

    public OrdaDatabaseResponseDto getDatabases() {
        return restTemplate.getForObject(ordaEndpoints.getDatabases(), OrdaDatabaseResponseDto.class);
    }

    // ------------------- DELETE SERVICES -------------------

    public void deleteServiceSoft(String name) {
        String url = String.format("%s/name/%s?hardDelete=false&recursive=false",
                ordaEndpoints.getServices(), name);
        restTemplate.delete(url);
    }

    public void deleteServiceSoftRecursive(String name) {
        String url = String.format("%s/name/%s?hardDelete=false&recursive=true",
                ordaEndpoints.getServices(), name);
        restTemplate.delete(url);
    }

    public void deleteServiceHardRecursive(String name) {
        String url = String.format("%s/name/%s?hardDelete=true&recursive=true",
                ordaEndpoints.getServices(), name);
        restTemplate.delete(url);
    }

    // ------------------- DELETE DATABASES -------------------

    public void deleteDatabaseSoft(String fqn) {
        String url = String.format("%s/name/%s?hardDelete=false&recursive=false",
                ordaEndpoints.getDatabases(), fqn);
        restTemplate.delete(url);
    }

    public void deleteDatabaseSoftRecursive(String fqn) {
        String url = String.format("%s/name/%s?hardDelete=false&recursive=true",
                ordaEndpoints.getDatabases(), fqn);
        restTemplate.delete(url);
    }

    public void deleteDatabaseHardRecursive(String fqn) {
        String url = String.format("%s/name/%s?hardDelete=true&recursive=true",
                ordaEndpoints.getDatabases(), fqn);
        restTemplate.delete(url);
    }

    // ------------------- DELETE SCHEMAS -------------------

    public void deleteSchemaSoft(String fqn) {
        String url = String.format("%s/name/%s?hardDelete=false&recursive=false",
                ordaEndpoints.getSchemas(), fqn);
        restTemplate.delete(url);
    }

    public void deleteSchemaSoftRecursive(String fqn) {
        String url = String.format("%s/name/%s?hardDelete=false&recursive=true",
                ordaEndpoints.getSchemas(), fqn);
        restTemplate.delete(url);
    }

    public void deleteSchemaHardRecursive(String fqn) {
        String url = String.format("%s/name/%s?hardDelete=true&recursive=true",
                ordaEndpoints.getSchemas(), fqn);
        restTemplate.delete(url);
    }

    // ------------------- DELETE TABLES -------------------

    public void deleteTableSoft(String fqn) {
        String url = String.format("%s/name/%s?hardDelete=false&recursive=false",
                ordaEndpoints.getTables(), fqn);
        restTemplate.delete(url);
    }

    public void deleteTableSoftRecursive(String fqn) {
        String url = String.format("%s/name/%s?hardDelete=false&recursive=true",
                ordaEndpoints.getTables(), fqn);
        restTemplate.delete(url);
    }

    public void deleteTableHard(String fqn) {
        String url = String.format("%s/name/%s?hardDelete=true&recursive=true",
                ordaEndpoints.getTables(), fqn);
        restTemplate.delete(url);
    }
}
