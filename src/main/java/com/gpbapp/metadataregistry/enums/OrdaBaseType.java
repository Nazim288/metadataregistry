package com.gpbapp.metadataregistry.enums;

public enum OrdaBaseType {
    POSTGRES("Postgres"),
    MYSQL("Mysql"),
    MSSQL("Mssql"),
    ORACLE("Oracle");

    private final String serviceType;

    OrdaBaseType(String serviceType) {
        this.serviceType = serviceType;
    }

    public String getServiceType() {
        return serviceType;
    }
}
