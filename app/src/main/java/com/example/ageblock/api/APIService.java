package com.example.ageblock.api;

import com.example.ageblock.api.params.AuthParams;
import com.example.ageblock.api.response.AuthResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface APIService {

    @POST("/login")
    Call<AuthResponse> login(@Body AuthParams user);

    @POST("/register")
    Call<AuthResponse> register(@Body AuthParams user);

}
