package com.example.ageblock.api;

import android.util.Log;

import com.example.ageblock.api.callbacks.GenericReturnCallback;
import com.example.ageblock.api.params.AuthParams;
import com.example.ageblock.api.params.RequestParams;
import com.example.ageblock.api.response.AuthResponse;
import com.example.ageblock.api.response.ErrorResponse;
import com.example.ageblock.api.response.RequestResponse;
import com.example.ageblock.api.response.RequestResponseList;
import com.example.ageblock.model.Request;
import com.example.ageblock.model.User;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class API {

    private final String SERVICE_URL = "https://us-central1-ageblock-96602.cloudfunctions.net/api/";

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

    public void login(User user, final GenericReturnCallback<User> i) {

        AuthParams u = new AuthParams(user);
        Log.d("DEBUG", new Gson().toJson(u));

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


    public void login(String email, String password, final GenericReturnCallback<User> i) {

        AuthParams u = new AuthParams(new User(email, password));
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

    public void getUser(User user, final GenericReturnCallback<User> i) {

        AuthParams u = new AuthParams(user);

        Call<AuthResponse> call = api.getUser(u);

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

    public void getCurrentRequests(User u, final GenericReturnCallback<ArrayList<Request>> i) {
        AuthParams p = new AuthParams(u);
        Call<RequestResponseList> call = api.getCurrentRequests(p);
        call.enqueue(new Callback<RequestResponseList>() {
            @Override
            public void onResponse(Call<RequestResponseList> call, Response<RequestResponseList> response) {
                if (response.isSuccessful()) {
                    i.success(response.body().requests);
                } else {
                    try {
                        ErrorResponse error = new Gson().fromJson(response.errorBody().string(), ErrorResponse.class);
                        i.error(error.msg);
                    } catch (Exception e) {
                        Log.d("ERR", e.getMessage());
                        i.error("unknown");
                    }
                }
            }

            @Override
            public void onFailure(Call<RequestResponseList> call, Throwable t) {
                Log.d("API", "API ERROR " + t.getMessage());
                i.error("no_server");
            }
        });
    }

    public void getHistoryRequests(User u, final GenericReturnCallback<ArrayList<Request>> i) {
        AuthParams p = new AuthParams(u);
        Call<RequestResponseList> call = api.getHistoryRequests(p);
        call.enqueue(new Callback<RequestResponseList>() {
            @Override
            public void onResponse(Call<RequestResponseList> call, Response<RequestResponseList> response) {
                if (response.isSuccessful()) {
                    i.success(response.body().requests);
                } else {
                    try {
                        ErrorResponse error = new Gson().fromJson(response.errorBody().string(), ErrorResponse.class);
                        i.error(error.msg);
                    } catch (Exception e) {
                        Log.d("ERR", e.getMessage());
                        i.error("unknown");
                    }
                }
            }

            @Override
            public void onFailure(Call<RequestResponseList> call, Throwable t) {
                Log.d("API", "API ERROR " + t.getMessage());
                i.error("no_server");
            }
        });
    }

    public void getAllPendingRequests(User u, final GenericReturnCallback<ArrayList<Request>> i) {
        AuthParams p = new AuthParams(u);
        Call<RequestResponseList> call = api.getAllPendingRequests(p);
        call.enqueue(new Callback<RequestResponseList>() {
            @Override
            public void onResponse(Call<RequestResponseList> call, Response<RequestResponseList> response) {
                if (response.isSuccessful()) {
                    i.success(response.body().requests);
                } else {
                    try {
                        ErrorResponse error = new Gson().fromJson(response.errorBody().string(), ErrorResponse.class);
                        i.error(error.msg);
                    } catch (Exception e) {
                        Log.d("ERR", e.getMessage());
                        i.error("unknown");
                    }
                }
            }

            @Override
            public void onFailure(Call<RequestResponseList> call, Throwable t) {
                Log.d("API", "API ERROR " + t.getMessage());
                i.error("no_server");
            }
        });
    }

    public void newRequest(Request r, final GenericReturnCallback<Request> i) {
        RequestParams p = new RequestParams(r);
        Call<RequestResponse> call = api.newRequest(p);
        call.enqueue(new Callback<RequestResponse>() {
            @Override
            public void onResponse(Call<RequestResponse> call, Response<RequestResponse> response) {
                if (response.isSuccessful()) {
                    i.success(response.body().request);
                } else {
                    try {
                        ErrorResponse error = new Gson().fromJson(response.errorBody().string(), ErrorResponse.class);
                        i.error(error.msg);
                    } catch (Exception e) {
                        Log.d("ERR", e.getMessage());
                        i.error("unknown");
                    }
                }
            }

            @Override
            public void onFailure(Call<RequestResponse> call, Throwable t) {
                Log.d("API", "API ERROR " + t.getMessage());
                i.error("no_server");
            }
        });
    }

    public void register(User u, final GenericReturnCallback<User> i) {
        AuthParams p = new AuthParams(u);
        Call<AuthResponse> call = api.register(p);

        call.enqueue(new Callback<AuthResponse>() {
            @Override
            public void onResponse(Call<AuthResponse> call, Response<AuthResponse> response) {
                if (response.isSuccessful()) {
                    Log.d("JSON ORIGINAL", new Gson().toJson(response.body().user));
                    User u = response.body().user;
                    i.success(u);
                } else {
                    try {
                        ErrorResponse error = new Gson().fromJson(response.errorBody().string(), ErrorResponse.class);
                        i.error(error.msg);
                    } catch (Exception e) {
                        Log.d("ERR", e.getMessage());
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


    public void registerElder(User u, final GenericReturnCallback<User> i) {
        AuthParams p = new AuthParams(u);
        Call<AuthResponse> call = api.registerElder(p);

        call.enqueue(new Callback<AuthResponse>() {
            @Override
            public void onResponse(Call<AuthResponse> call, Response<AuthResponse> response) {
                if (response.isSuccessful()) {
                    Log.d("JSON ORIGINAL", new Gson().toJson(response.body().user));
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
