package com.mmc.services;

import com.mmc.models.Account;
import com.mmc.models.AuthResponse;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface AccountService {
    String ACCOUNT = "mobile/accounts";

    @POST(ACCOUNT + "/login")
    Call<AuthResponse> login(@Body Account loginAccount);

    @POST(ACCOUNT + "/register")
    Call<AuthResponse> register(@Body Account registerAccount);
}
