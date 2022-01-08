package com.launchcode.AroundTownServer.models;

import java.io.Serializable;

public class JwtRequest implements Serializable {

    private static final long serialVersionUID = 5926468583005150707L;

    private String username;
    private String pwHash;

    //need default constructor for JSON Parsing
    public JwtRequest() {

    }

    public JwtRequest(String username, String pwHash) {
        this.setUsername(username);
        this.setPwHash(pwHash);
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPwHash() {
        return this.pwHash;
    }

    public void setPwHash(String pwHash) {
        this.pwHash = pwHash;
    }
}