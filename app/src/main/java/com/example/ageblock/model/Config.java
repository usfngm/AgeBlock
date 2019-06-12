package com.example.ageblock.model;

public class Config {
    private static Config _instance;

    private User user;

    private Config()
    {

    }

    public static Config get() {
        if (_instance == null)
        {
            _instance = new Config();
        }
        return _instance;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
