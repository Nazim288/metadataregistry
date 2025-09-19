package com.gpbapp.metadataregistry.service.impl;

import com.gpbapp.metadataregistry.enums.OrdaBaseType;
import com.gpbapp.metadataregistry.service.OrdaSyncStrategy;
import org.springframework.stereotype.Service;


@Service
public class OracleOrdaSyncStrategy implements OrdaSyncStrategy {

    @Override
    public OrdaBaseType getType() {
        return OrdaBaseType.ORACLE;
    }

    @Override
    public void sync() {
        System.out.println("Synced Oracle metadata, total sources: ");
    }
}
