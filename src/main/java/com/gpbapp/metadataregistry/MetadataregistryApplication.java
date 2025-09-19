package com.gpbapp.metadataregistry;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class MetadataregistryApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext run = SpringApplication.run(MetadataregistryApplication.class, args);
    }

}
