package com.gpbapp.metadataregistry.service.impl;

import com.gpbapp.metadataregistry.enums.OrdaBaseType;
import com.gpbapp.metadataregistry.service.OrdaSyncStrategy;
import org.springframework.stereotype.Service;

@Service
public class MssqlOrdaSyncStrategy implements OrdaSyncStrategy {

    @Override
    public OrdaBaseType getType() {
        return OrdaBaseType.MSSQL;
    }

    @Override
    public void sync() {

        System.out.println("Synced MSSQL metadata, total sources: ");
    }
}
