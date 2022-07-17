package com.mmc.models;

public class CreateOrderResponse {
    private ResponseStatus status;
    private Order data;

    public ResponseStatus getStatus() {
        return status;
    }

    public void setStatus(ResponseStatus status) {
        this.status = status;
    }

    public Order getData() {
        return data;
    }

    public void setData(Order data) {
        this.data = data;
    }
}
