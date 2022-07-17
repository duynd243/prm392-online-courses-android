package com.mmc.models;

import java.io.Serializable;

public class Mentor implements Serializable {
    private long id;
    private String fullName;
    private String imageUrl;
    private String email;
    private String bio;

    public Mentor() {
    }

    public long getId() {
        return id;
    }

    public String getFullName() {
        return fullName;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getEmail() {
        return email;
    }

    public String getBio() {
        return bio;
    }
}
