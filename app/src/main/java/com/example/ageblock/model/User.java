package com.example.ageblock.model;

import android.app.Activity;
import android.content.SharedPreferences;

import com.google.gson.Gson;

import java.util.ArrayList;

public class User {

    private String uid;

    private String name;

    private String email;

    private String password;

    private String type;

    private String phone;

    private String nationalID;

    // Parents
    private ArrayList<String> parent_elders;

    // Volunteers
    private ArrayList<String> volunteer_skills;

    // Elder
    private String elder_parentID;

    public String getElder_parentID() {
        return elder_parentID;
    }

    public void setElder_parentID(String elder_parentID) {
        this.elder_parentID = elder_parentID;
    }

    public ArrayList<String> getParent_elders() {
        return parent_elders;
    }

    public void setParent_elders(ArrayList<String> parent_elders) {
        this.parent_elders = parent_elders;
    }

    public ArrayList<String> getVolunteer_skills() {
        return volunteer_skills;
    }

    public void setVolunteer_skills(ArrayList<String> volunteer_skills) {
        this.volunteer_skills = volunteer_skills;
    }

    public User(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public User() {

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

    public static void saveUser(Activity con, String json) {
        SharedPreferences sp = con.getSharedPreferences("app", 0);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString("user", json);
        editor.commit();
    }
}
