package com.gpbapp.metadataregistry.dto;

public class OrdaConfigCreateDto {
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getHostPort() {
        return hostPort;
    }

    public void setHostPort(String hostPort) {
        this.hostPort = hostPort;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getDatabase() {
        return database;
    }

    public void setDatabase(String database) {
        this.database = database;
    }

    private String type;      // Postgres, Mssql, etc.
    private String hostPort;  // host:port
    private String username;
    private String password;
    private String database;

    public OrdaConfigCreateDto() {
    }
}
