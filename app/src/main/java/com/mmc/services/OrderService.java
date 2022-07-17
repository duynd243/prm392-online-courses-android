package com.mmc.services;

import com.mmc.models.CreateOrderRequest;
import com.mmc.models.CreateOrderResponse;
import com.mmc.models.GetOrdersResponse;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface OrderService {
    String COMMON_PATH = "mobile";

    @POST(COMMON_PATH + "/orders")
    Call<CreateOrderResponse> createOrder(@Body CreateOrderRequest createOrderRequest);

    @GET(COMMON_PATH + "/{userId}/orders")
    Call<GetOrdersResponse> getOrdersOfUser(@Path("userId") long userId);
}
