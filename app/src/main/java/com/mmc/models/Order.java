package com.mmc.models;

import java.io.Serializable;

public class Order implements Serializable {
    private long id;
    private long userId;
    private long courseId;
    private String createDate;

    public Order() {
    }

    public Order(long id, long userId, long courseId, String createDate) {
        this.id = id;
        this.userId = userId;
        this.courseId = courseId;
        this.createDate = createDate;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public long getCourseId() {
        return courseId;
    }

    public void setCourseId(long courseId) {
        this.courseId = courseId;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }
}
