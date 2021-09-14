package com.example.familymap;

import com.example.familymap.UserInterface.MapsFragment;
import com.example.familymap.shared.model.Event;
import com.example.familymap.shared.model.Person;

import org.junit.Test;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

public class MapsFragmentTest {

    @Test
    public void parserMainTest(){
        MapsFragment map = new MapsFragment();
        String str = "first" + " " + "last" + '\n'
                + "BIRTH TEST" + ": " + "Petropavlovsh-Kamchatsky" + ", "
                + "Russia" + " (" +"2020" + ")";
        Person e = map.parserMain(str);
        assertNotNull(e);
    }
}
