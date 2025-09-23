package com.gpbapp.metadataregistry.service;

import com.gpbapp.metadataregistry.enums.OrdaBaseType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class MetaSyncExecutor {

    private final Map<OrdaBaseType, MetaSyncStrategy> strategies;

    public MetaSyncExecutor(List<MetaSyncStrategy> strategies) {
        this.strategies = strategies.stream()
                .collect(Collectors.toMap(MetaSyncStrategy::getType, s -> s));
    }

    public ResponseEntity<String> sync(String source, OrdaBaseType type) {
        MetaSyncStrategy strategy = strategies.get(type);
        if (strategy == null) {
            throw new IllegalArgumentException("No strategy found for type: " + type);
        }
       return strategy.sync(source);
    }
}
