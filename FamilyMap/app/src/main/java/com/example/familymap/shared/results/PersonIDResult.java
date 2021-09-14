package com.example.familymap.shared.results;

/**
 * data stored for person object found based on person id passed in earlier
 */
public class PersonIDResult {

    /**
     * returned person id stored as string
     */
    public String personID;

    /**
     * returned username stored as string
     */
    public String associatedUsername;

    /**
     * returned first name stored as string
     */
    public String firstName;

    /**
     * returned last name stored as string
     */
    public String lastName;

    /**
     * returned gender stored as string
     */
    public String gender;

    /**
     * returned father id stored as string
     */
    public String fatherID;

    /**
     * returned mother id stored as string
     */
    public String motherID;

    /**
     * returned spouse id stored as string
     */
    public String spouseID;

    /**
     * is true if function was successful; otherwise it is false
     */
    public boolean success;
}
