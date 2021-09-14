package com.example.familymap.shared.requests;

/**
 * recieves login information from server and stores it in java format
 */
public class LoginRequest {

    /**
     * username input variable converted from JSON to java and stored here
     */
    public String userName;

    /**
     * password input variable converted from JSON to java and stored here
     */
    public String password;
}
