package com.gpbapp.metadataregistry.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gpbapp.metadataregistry.properties.OrdaProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.MediaType;
import org.springframework.http.HttpEntity;

@Service
public class OrdaClientService {
    private static final Logger log = LoggerFactory.getLogger(OrdaClientService.class);
    private final RestTemplate restTemplate;
    private final OrdaProperties ordaProperties;

    public OrdaClientService(RestTemplate restTemplate, OrdaProperties ordaProperties) {
        this.restTemplate = restTemplate;
        this.ordaProperties = ordaProperties;
    }

    /**
     * Универсальный POST-запрос к Орде
     * @param endpoint endpoint Орды, например "/api/v1/databases"
     * @param requestBody объект запроса (DatabaseDto, DatabaseSchemaDto, TableDto)
     * @param responseType класс ответа, например DatabaseDto.class
     * @param <T> тип тела запроса
     * @param <R> тип тела ответа
     * @return объект ответа от Орды
     */
    public <T, R> R postToOrda(String endpoint, T requestBody, Class<R> responseType) {
        String url = ordaProperties.getBaseUrl() + endpoint;

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(ordaProperties.getToken());

        try {
            ObjectMapper mapper = new ObjectMapper();
            String json = mapper.writeValueAsString(requestBody);
            log.info(">>> POST {} body = {}", url, json);
        } catch (Exception e) {
            log.error("Не удалось сериализовать тело запроса", e);
        }

        HttpEntity<T> request = new HttpEntity<>(requestBody, headers);

        return restTemplate.exchange(url, HttpMethod.POST, request, responseType).getBody();
    }
}
