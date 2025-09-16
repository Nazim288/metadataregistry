package com.gpbapp.metadataregistry.service;

import com.gpbapp.metadataregistry.enums.OrdaBaseType;

public interface OrdaSyncStrategy {
    OrdaBaseType getType();
    void sync();
}

