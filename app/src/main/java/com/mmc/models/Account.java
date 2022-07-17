package com.mmc.models;

import java.io.Serializable;

public class Account implements Serializable {
    private long id;
    private String fullName;
    private String email;
    private String password;

    public Account() {
    }

    public Account(long id, String fullName, String email, String password) {
        this.id = id;
        this.fullName = fullName;
        this.email = email;
        this.password = password;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }
}