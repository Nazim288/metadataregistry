package com.gpbapp.metadataregistry.exceptions;

import org.springframework.http.HttpStatusCode;

public class OrdaApiException extends RuntimeException {
    private final HttpStatusCode status;
    private final String responseBody;

    public OrdaApiException(HttpStatusCode status, String responseBody) {
        super("Orda API error: " + status + " - " + responseBody);
        this.status = status;
        this.responseBody = responseBody;
    }

    public HttpStatusCode getStatus() {
        return status;
    }

    public String getResponseBody() {
        return responseBody;
    }
}
