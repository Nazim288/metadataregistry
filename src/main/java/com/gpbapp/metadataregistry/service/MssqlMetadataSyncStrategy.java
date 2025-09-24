package com.gpbapp.metadataregistry.service;

import com.gpbapp.metadataregistry.enums.OrdaBaseType;
import com.gpbapp.metadataregistry.service.MetaSyncStrategy;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class MssqlMetadataSyncStrategy implements MetaSyncStrategy {
    @Override
    public OrdaBaseType getType() {
        return null;
    }

    @Override
    public ResponseEntity<String> sync(String source) {
       return ResponseEntity.ok("синхронизация прошла");

    }
}
