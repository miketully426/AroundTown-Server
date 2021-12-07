package com.launchcode.AroundTownServer.models;

import android.support.annotation.Size;
import com.sun.istack.NotNull;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;


import javax.swing.*;

@Entity
public class User {

    private static final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    @Id
    @GeneratedValue
    private int id;

    @NotNull
    @Size(max=100)
    private String name;

    @NotNull
    private String username;

    @NotNull
    private String pwHash;

    public User() {}

    public User(int id, String name, String username, String password) {
        this.id = id;
        this.name = name;
        this.username = username;
        this.pwHash = encoder.encode(password);
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public boolean isMatchingPassword(String password) {
        return encoder.matches(password, pwHash);
    }

}
