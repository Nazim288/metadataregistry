package com.gpbapp.metadataregistry.service;

import com.gpbapp.metadataregistry.enums.OrdaBaseType;

public interface OrdaSyncService {
    void ordaSync(OrdaBaseType type);
}
