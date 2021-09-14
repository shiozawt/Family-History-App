package com.example.familymap.shared.model;

/**
 * stores data classes for Person objects
 */
public class Person {
    private String personID;
    private String associatedUsername;
    private String firstName;
    private String lastName;
    private String gender;
    private String fatherID;
    private String motherID;
    private String spouseID;

    /**
     *
     * @param personID
     * @param associatedUsername
     * @param firstName
     * @param lastName
     * @param gender
     * @param fatherID
     * @param motherID
     * @param spouseID
     */
    public Person(String personID, String associatedUsername, String firstName, String lastName, String gender,
                  String fatherID, String motherID, String spouseID){
        this.personID = personID;
        this.associatedUsername = associatedUsername;
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.fatherID = fatherID;
        this.motherID = motherID;
        this.spouseID = spouseID;
    }

    public String getPersonID() {
        return personID;
    }

    public void setPersonID(String personID) {
        this.personID = personID;
    }

    public String getAssociatedUsername() {
        return associatedUsername;
    }

    public void setAssociatedUsername(String associatedUsername) {
        this.associatedUsername = associatedUsername;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getFatherID() {
        return fatherID;
    }

    public void setFatherID(String fatherID) {
        this.fatherID = fatherID;
    }

    public String getMotherID() {
        return motherID;
    }

    public void setMotherID(String motherID) {
        this.motherID = motherID;
    }

    public String getSpouseID() {
        return spouseID;
    }

    public void setSpouseID(String spouseID) {
        this.spouseID = spouseID;
    }

    /**
     *
     * @param obj
     * @return
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        Person person = (Person)obj;
        if (obj instanceof Person){
            if (fatherID == null){
                if (person.fatherID != null){
                    return false;
                }
            }
            if (fatherID != null){
                if (person.fatherID == null){
                    return false;
                }
            }
            if (motherID == null){
                if (person.motherID != null){
                    return false;
                }
            }
            if (motherID != null){
                if (person.motherID == null){
                    return false;
                }
            }

            if (spouseID == null){
                if (person.spouseID != null){
                    return false;
                }
            }
            if (spouseID != null){
                if (person.spouseID == null){
                    return false;
                }
            }

            return person.getPersonID().equals(getPersonID()) &&
                    person.getAssociatedUsername().equals(getAssociatedUsername()) &&
                    person.getFirstName().equals(getFirstName()) &&
                    person.getLastName().equals(getLastName()) &&
                    person.getGender().equals(getGender());
        }
        else{
            return false;
        }
    }
}
