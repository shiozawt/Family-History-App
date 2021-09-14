package com.example.familymap.shared.results;

import com.example.familymap.shared.model.Person;

/**
 * stores family array and boolean(true if function was successful)
 */
public class FamilyArrayResult {

    /**
     * array of family members for given person stored here
     */
    Person[] persons;

    /**
     * true if function was successful; else false
     */
    boolean success;
}
