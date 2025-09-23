package com.gpbapp.metadataregistry.dto.orda;

public class ConfigDto {
    private String hostPort;
    private String username;
    private AuthType authType;
    private String database;

    public ConfigDto(String hostPort, String username, AuthType authType, String database) {
        this.hostPort = hostPort;
        this.username = username;
        this.authType = authType;
        this.database = database;
    }

    public ConfigDto() {
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

    public AuthType getAuthType() {
        return authType;
    }

    public void setAuthType(AuthType authType) {
        this.authType = authType;
    }

    public String getDatabase() {
        return database;
    }

    public void setDatabase(String database) {
        this.database = database;
    }
}
