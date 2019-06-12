package com.example.ageblock.api;

import android.util.Log;

import com.example.ageblock.api.callbacks.GenericReturnCallback;
import com.example.ageblock.api.params.LoginParams;
import com.example.ageblock.api.response.AuthResponse;
import com.example.ageblock.api.response.ErrorResponse;
import com.example.ageblock.model.User;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class API {

    private final String SERVICE_URL = "http://192.168.1.9:3000";

    private static API _instance;

    private Gson gson;

    private Retrofit retrofit;

    private APIService api;


    private API() {
        gson = new GsonBuilder()
                .setLenient()
                .create();

        retrofit = new Retrofit.Builder()
                .baseUrl(SERVICE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        api = retrofit.create(APIService.class);
    }

    public static API getInstance() {
        if (_instance == null) {
            _instance = new API();
        }
        return _instance;
    }

    public void login(String email, String password, final GenericReturnCallback<User> i) {

        LoginParams u = new LoginParams(new User(email, password));

        Call<AuthResponse> call = api.login(u);

        call.enqueue(new Callback<AuthResponse>() {
            @Override
            public void onResponse(Call<AuthResponse> call, Response<AuthResponse> response) {
                if (response.isSuccessful()) {
                    User u = response.body().user;
                    i.success(u);
                } else {
                    try {
                        ErrorResponse error = new Gson().fromJson(response.errorBody().string(), ErrorResponse.class);
                        i.error(error.msg);
                    } catch (Exception e) {
                        i.error("unknown");
                    }
                }
            }

            @Override
            public void onFailure(Call<AuthResponse> call, Throwable t) {
                Log.d("API", "API ERROR " + t.getMessage());
                i.error("no_server");
            }
        });
    }

}
