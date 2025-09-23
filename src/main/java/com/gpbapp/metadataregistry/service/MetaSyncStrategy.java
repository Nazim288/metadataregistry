package com.gpbapp.metadataregistry.service;

import com.gpbapp.metadataregistry.enums.OrdaBaseType;
import org.springframework.http.ResponseEntity;

public interface MetaSyncStrategy {
    OrdaBaseType getType();
    ResponseEntity<String> sync(String source);
}
