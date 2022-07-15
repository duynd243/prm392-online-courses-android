package com.mmc.models;

public class ResponseStatus {
    private boolean success;
    private String message;
    private int status;

    public ResponseStatus() {
    }

    public ResponseStatus(boolean success, String message, int status) {
        this.success = success;
        this.message = message;
        this.status = status;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
