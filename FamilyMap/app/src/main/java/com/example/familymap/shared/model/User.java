package com.example.familymap.shared.model;

/**
 * stores data classes for user objects
 */
public class User {

    private String userName;
    private String password;
    private String email;
    private String firstName;
    private String lastName;
    private String gender;
    private String personID;

    /**
     *
     * @param userName
     * @param password
     * @param email
     * @param firstName
     * @param lastName
     * @param gender
     * @param personID
     */
    public User(String userName, String password, String email, String firstName, String lastName,
                String gender, String personID){
        this.userName = userName;
        this.password = password;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.personID = personID;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        lastName = lastName;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        gender = gender;
    }

    public String getPersonID() {
        return personID;
    }

    public void setPersonID(String personID) {
        personID = personID;
    }

    /**
     *
     * @param obj
     * @return
     */
    @Override
    public boolean equals(Object obj) {
        User user = (User)obj;
        if(obj == null){
            return false;
        }
        if(obj instanceof User){
            return user.getUserName().equals(getUserName()) &&
                    user.getPassword().equals(getPassword()) &&
                    user.getEmail().equals(getEmail()) &&
                    user.getFirstName().equals(getFirstName()) &&
                    user.getLastName().equals(getLastName()) &&
                    user.getGender().equals(getGender()) &&
                    user.getPersonID().equals(getPersonID());
        }
        else{
            return false;
        }
    }
}
