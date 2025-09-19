package com.gpbapp.metadataregistry.service;

import com.gpbapp.metadataregistry.dto.*;
import com.gpbapp.metadataregistry.dto.orda.*;
import com.gpbapp.metadataregistry.properties.OrdaEndpointsGet;
import com.gpbapp.metadataregistry.properties.OrdaEndpointsPost;
import com.gpbapp.metadataregistry.properties.OrdaProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import java.util.List;

import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;


@Service
public class OrdaService {
    private static final Logger log = LoggerFactory.getLogger(OrdaService.class);

    public OrdaService(RestTemplate restTemplate, OrdaProperties ordaProperties, OrdaEndpointsPost ordaEndpointsPost, OrdaEndpointsGet ordaEndpointsGet, OrdaClientService ordaClientService) {
        this.restTemplate = restTemplate;
        this.ordaProperties = ordaProperties;
        this.ordaEndpointsPost = ordaEndpointsPost;
        this.ordaEndpointsGet = ordaEndpointsGet;
        this.ordaClientService = ordaClientService;
    }

    private final RestTemplate restTemplate;
    private final OrdaProperties ordaProperties;
    private final OrdaEndpointsPost ordaEndpointsPost;
    private final OrdaEndpointsGet ordaEndpointsGet;
    private final OrdaClientService ordaClientService;




    public OrdaServiceDto createService(OrdaServiceCreateDto dto) {
        try {
            ResponseEntity<OrdaServiceDto> response =
                    restTemplate.postForEntity(
                            ordaEndpointsPost.getServices(),
                            dto,
                            OrdaServiceDto.class
                    );

            OrdaServiceDto body = response.getBody();
            if (body == null) {
                throw new IllegalStateException("Создание сервиса: пустой ответ от Orda");
            }

            return body;
        } catch (Exception e) {
            throw new RuntimeException("Ошибка при создании сервиса в Orda", e);
        }
    }


    public OrdaDbDto createDatabase(OrdaBaseCreateDto requestDto) {
        try {
            ResponseEntity<OrdaDbDto> response =
                    restTemplate.postForEntity(
                            ordaEndpointsPost.getDatabases(),
                            requestDto,
                            OrdaDbDto.class
                    );

            OrdaDbDto body = response.getBody();
            if (body == null) {
                throw new IllegalStateException("Создание базы: пустой ответ от Orda");
            }
            return body;
        } catch (Exception e) {
            throw new RuntimeException("Ошибка при создании базы в Orda", e);
        }
    }

    public OrdaDatabaseSchemaDto createSchema(OrdaSchemaCreateDTO dto) {
        try {
            ResponseEntity<OrdaDatabaseSchemaDto> response =
                    restTemplate.postForEntity(
                            ordaEndpointsPost.getSchemas(),
                            dto,
                            OrdaDatabaseSchemaDto.class
                    );

            OrdaDatabaseSchemaDto body = response.getBody();
            if (body == null) {
                throw new IllegalStateException("Создание схемы: пустой ответ от Orda");
            }
            return body;
        } catch (Exception e) {
            throw new RuntimeException("Ошибка при создании схемы в Orda", e);
        }
    }

    public OrdaTableDto createTable(OrdaTableCreateDTO dto) {
        try {
            ResponseEntity<OrdaTableDto> response =
                    restTemplate.postForEntity(
                            ordaEndpointsPost.getTables(),
                            dto,
                            OrdaTableDto.class
                    );

            OrdaTableDto body = response.getBody();
            if (body == null) {
                throw new IllegalStateException("Создание таблицы: пустой ответ от Orda");
            }
            return body;
        } catch (Exception e) {
            throw new RuntimeException("Ошибка при создании таблицы в Orda", e);
        }
    }

    public OrdaTableDto createOrUpdateTable(OrdaTableCreateDTO dto) {
        try {
            ResponseEntity<OrdaTableDto> response = restTemplate.exchange(
                    ordaEndpointsPost.getTables(),
                    HttpMethod.PUT,
                    new HttpEntity<>(dto),
                    OrdaTableDto.class
            );

            OrdaTableDto body = response.getBody();
            if (body == null) {
                throw new IllegalStateException("Создание/обновление таблицы: пустой ответ от Orda");
            }
            return body;
        } catch (Exception e) {
            throw new RuntimeException("Ошибка при создании/обновлении таблицы в Orda", e);
        }
    }

    public List<OrdaServiceDto> getServices() {
        OrdaServicesResponseDto response = restTemplate.getForObject(ordaEndpointsGet.getServices(), OrdaServicesResponseDto.class);
        return response != null ? response.getData() : List.of();
    }


    public List<OrdaTableDto> getTables() {
        UriComponentsBuilder builder = UriComponentsBuilder
                .fromUriString(ordaEndpointsGet.getTables())
                .queryParam("limit", 1000000);

        OrdaTablesResponseDto response = restTemplate.getForObject(builder.toUriString(), OrdaTablesResponseDto.class);

        return response != null ? response.getData() : List.of();

    }

    public OrdaTablesResponseDto getTablesPage(int limit, String after) {
        UriComponentsBuilder builder = UriComponentsBuilder
                .fromUriString(ordaEndpointsGet.getTables())
                .queryParam("limit", limit);

        if (after != null && !after.isBlank()) {
            builder.queryParam("after", after);
        }

        return restTemplate.getForObject(
                builder.toUriString(),
                OrdaTablesResponseDto.class
        );
    }

    public List<OrdaDatabaseSchemaDto> getSchemas() {
        OrdaSchemasResponseDto response = restTemplate.getForObject(ordaEndpointsGet.getSchemas(), OrdaSchemasResponseDto.class);
        return response != null ? response.getData() : List.of();

    }

    public  List<OrdaDbDto> getDatabases() {
        OrdaDatabaseResponseDto response = restTemplate.getForObject(ordaEndpointsGet.getDatabases(), OrdaDatabaseResponseDto.class);
        return response != null ? response.getData() : List.of();

    }


}
