package com.example.ageblock.model;

import java.util.ArrayList;

public class Volunteer extends User {

    private ArrayList<String> skills;

    public Volunteer(String email, String password) {
        super(email, password);
        skills = new ArrayList();
    }

    public Volunteer(String name, String phone, String nationalID, String email, String password) {
        super(name, phone, nationalID, email, password);
        skills = new ArrayList();
    }

    public void addSkill(String skill)
    {
        skills.add(skill);
    }

    public void removeSkill(String skill)
    {
        skills.remove(skill);
    }


}
