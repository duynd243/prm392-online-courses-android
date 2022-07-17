package com.mmc.repositories;

import com.mmc.api.APIClient;
import com.mmc.services.OrderService;

public class OrderRepository {
    public static OrderService getOrderService() {
        return APIClient.getAPIClient().create(OrderService.class);
    }
}
