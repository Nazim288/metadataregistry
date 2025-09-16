package com.gpbapp.metadataregistry.config;


import com.gpbapp.metadataregistry.properties.HttpClientProperties;
import com.gpbapp.metadataregistry.properties.OrdaProperties;
import org.apache.hc.client5.http.config.RequestConfig;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.util.Timeout;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.DefaultUriBuilderFactory;
import org.springframework.http.HttpHeaders;

import java.util.List;


@Configuration
public class RestTemplateConfig {
    private final OrdaProperties ordaProperties;
    private final HttpClientProperties httpClientProperties;


    @Bean
    public RestTemplate ordaRestTemplate() {
        RequestConfig requestConfig = RequestConfig.custom()
                .setConnectionRequestTimeout(Timeout.ofSeconds(httpClientProperties.getConnectTimeout()))
                .setResponseTimeout(Timeout.ofSeconds(httpClientProperties.getReadTimeout()))
                .build();

        CloseableHttpClient httpClient = HttpClients.custom()
                .setDefaultRequestConfig(requestConfig)
                .build();

        HttpComponentsClientHttpRequestFactory requestFactory =
                new HttpComponentsClientHttpRequestFactory(httpClient);

        RestTemplate restTemplate = new RestTemplate(requestFactory);

        restTemplate.setUriTemplateHandler(
                new DefaultUriBuilderFactory(ordaProperties.getBaseUrl())
        );

        restTemplate.getInterceptors().add((request, body, execution) -> {
            HttpHeaders headers = request.getHeaders();
            if (!headers.containsKey(HttpHeaders.CONTENT_TYPE)) {
                headers.setContentType(MediaType.APPLICATION_JSON);
            }
            headers.setAccept(List.of(MediaType.APPLICATION_JSON));

            String token = ordaProperties.getToken();
            if (token != null && !token.isBlank()) {
                headers.setBearerAuth(token);
            }
            return execution.execute(request, body);
        });

        return restTemplate;

    }
    public RestTemplateConfig(OrdaProperties ordaProperties, HttpClientProperties httpClientProperties) {
        this.ordaProperties = ordaProperties;
        this.httpClientProperties = httpClientProperties;
    }
}
