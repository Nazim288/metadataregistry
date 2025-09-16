package com.gpbapp.metadataregistry.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;


@Component
@ConfigurationProperties(prefix = "schemas")
public class MetadataSchemasProperties {
    private String postgres;
    private String mssql;
    private String oracle;

    public String getPostgres() {
        return postgres;
    }

    public void setPostgres(String postgres) {
        this.postgres = postgres;
    }

    public String getMssql() {
        return mssql;
    }

    public void setMssql(String mssql) {
        this.mssql = mssql;
    }

    public String getOracle() {
        return oracle;
    }

    public void setOracle(String oracle) {
        this.oracle = oracle;
    }

    public MetadataSchemasProperties(String postgres, String mssql, String oracle) {
        this.postgres = postgres;
        this.mssql = mssql;
        this.oracle = oracle;
    }

    public MetadataSchemasProperties() {
    }
}
