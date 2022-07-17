package com.mmc.models;

public class CreateOrderRequest {
    private long userId;
    private long courseId;

    public CreateOrderRequest(long userId, long courseId) {
        this.userId = userId;
        this.courseId = courseId;
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
}
