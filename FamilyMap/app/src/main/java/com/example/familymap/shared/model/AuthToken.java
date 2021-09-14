package com.example.familymap.shared.model;

/**
 * stores data classes for AuthToken objects
 */
public class AuthToken {

    private String personID;
    private String AuthToken;

    /**
     *
     * @param personID
     * @param AuthToken
     */
    public AuthToken(String personID, String AuthToken){
        this.personID = personID;
        this.AuthToken = AuthToken;
    }

    public String getPersonID() {
        return personID;
    }

    public void setPersonID(String personID) {
        this.personID = personID;
    }

    public String getAuthToken() {
        return AuthToken;
    }

    public void setAuthToken(String authToken) {
        AuthToken = authToken;
    }

    /**
     *
     * @param obj
     * @return
     */
    @Override
    public boolean equals(Object obj) {
        AuthToken token = (AuthToken)obj;
        if(obj == null){
            return false;
        }
        if(obj instanceof AuthToken){
            return  token.getAuthToken().equals(getAuthToken()) &&
                    token.getPersonID().equals(getPersonID());
        }
        else{
            return false;
        }
    }
}
