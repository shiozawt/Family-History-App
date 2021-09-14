package com.example.familymap.model;

import com.example.familymap.shared.model.User;

public class LoginStorage {
    String _username;
    boolean genderIsMale;
    public LoginStorage(){
        String _username = "";
        boolean genderIsMale;
    }
    public LoginStorage storage;

    public void setUsername(String username){
        storage._username = username;
    }

    public void setMale(boolean male){
        storage.genderIsMale = male;
    }
}
