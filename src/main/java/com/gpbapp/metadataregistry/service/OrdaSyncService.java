package com.gpbapp.metadataregistry.service;

import com.gpbapp.metadataregistry.enums.OrdaBaseType;

public interface OrdaSyncService {
    // работает с кешем сформинованным из орды
    void ordaSync(OrdaBaseType type);

}
