package com.gpbapp.metadataregistry.controller;

import com.gpbapp.metadataregistry.service.MetadataCache;
import com.gpbapp.metadataregistry.service.MetadataService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/metadata")
public class MetadataController {

    public MetadataController(MetadataService metadataService, MetadataCache metadataCache) {
        this.metadataService = metadataService;
        this.metadataCache = metadataCache;
    }

    private final MetadataService metadataService;
    private final MetadataCache metadataCache;

    @GetMapping("/all")
    public ResponseEntity<String> getMetadata(String schema) {
        try {
            metadataService.getAllMetadata(schema);
            return ResponseEntity.ok(String.format("Кэш для баз данных типа %s обновлён успешно", schema));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Ошибка при обновлении кеша");
        }
    }

    @PostMapping("/cache")
    public ResponseEntity<String> loadCache(String schema) {
        try {
            String response = metadataCache.loadAll(schema);
            return ResponseEntity.ok(String.format("Кэш для баз данных типа %s обновлён успешно, загруженно: %s ", schema, response));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Ошибка при обновлении кеша");
        }
    }

    @GetMapping("/cache/size")
    public ResponseEntity<String> getSize() {
        try {
            String size = metadataCache.getSize();
            return ResponseEntity.ok(String.format(size));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Ошибка при получении размера кеша");
        }
    }

}
