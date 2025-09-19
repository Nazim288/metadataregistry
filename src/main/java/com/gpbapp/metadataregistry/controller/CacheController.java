package com.gpbapp.metadataregistry.controller;

import com.gpbapp.metadataregistry.service.OrdaCacheInitializer;
import com.gpbapp.metadataregistry.service.OrdaCacheService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/cache")
public class CacheController {

    public CacheController(OrdaCacheService cacheService, OrdaCacheInitializer cacheInitializer) {
        this.cacheService = cacheService;
        this.cacheInitializer = cacheInitializer;
    }

    private final OrdaCacheService cacheService;
    private final OrdaCacheInitializer cacheInitializer;


    @GetMapping("/size")
    public ResponseEntity<String> getSizeCache() {
        try {
            String size = cacheService.getSize();
            return ResponseEntity.ok(size);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Ошибка при обновлении кеша");
        }
    }

    @PostMapping("/cache/init")
    public ResponseEntity<String> initCache() {
        cacheInitializer.init();
        return ResponseEntity.ok("✅ Orda cache initialized manually");
    }

}