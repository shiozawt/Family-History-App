package com.example.familymap;

import com.example.familymap.model.DataCache;
import com.example.familymap.shared.model.Person;

import org.junit.Assert;
import org.junit.Test;

import java.util.List;

public class DataCacheTest {
    @Test
    public void getPersonChildrenTest(){
        DataCache data = DataCache.getInstance();

        Person[] array = new Person[3];

        Person one = new Person("a","a","a","a","m","d","f","c");
        Person two = new Person("b","b","b","b","m",null,"c",null);
        Person three = new Person("c","c","c","c","f","q","r","a");

        array[0] = one;
        array[1] = two;
        array[2] = three;

        data._inputPeople(array);

        List<Person> list = data.getPersonChildren(one);
        Assert.assertNotNull(list);
    }
}
