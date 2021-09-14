package com.example.familymap.shared.requests;

import com.example.familymap.shared.model.Event;
import com.example.familymap.shared.model.Person;
import com.example.familymap.shared.model.User;

/**
 * Loads in person array, event array, and user array. These will be called by other functions
 * later in order to repopulate the given database after it is cleared.
 */
public class LoadRequest {

    /**
     * contains an array of users to be created.
     */
    public Person[] persons;

    /**
     * contain family history information for these users
     */
    public Event[] events;

    /**
     * contain family history information for these users
     */
    public User[] users;
}
