package com.gpbapp.metadataregistry.exceptions;

import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.client.DefaultResponseErrorHandler;
import java.io.IOException;

public class RestTemplateResponseErrorHandler extends DefaultResponseErrorHandler {
    @Override
    public void handleError(ClientHttpResponse response) throws IOException {
        System.err.println("Ошибка при вызове Orda API: " + response.getStatusCode());
        super.handleError(response);
    }
}
