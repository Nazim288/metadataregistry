package com.gpbapp.metadataregistry.service.impl;

import com.gpbapp.metadataregistry.enums.OrdaBaseType;
import com.gpbapp.metadataregistry.service.OrdaSyncService;
import com.gpbapp.metadataregistry.service.OrdaSyncStrategy;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class OrdaSyncServiceImpl implements OrdaSyncService {
    private final Map<OrdaBaseType, OrdaSyncStrategy> strategies;

    public OrdaSyncServiceImpl(List<OrdaSyncStrategy> strategyList) {
        this.strategies = strategyList.stream()
                .collect(Collectors.toMap(OrdaSyncStrategy::getType, s -> s));
    }

    @Override
    public void ordaSync(OrdaBaseType type) {
        OrdaSyncStrategy strategy = strategies.get(type);
        if (strategy == null) {
            throw new IllegalArgumentException("Unsupported DB type: " + type);
        }
        strategy.sync();
    }
}
