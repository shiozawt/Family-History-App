package com.example.familymap;

import com.example.familymap.UserInterface.PersonActivity;
import com.example.familymap.shared.model.Event;

import org.junit.Test;

import java.util.LinkedList;
import java.util.List;

import static org.junit.Assert.assertNotNull;

public class PersonActivityTest {

    @Test
    public void eventListOrganizerTest(){
        PersonActivity personActivity = new PersonActivity();
        List<Event> passInList = new LinkedList<>();

        float one_lat = (float) -36.1833000183105;
        float one_long = (float) 144.966705322266;
        Event event_one = new Event("Sheila_Birth", "sheila",	"Sheila_Parker",
                one_lat, one_long,"Australia",
                "Melbourne","birth",	1970);

        Event event_two = new Event("Sheila_Marriage", "sheila",
                "Sheila_Parker", one_lat, one_long,"Australia",
                "Melbourne","marriage",	1970);

        Event event_three = new Event("Sheila_Asteroids", "sheila",
                "Sheila_Parker", one_lat, one_long,"Australia",
                "Melbourne","completed asteroids",	1970);

        Event event_four = new Event("Other_Asteroids", "sheila",
                "Sheila_Parker", one_lat, one_long,"Australia",
                "Melbourne","COMPLETED ASTEROIDS",	1970);

        Event event_five = new Event("Sheila_Death", "sheila",
                "Sheila_Parker", one_lat, one_long,"Australia",
                "Melbourne","death",	1970);

        List<Event> eventList = personActivity.eventListOrganizer(passInList);
        assertNotNull(eventList);
    }
}
