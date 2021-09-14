package com.example.familymap.shared.requests;

/**
 * receives registration information from server and stores it in java format
 */
public class RegisterRequest {

    /**
     * username input variable converted from JSON to java and stored here
     */
    public String userName;

    /**
     * password input variable converted from JSON to java and stored here
     */
    public String password;

    /**
     * email input variable converted from JSON to java and stored here
     */
    public String email;

    /**
     * first name input variable converted from JSON to java and stored here
     */
    public String firstName;

    /**
     * last name input variable converted from JSON to java and stored here
     */
    public String lastName;

    /**
     * gender input variable converted from JSON to java and stored here
     */
    public String gender;

}
