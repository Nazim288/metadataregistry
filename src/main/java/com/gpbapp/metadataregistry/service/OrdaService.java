package com.gpbapp.metadataregistry.service;

import com.gpbapp.metadataregistry.dto.*;
import com.gpbapp.metadataregistry.properties.OrdaEndpointsPost;
import com.gpbapp.metadataregistry.properties.OrdaProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import java.util.List;

import org.springframework.web.client.RestTemplate;


@Service
public class OrdaService {
    private static final Logger log = LoggerFactory.getLogger(OrdaService.class);
    private final RestTemplate restTemplate;
    private final OrdaProperties ordaProperties;
    private final OrdaEndpointsPost ordaEndpoints;
    private final OrdaClientService ordaClientService;

    public OrdaService(RestTemplate restTemplate, OrdaProperties ordaProperties, OrdaEndpointsPost ordaEndpoints, OrdaClientService ordaClientService) {
        this.restTemplate = restTemplate;
        this.ordaProperties = ordaProperties;
        this.ordaEndpoints = ordaEndpoints;
        this.ordaClientService = ordaClientService;
    }



    public String createService(OrdaServiceCreateDto dto) {
        try {
            ResponseEntity<String> response =
                    restTemplate.postForEntity(ordaEndpoints.getServices(), dto, String.class);
            String body = response.getBody();

            if (body == null || body.isBlank()) {
                throw new IllegalStateException("Создание базы: пустой ответ от Orda");
            }
            return response.getBody();
        } catch (Exception e) {
            throw new RuntimeException("Ошибка при создании базы в Orda", e);
        }
    }

    public String createDatabase(OrdaBaseCreateDto requestDto) {
        try {
            ResponseEntity<String> response =
                    restTemplate.postForEntity(ordaEndpoints.getDatabases(), requestDto, String.class);
            String body = response.getBody();

            if (body == null || body.isBlank()) {
                throw new IllegalStateException("Создание базы: пустой ответ от Orda");
            }
            return response.getBody();
        } catch (Exception e) {
            throw new RuntimeException("Ошибка при создании базы в Orda", e);
        }
    }

    public String createSchema(OrdaSchemaCreateDTO dto) {
        try {
            ResponseEntity<String> response =
                    restTemplate.postForEntity(ordaEndpoints.getSchemas(), dto, String.class);
            String body = response.getBody();

            if (body == null || body.isBlank()) {
                throw new IllegalStateException("Создание базы: пустой ответ от Orda");
            }
            return response.getBody();
        } catch (Exception e) {
            throw new RuntimeException("Ошибка при создании базы в Orda", e);
        }
    }

    public String createTable(OrdaTableCreateDTO dto) {
        try {
            ResponseEntity<String> response =
                    restTemplate.postForEntity(ordaEndpoints.getTables(), dto, String.class);
            String body = response.getBody();

            if (body == null || body.isBlank()) {
                throw new IllegalStateException("Создание базы: пустой ответ от Orda");
            }
            return response.getBody();
        } catch (Exception e) {
            throw new RuntimeException("Ошибка при создании базы в Orda", e);
        }
    }
    public String createOrUpdateTable(OrdaTableCreateDTO dto) {
        try {

            ResponseEntity<String> response = restTemplate.exchange(
                    ordaEndpoints.getTables(),
                    HttpMethod.PUT,
                    new HttpEntity<>(dto), // ✅ Оборачиваем
                    String.class
            );;

            String body = response.getBody();

            if (body == null || body.isBlank()) {
                throw new IllegalStateException("Создание/обновление таблицы: пустой ответ от Orda");
            }
            return body;
        } catch (Exception e) {
            throw new RuntimeException("Ошибка при создании/обновлении таблицы в Orda", e);
        }
    }

    public List<DatabaseDto> getServices() {
        OrdaServicesResponseDto response = restTemplate.getForObject(ordaEndpoints.getServices(), OrdaServicesResponseDto.class);
        return response != null ? response.getData() : List.of();
    }


    public List<OrdaTableDto> getTable() {
        OrdaTablesResponseDto response = restTemplate.getForObject(ordaEndpoints.getTables(), OrdaTablesResponseDto.class);
        return response != null ? response.getData() : List.of();

    }

    public List<OrdaDatabaseSchemaDto> getSchema() {
        OrdaSchemasResponseDto response = restTemplate.getForObject(ordaEndpoints.getSchemas(), OrdaSchemasResponseDto.class);
        return response != null ? response.getData() : List.of();

    }

    public  List<OrdaTableDto> getDatabase() {
        OrdaTablesResponseDto response = restTemplate.getForObject(ordaEndpoints.getDatabases(), OrdaTablesResponseDto.class);
        return response != null ? response.getData() : List.of();

    }


}
