package com.example.familymap.model;

import android.provider.Contacts;
import android.widget.Switch;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.familymap.shared.model.Event;
import com.example.familymap.shared.model.Person;
import com.example.familymap.shared.model.User;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedSet;
import java.util.Vector;

//TODO: ** fix all methods so that they perform their function: NOT RECURSIVE **

public class DataCache {

    private static DataCache instance;

    static {
        initialize();
    }

    private static void initialize() { instance = new DataCache(); }

    public static void setUser(Person p){instance._setUser(p);}

    public static Person getPersonById(String id) {return instance._getPersonById(id);}

    public Event getEventById(String id){return instance._getEventById(id); }

    public static List<Person> getPersonChildren(Person p){
        return instance._getPersonChildren(p);
    }

    public static DataCache getInstance(){
        return instance;
    }

    private Map<String, Person> people; // person object for given person id
    private Map<String, Event> events; // events for given event id's
    private Map<String, List<Event>> personEvents; // person id, time sorted list of events for given person
    private List<String> eventTypes; // contains types of events
    private Map<String, Integer> eventTypeColors; // maps events to colors
    private Person user;
    private Set<String> paternalAncestors; //pre-compute the ancestors on paternal side(set of personIDs)
    private Set<String> maternalAncestors; //pre-compute the ancestors on maternal side(set of personIDs)
    private Event currEvent;

    private DataCache(){
        people = new HashMap<>();
        events = new HashMap<>();
        personEvents = new HashMap<>();
        eventTypes = new ArrayList<>();
        eventTypeColors = new HashMap<>();
        user = null;
        paternalAncestors = new HashSet<>();
        maternalAncestors = new HashSet<>();
        currEvent = null;
        maleEvents = new LinkedList<>();
        femaleEvents = new LinkedList<>();
    }

    public List<Event> maleEvents;
    public List<Event> femaleEvents;

    public boolean lifeStory = true;
    public boolean familyTree = true;
    public boolean paternal = false;
    public boolean maternal = false;
    public boolean male = false;
    public boolean female = false;

    public Set<String> getPaternalAncestors(){
        Vector<String> stringVec = new Vector<>();

        if(user.getFatherID() == null){
            return null;
        }
        else{
            stringVec.add(user.getFatherID());
            while(stringVec.size() != 0){
                Person p = getPersonById(stringVec.elementAt(0));
                if(p.getFatherID() != null){
                    stringVec.add(p.getFatherID());
                }
                if(p.getMotherID() != null){
                    stringVec.add(p.getMotherID());
                }
                paternalAncestors.add(stringVec.elementAt(0));
                stringVec.remove(0);
            }
        }
        return paternalAncestors;
    }

    public void getGenderLists(){
        Set<String> keyset = people.keySet();
        Vector<Person> maleVec = new Vector<>();
        Vector<Person> femaleVec = new Vector<>();

        for(String s: keyset){
            Person p = people.get(s);
            if(p.getGender().equals("m")){
                maleVec.add(p);
            }
            else{
                femaleVec.add(p);
            }
        }

        for(Person p: maleVec){
            List<Event> eventList = getEventList(p.getPersonID());
            if(eventList != null) {
                for (Event e : eventList) {
                    maleEvents.add(e);
                }
            }
        }

        for(Person p: femaleVec){
            List<Event> eventList = getEventList(p.getPersonID());
            if(eventList != null) {
                for (Event e : eventList) {
                    femaleEvents.add(e);
                }
            }
        }
    }

    public Set<String> getMaternalAncestors(){
        //original person
        //if they have father and motherids add them to a list
        //go through list. add people from ids. if they have father/mothers add them. then delete given id.

        Vector<String> stringVec = new Vector<>();

        if(user.getMotherID() == null){
            return null;
        }
        else{
            stringVec.add(user.getMotherID());
            while(stringVec.size() != 0){
                Person p = getPersonById(stringVec.elementAt(0));
                if(p.getMotherID() != null){
                    stringVec.add(p.getMotherID());
                }
                if(p.getFatherID() != null){
                    stringVec.add(p.getFatherID());
                }
                maternalAncestors.add(stringVec.elementAt(0));
                stringVec.remove(0);
            }
        }
        return maternalAncestors;
    }



    public Event getCurrEvent(){
        return currEvent;
    }
    public void setCurrEvent(Event event){
        currEvent = event;
    }

    public void _setUser(Person p){
       user = p;
    }

    public Person _getUser(){
        return user;
    }

    public void _inputEvents(Event[] ev){
        for(Event e: ev){
            events.put(e.getEventID(), e);
        }
    }

    public List<Event> getEventList(){
        List<Event> eventList = new LinkedList<>();
        Set<String> keys = events.keySet();
        for(String s: keys){
            Event e = events.get(s);
            eventList.add(e);
        }
        return eventList;
    }

    public List<Person> getPersonList(){
        List<Person> personList = new LinkedList<>();
        Set<String> keys = people.keySet();
        for(String s: keys){
            Person p = people.get(s);
            personList.add(p);
        }
        return personList;
    }

    public Map<String, Event> _getEvents(){
        return events;
    }

    public void _inputPeople(Person[] pv){
        for(Person p: pv){
            people.put(p.getPersonID(), p);
        }
    }

    public boolean spouseLine = true;

    public Map<String, List<Event>> sort_events(){
        Set<String> personKeys = people.keySet();
        Set<String> eventKeys = events.keySet();

        for(String s: personKeys){
            List<Event> list = new ArrayList<Event>();
            Person p = people.get(s);

            for(String str: eventKeys){
                Event e = events.get(str);
                if(e.getPersonID().equals(p.getPersonID())){
                        list.add(e);
                    }
            }
            //Collections.sort(list);
            personEvents.put(p.getPersonID(), list);
        }
        return personEvents;
    }

    public List<Event> getEventList(String personID){
        List<Event> list = personEvents.get(personID);
        return list;
    }

    public Event getEventFromCity(String city){
        Set<String> eventKeys = events.keySet();

        for(String s: eventKeys) {
            Event e = events.get(s);
            if(e.getCity() == city){
                return e;
            }
        }
        return null;
    }

    public Map<String, List<Event>> getSortedEvents(){
        return personEvents;
    }

    private Person _getPersonById(String id) {
        Set<String> personKeys = people.keySet();
        Person p = null;
        for (String s : personKeys) {
            Person temp = people.get(s);
            if (temp.getPersonID().length() == id.length()) {
                if (temp.getPersonID().charAt(0) == id.charAt(0)) {
                    if (temp.getPersonID().charAt(temp.getPersonID().length() - 1)
                            == id.charAt(id.length() - 1)) {
                        p = temp;
                        return p;
                    }
                }
            }
        }
        return p;
    }

    private List<Person> _getPersonChildren(Person p){
        List<Person> children = new LinkedList<>();
        Set<String> keys= people.keySet();
        for(String s: keys){
            Person person = people.get(s);
            if(person.getFatherID() != null && person.getMotherID() != null){
                if (person.getFatherID().equals(p.getPersonID()) || person.getMotherID().equals(p.getPersonID())) {
                    children.add(person);
                }
            }
        }
        return children;
    }

    public int getMarkerColor(Event e){
        int MarkerColor = 0;

        String lc = e.getEventType().toLowerCase();
        String up = e.getEventType().toUpperCase();

        if(eventTypes.contains(e.getEventType())){
            MarkerColor = eventTypeColors.get(e.getEventType());

            return MarkerColor;
        }
        else if(eventTypes.contains(up)){
            MarkerColor = eventTypeColors.get(up);

            return MarkerColor;
        }
        else if(eventTypes.contains(lc)){
            MarkerColor = eventTypeColors.get(lc);

            return MarkerColor;
        }
        else{
            eventTypes.add(e.getEventType());

            if(eventTypeColors.size() < 8){
                if(eventTypeColors.size() == 0){
                    MarkerColor = eventTypeColors.size();

                }
                else{
                    MarkerColor = eventTypeColors.size() - 1;
                }
            }
            else{
                MarkerColor = eventTypeColors.size() % 8;
            }

            eventTypeColors.put(e.getEventType(), MarkerColor);

            return MarkerColor;
        }

    } //takes event and returns color val based on event type

    private Event _getEventById(String id) {
        Set<String> eventKeys = events.keySet();
        Event e = null;
        for (String s : eventKeys) {
            Event temp = events.get(s);
            if (temp.getEventID().equals(id)) {
                        e = temp;
                        return e;
                    }
                }
        return e;
    }

    public void clearData(){
        people.clear();
        events.clear();
        personEvents.clear();
        eventTypes.clear();
        eventTypeColors.clear();
        user = null;
        currEvent = null;
    }
}