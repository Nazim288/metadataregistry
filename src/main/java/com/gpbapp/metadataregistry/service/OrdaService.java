package com.gpbapp.metadataregistry.service;

import com.gpbapp.metadataregistry.dto.orda.*;
import com.gpbapp.metadataregistry.properties.OrdaEndpointsGet;
import com.gpbapp.metadataregistry.properties.OrdaEndpointsPost;
import com.gpbapp.metadataregistry.properties.OrdaProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.*;
import org.springframework.stereotype.Service;

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

    public OrdaTableDto updateTable(OrdaTableCreateDTO dto) {
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
    public  OrdaServiceDto updateService(OrdaServiceCreateDto dto) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            HttpEntity<OrdaServiceCreateDto> requestEntity = new HttpEntity<>(dto, headers);


            ResponseEntity<OrdaServiceDto> response =
                    restTemplate.exchange(
                            ordaEndpointsPost.getServices(),
                            HttpMethod.PUT,
                            new HttpEntity<>(dto),
                            OrdaServiceDto.class
                    );

            return response.getBody();
        } catch (Exception e) {
            throw new RuntimeException("Ошибка при обновлении сервиса в Orda", e);
        }
    }

    public OrdaDbDto updateDatabase(OrdaBaseCreateDto dto) {
        try {
            ResponseEntity<OrdaDbDto> response =
                    restTemplate.exchange(
                            ordaEndpointsPost.getDatabases(),
                            HttpMethod.PUT,
                            new HttpEntity<>(dto),
                            OrdaDbDto.class
                    );

            OrdaDbDto body = response.getBody();
            if (body == null) {
                throw new IllegalStateException("Обновление базы: пустой ответ от Orda");
            }

            return body;
        } catch (Exception e) {
            throw new RuntimeException("Ошибка при обновлении базы в Orda", e);
        }
    }

    public OrdaDatabaseSchemaDto updateSchema(OrdaSchemaCreateDTO dto) {
        try {
            ResponseEntity<OrdaDatabaseSchemaDto> response =
                    restTemplate.exchange(
                            ordaEndpointsPost.getSchemas(),
                            HttpMethod.PUT,
                            new HttpEntity<>(dto),
                            OrdaDatabaseSchemaDto.class
                    );

            OrdaDatabaseSchemaDto body = response.getBody();
            if (body == null) {
                throw new IllegalStateException("Обновление схемы: пустой ответ от Orda");
            }

            return body;
        } catch (Exception e) {
            throw new RuntimeException("Ошибка при обновлении схемы в Orda", e);
        }
    }


    public OrdaServicesResponseDto getServices() {
        OrdaServicesResponseDto response = restTemplate.getForObject(ordaEndpointsGet.getServices(), OrdaServicesResponseDto.class);
        return response;
    }


    public OrdaTablesResponseDto getTables() {
        UriComponentsBuilder builder = UriComponentsBuilder
                .fromUriString(ordaEndpointsGet.getTables())
                .queryParam("limit", 1000000);

        OrdaTablesResponseDto response = restTemplate.getForObject(builder.toUriString(), OrdaTablesResponseDto.class);

        return response ;

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

    public OrdaSchemasResponseDto getSchemas() {
        OrdaSchemasResponseDto response = restTemplate.getForObject(ordaEndpointsGet.getSchemas(), OrdaSchemasResponseDto.class);
        return response ;

    }

    public  OrdaDatabaseResponseDto getDatabases() {
        OrdaDatabaseResponseDto response = restTemplate.getForObject(ordaEndpointsGet.getDatabases(), OrdaDatabaseResponseDto.class);
        return response;

    }
    /**
     * Удаление Database Service по имени (мягкое, нерекурсивное).
     * Сервис удалится только если в нём нет баз.
     */
    public void deleteServiceSoft(String name) {
        String url = String.format(
                "%s/name/%s?hardDelete=false&recursive=false",
                ordaEndpointsPost.getServices(),
                name
        );
        try {
            restTemplate.delete(url);
        } catch (Exception e) {
            throw new RuntimeException("Ошибка при мягком удалении сервиса в Orda: " + name, e);
        }
    }

    /**
     * Удаление Database Service по имени (мягкое, рекурсивное).
     * Удаляет сервис вместе с базами и таблицами, но без hardDelete.
     */
    public void deleteServiceSoftRecursive(String name) {
        String url = String.format(
                "%s/name/%s?hardDelete=false&recursive=true",
                ordaEndpointsPost.getServices(),
                name
        );
        try {
            restTemplate.delete(url);
        } catch (Exception e) {
            throw new RuntimeException("Ошибка при мягком рекурсивном удалении сервиса в Orda: " + name, e);
        }
    }

    /**
     * Удаление Database Service по имени (жёсткое, рекурсивное).
     * Полностью и безвозвратно удаляет сервис и все дочерние объекты.
     */
    public void deleteServiceHardRecursive(String name) {
        String url = String.format(
                "%s/name/%s?hardDelete=true&recursive=true",
                ordaEndpointsPost.getServices(),
                name
        );
        try {
            restTemplate.delete(url);
        } catch (Exception e) {
            throw new RuntimeException("Ошибка при жёстком рекурсивном удалении сервиса в Orda: " + name, e);
        }
    }

    /**
     * Удаление базы данных по FQN (мягкое, нерекурсивное).
     * Можно удалить только если нет таблиц.
     */
    public void deleteDatabaseSoft(String fqn) {
        String url = String.format(
                "%s/name/%s?hardDelete=false&recursive=false",
                ordaEndpointsPost.getDatabases(),
                fqn
        );
        try {
            restTemplate.delete(url);
        } catch (Exception e) {
            throw new RuntimeException("Ошибка при мягком удалении базы данных в Orda: " + fqn, e);
        }
    }

    /**
     * Удаление базы данных по FQN (мягкое, рекурсивное).
     * Удаляет базу и все таблицы, но не hardDelete.
     */
    public void deleteDatabaseSoftRecursive(String fqn) {
        String url = String.format(
                "%s/name/%s?hardDelete=false&recursive=true",
                ordaEndpointsPost.getDatabases(),
                fqn
        );
        try {
            restTemplate.delete(url);
        } catch (Exception e) {
            throw new RuntimeException("Ошибка при мягком рекурсивном удалении базы данных в Orda: " + fqn, e);
        }
    }

    /**
     * Удаление базы данных по FQN (жёсткое, рекурсивное).
     * Удаляет базу и все таблицы без возможности восстановления.
     */
    public void deleteDatabaseHardRecursive(String fqn) {
        String url = String.format(
                "%s/name/%s?hardDelete=true&recursive=true",
                ordaEndpointsPost.getDatabases(),
                fqn
        );
        try {
            restTemplate.delete(url);
        } catch (Exception e) {
            throw new RuntimeException("Ошибка при жёстком рекурсивном удалении базы данных в Orda: " + fqn, e);
        }
    }

    /**
     * Удаление схемы по FQN (мягкое, нерекурсивное).
     * Можно удалить только если в схеме нет таблиц.
     */
    public void deleteSchemaSoft(String fqn) {
        String url = String.format(
                "%s/name/%s?hardDelete=false&recursive=false",
                ordaEndpointsPost.getSchemas(),
                fqn
        );
        try {
            restTemplate.delete(url);
        } catch (Exception e) {
            throw new RuntimeException("Ошибка при мягком удалении схемы в Orda: " + fqn, e);
        }
    }

    /**
     * Удаление схемы по FQN (мягкое, рекурсивное).
     * Удаляет схему и все таблицы, но остаётся возможность восстановления.
     */
    public void deleteSchemaSoftRecursive(String fqn) {
        String url = String.format(
                "%s/name/%s?hardDelete=false&recursive=true",
                ordaEndpointsPost.getSchemas(),
                fqn
        );
        try {
            restTemplate.delete(url);
        } catch (Exception e) {
            throw new RuntimeException("Ошибка при мягком рекурсивном удалении схемы в Orda: " + fqn, e);
        }
    }

    /**
     * Удаление схемы по FQN (жёсткое, рекурсивное).
     * Полностью и безвозвратно удаляет схему и все её таблицы.
     */
    public void deleteSchemaHardRecursive(String fqn) {
        String url = String.format(
                "%s/name/%s?hardDelete=true&recursive=true",
                ordaEndpointsPost.getSchemas(),
                fqn
        );
        try {
            restTemplate.delete(url);
        } catch (Exception e) {
            throw new RuntimeException("Ошибка при жёстком рекурсивном удалении схемы в Orda: " + fqn, e);
        }
    }
    /**
     * Удаление таблицы по FQN (мягкое).
     * Таблица помечается удалённой, можно восстановить.
     */
    public void deleteTableSoft(String fqn) {
        String url = String.format(
                "%s/name/%s?hardDelete=false&recursive=false",
                ordaEndpointsPost.getTables(),
                fqn
        );
        try {
            restTemplate.delete(url);
        } catch (Exception e) {
            throw new RuntimeException("Ошибка при мягком удалении таблицы в Orda: " + fqn, e);
        }
    }

    /**
     * Удаление таблицы по FQN (мягкое, рекурсивное).
     * На практике то же самое, что и мягкое без рекурсии,
     * так как у таблицы нет "детей", но параметр поддержан для единообразия.
     */
    public void deleteTableSoftRecursive(String fqn) {
        String url = String.format(
                "%s/name/%s?hardDelete=false&recursive=true",
                ordaEndpointsPost.getTables(),
                fqn
        );
        try {
            restTemplate.delete(url);
        } catch (Exception e) {
            throw new RuntimeException("Ошибка при мягком рекурсивном удалении таблицы в Orda: " + fqn, e);
        }
    }

    /**
     * Удаление таблицы по FQN (жёсткое).
     * Полностью и безвозвратно удаляет таблицу.
     */
    public void deleteTableHard(String fqn) {
        String url = String.format(
                "%s/name/%s?hardDelete=true&recursive=true",
                ordaEndpointsPost.getTables(),
                fqn
        );
        try {
            restTemplate.delete(url);
        } catch (Exception e) {
            throw new RuntimeException("Ошибка при жёстком удалении таблицы в Orda: " + fqn, e);
        }
    }

}
