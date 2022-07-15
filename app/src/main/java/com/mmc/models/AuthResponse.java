package com.mmc.models;

public class AuthResponse {
    private ResponseStatus status;
    private Account data;

    public ResponseStatus getStatus() {
        return status;
    }

    public void setStatus(ResponseStatus status) {
        this.status = status;
    }

    public Account getData() {
        return data;
    }

    public void setData(Account data) {
        this.data = data;
    }
}
