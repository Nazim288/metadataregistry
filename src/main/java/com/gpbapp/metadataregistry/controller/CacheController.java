package com.gpbapp.metadataregistry.controller;

import com.gpbapp.metadataregistry.service.MetadataCacheService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/cache")
public class CacheController {

    public CacheController(MetadataCacheService cacheService) {
        this.cacheService = cacheService;
    }

    private final MetadataCacheService cacheService;  // Сервис для работы с кешем

    @PostMapping("/refresh")
    public ResponseEntity<String> refreshCache() {
        try {
            cacheService.refreshTableMetadataCache();  // Сервисная логика
            return ResponseEntity.ok("Кэш обновлён успешно");
        } catch (Exception e) {
            //log.error("Ошибка при обновлении кеша: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Ошибка при обновлении кеша");
        }
    }
}