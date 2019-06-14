package com.example.ageblock.api;

import com.example.ageblock.api.params.AuthParams;
import com.example.ageblock.api.params.RequestParams;
import com.example.ageblock.api.response.AuthResponse;
import com.example.ageblock.api.response.RequestResponse;
import com.example.ageblock.api.response.RequestResponseList;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface APIService {

    @POST("/login")
    Call<AuthResponse> login(@Body AuthParams user);

    @POST("/getUser")
    Call<AuthResponse> getUser(@Body AuthParams user);

    @POST("/register")
    Call<AuthResponse> register(@Body AuthParams user);

    @POST("/registerElder")
    Call<AuthResponse> registerElder(@Body AuthParams user);

    @POST("/request")
    Call<RequestResponse> newRequest(@Body RequestParams request);

    @POST("/currentRequests")
    Call<RequestResponseList> getCurrentRequests(@Body AuthParams user);

    @POST("/historyRequests")
    Call<RequestResponseList> getHistoryRequests(@Body AuthParams user);

    @POST("/allPendingRequests")
    Call<RequestResponseList> getAllPendingRequests(@Body AuthParams user);

}
