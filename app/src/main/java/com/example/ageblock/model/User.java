package com.example.ageblock.model;

import android.app.Activity;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

public class User {

    private String uid;

    private String name;

    private String email;

    private String password;

    private String type;

    private String phone;

    private String nationalID;


    public User(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public User(String name, String phone, String nationalID, String email, String password)
    {
        this.name = name;
        this.phone = phone;
        this.nationalID = nationalID;
        this.email = email;
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getNationalID() {
        return nationalID;
    }

    public void setNationalID(String nationalID) {
        this.nationalID = nationalID;
    }

    public static User getLoggedUser(Activity con) {
        SharedPreferences sp = con.getSharedPreferences("app", 0);
        if (sp.getString("user", null) != null) {
            return new Gson().fromJson(sp.getString("user", null), User.class);
        }
        return null;
    }
}
