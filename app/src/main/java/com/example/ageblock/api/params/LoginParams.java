package com.example.ageblock.api.params;

import com.example.ageblock.model.User;

public class LoginParams {

    public User user;

    public LoginParams(User user) {
        this.user = user;
    }
}
