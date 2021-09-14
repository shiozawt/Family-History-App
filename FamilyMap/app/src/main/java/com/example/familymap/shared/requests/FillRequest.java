package com.example.familymap.shared.requests;

/**
 * recieves fill request information from server and stores it in java format.
 * stores username and num of generations to fill (if passed in by user. default is 4).
 */
public class FillRequest {

    /**
     * contains String username for fill request in java format
     */
    String userName;

    /**
     * contains number of generations to fill with generated family. default is 4
     */
    int generations = 4;

}
