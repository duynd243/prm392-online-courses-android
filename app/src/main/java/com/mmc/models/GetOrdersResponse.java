package com.mmc.models;

import java.util.List;

public class GetOrdersResponse {
    private ResponseStatus status;
    private List<Order> data;

    public ResponseStatus getStatus() {
        return status;
    }

    public void setStatus(ResponseStatus status) {
        this.status = status;
    }

    public List<Order> getData() {
        return data;
    }

    public void setData(List<Order> data) {
        this.data = data;
    }
}
