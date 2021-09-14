package com.example.familymap.shared.results;

/**
 * stores boolean value based on whether or not the load was successful
 */
public class LoadResult {

    public int numPeople;

    public int numEvents;

    public int numUsers;

    /**
     * true if load was successful
     * @return
     */
    public boolean success;
}
