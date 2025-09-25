package com.gpbapp.metadataregistry.controller;

import com.gpbapp.metadataregistry.service.MetadataPgCache;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/metadata")
public class MetadataController {

    public MetadataController( MetadataPgCache metadataPgCache) {
        this.metadataPgCache = metadataPgCache;
    }

    private final MetadataPgCache metadataPgCache;

    @GetMapping("/cache/size")
    public ResponseEntity<String> getSize() {
        try {
            String size = metadataPgCache.getSize();
            return ResponseEntity.ok(String.format(size));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Ошибка при получении размера кеша");
        }
    }

}
