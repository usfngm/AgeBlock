package com.example.ageblock.api;

import com.example.ageblock.api.params.LoginParams;
import com.example.ageblock.api.response.AuthResponse;
import com.example.ageblock.model.User;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface APIService {

    @POST("/login")
    public Call<AuthResponse> login(@Body LoginParams user);

}
