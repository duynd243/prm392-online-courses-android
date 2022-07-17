package com.mmc.api;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class APIClient {
    private static final String COURSE_API_BASE_URL = "https://mentor-mentee-connect-api.tk/api/v1/";
    private static Retrofit retrofit;

    public static Retrofit getAPIClient() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(COURSE_API_BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}
