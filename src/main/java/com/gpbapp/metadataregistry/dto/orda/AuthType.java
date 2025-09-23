package com.gpbapp.metadataregistry.dto.orda;

public class AuthType {
    private String password;

    public AuthType() {
    }

    public AuthType(String password) {
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
