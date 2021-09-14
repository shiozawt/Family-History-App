package com.example.familymap.shared.results;

/**
 * stores data to be returned to server if login is successful
 */
public class LoginResult {

    /**
     * stores authToken to be returned to Server
     */
    public String authToken;

    /**
     * stores username to be returned to Server
     */
    public String userName;

    /**
     * stores PersonID to be returned to Server
     */
    public String personID;

    /**
     * stores boolean value to determine if this was a successful operation
     */
    public boolean Success;
}
