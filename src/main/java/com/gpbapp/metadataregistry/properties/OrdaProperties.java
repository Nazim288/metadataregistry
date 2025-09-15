package com.gpbapp.metadataregistry.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@ConfigurationProperties(prefix = "orda")
@Component
public class OrdaProperties {
    public OrdaProperties(String baseUrl, String token) {
        this.baseUrl = baseUrl;
        this.token = token;
    }

    private String baseUrl;
    private String token;


    public String getBaseUrl() {
        return baseUrl;
    }

    public void setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public OrdaProperties() {
    }
}
