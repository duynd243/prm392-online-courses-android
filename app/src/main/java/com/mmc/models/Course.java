package com.mmc.models;

import java.io.Serializable;
import java.math.BigDecimal;

public class Course implements Serializable {
    private long id;
    private String name;
    private BigDecimal price;
    private String description;
    private String imageUrl;
    private int currentNumberMentee;
    private double totalRating;
    private Mentor mentor;
    private Subject subject;

    public Course() {

    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public String getDescription() {
        return description;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public int getCurrentNumberMentee() {
        return currentNumberMentee;
    }

    public double getTotalRating() {
        return totalRating;
    }

    public Mentor getMentor() {
        return mentor;
    }

    public Subject getSubject() {
        return subject;
    }
}
