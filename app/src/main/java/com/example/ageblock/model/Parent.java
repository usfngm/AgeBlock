package com.example.ageblock.model;

import java.util.ArrayList;

public class Parent extends User {

    ArrayList<String> elders;

    public Parent(String email, String password) {
        super(email, password);
        elders = new ArrayList();
    }

    public Parent(String name, String phone, String nationalID, String email, String password) {
        super(name, phone, nationalID, email, password);
        elders = new ArrayList();
    }

    public void addElder(String elder) {
        elders.add(elder);
    }

    public void removeElder(String elder) {
        elders.remove(elder);
    }

}
