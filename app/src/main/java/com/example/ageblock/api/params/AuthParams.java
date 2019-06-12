package com.example.ageblock.api.params;

import com.example.ageblock.model.User;

public class AuthParams {

    public User user;

    public AuthParams(User user) {
        this.user = user;
    }
}
