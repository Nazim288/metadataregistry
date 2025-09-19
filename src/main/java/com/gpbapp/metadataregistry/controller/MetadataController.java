package com.gpbapp.metadataregistry.controller;

import com.gpbapp.metadataregistry.service.MetadataService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/metadata")
public class MetadataController {
    public MetadataController(MetadataService metadataService) {
        this.metadataService = metadataService;
    }

    private final MetadataService metadataService;

    @GetMapping("/all")
    public ResponseEntity<String> getMetadata(String schema) {
        try {
            metadataService.getAllMetadata(schema);
            return ResponseEntity.ok(String.format("Кэш для баз данных типа %s обновлён успешно", schema));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Ошибка при обновлении кеша");
        }
    }

}
