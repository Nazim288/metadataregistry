package com.gpbapp.metadataregistry.controller;

import com.gpbapp.metadataregistry.enums.OrdaBaseType;

import com.gpbapp.metadataregistry.service.OrdaSyncService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/cache")
public class CacheController {

    private final OrdaSyncService ordaSyncService;

    public CacheController(OrdaSyncService ordaSyncService) {
        this.ordaSyncService = ordaSyncService;
    }

    @PostMapping("/refresh")
    public ResponseEntity<String> refreshCache(OrdaBaseType type) {
        try {
            ordaSyncService.ordaSync(type);
            return ResponseEntity.ok(String.format("Кэш для баз данных типа %s обновлён успешно", type.name()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Ошибка при обновлении кеша");
        }
    }
}