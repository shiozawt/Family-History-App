package com.example.familymap.UserInterface;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.example.familymap.R;
import com.example.familymap.model.DataCache;
import com.example.familymap.shared.model.Event;
import com.example.familymap.shared.model.Person;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;

import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Vector;

public class PersonActivity extends AppCompatActivity {
    DataCache dataCache = DataCache.getInstance();
    Person person;
    Map<Person, String> relationMap= new HashMap<>(); //person, relationship

    TextView first, last, gender;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        DataCache data = DataCache.getInstance();
        super.onCreate(savedInstanceState);
        setContentView((com.example.familymap.R.layout.activity_person));

        Intent intent = getIntent();

        String personID = intent.getStringExtra("person");

        System.out.print("PERSON ID IS " + personID + '\n');

       person = dataCache.getPersonById(personID);

        first = (TextView) findViewById(R.id.first_name_display);
        first.setText("First name: " + person.getFirstName());

        last = (TextView) findViewById(R.id.last_name_display);
        last.setText("Last name: " + person.getLastName());

        gender = (TextView) findViewById(R.id.gender_display);
        String g;
        if(person.getGender().equals("m")){
            g = "Male";
        }
        else{
            g = "Female";
        }
        gender.setText("Gender: " + g);

        ExpandableListView expandableListView = findViewById(R.id.expandableListView);

        //TODO: Get lists of people and events for given person from DataCache
        List<Event> eventList = dataCache.getEventList(person.getPersonID());
        eventList = eventListOrganizer(eventList);
        List<Person> personList = new LinkedList<>();
        List<Person> childrenList = dataCache.getPersonChildren(person);
        if(person.getFatherID() != null){
            personList.add(dataCache.getPersonById(person.getFatherID()));
            relationMap.put(dataCache.getPersonById(person.getFatherID()), "Father");
        }
        if(person.getMotherID() != null){
            personList.add(dataCache.getPersonById(person.getMotherID()));
            relationMap.put(dataCache.getPersonById(person.getMotherID()), "Mother");
        }
        if(person.getSpouseID() != null){
            personList.add(dataCache.getPersonById(person.getSpouseID()));
            relationMap.put(dataCache.getPersonById(person.getSpouseID()), "Spouse");
        }
        for(Person child: childrenList){
            personList.add(child);
        }

        if(data.maternal == true){
            Set<String> set = data.getMaternalAncestors();

            for(String s: set){
                Person p = data.getPersonById(s);
                List<Event> list = data.getEventList(p.getPersonID());
                for(Event e: list){
                    if(eventList.contains(e)){
                        eventList.remove(e);
                    }
                }
            }
        }

        if(data.paternal == true){
            Set<String> set = data.getPaternalAncestors();

            for(String s: set){
                Person p = data.getPersonById(s);
                List<Event> list = data.getEventList(p.getPersonID());
                for(Event e: list){
                    if(eventList.contains(e)){
                        eventList.remove(e);
                    }
                }
            }
        }

        if(data.male == true){
            List<Event> events = data.maleEvents;
            for(Event e: events){
                if(eventList.contains(e)){
                    eventList.remove(e);
                }
            }
        }

        if(data.female == true){
            List<Event> events = data.femaleEvents;
            for(Event e: events){
                if(eventList.contains(e)){
                    eventList.remove(e);
                }
            }
        }

        expandableListView.setAdapter(new ExpandableListAdapter(eventList, personList)); //TODO: REPLACE VARIABLES WITH LIST NAMES

    }


    private class ExpandableListAdapter extends BaseExpandableListAdapter {

        private static final int EVENT_GROUP_POSITION = 0;
        private static final int PERSON_GROUP_POSITION = 1;

        private final List<Event> eventList;
        private final List<Person> personList;

        ExpandableListAdapter(List<Event> eventList, List<Person> personList) {
            this.eventList = eventList;
            this.personList = personList;
        }

        @Override
        public int getGroupCount() {
            return 2;
        }

        @Override
        public int getChildrenCount(int groupPosition) {
            switch (groupPosition) {
                case EVENT_GROUP_POSITION:
                    return eventList.size();
                case PERSON_GROUP_POSITION:
                    return personList.size();
                default:
                    throw new IllegalArgumentException("Unrecognized group position: " + groupPosition);
            }
        }

        @Override
        public Object getGroup(int groupPosition) {
            switch (groupPosition) {
                case EVENT_GROUP_POSITION:
                    return getString(R.string.eventListTitle);
                case PERSON_GROUP_POSITION:
                    return getString(R.string.personListTitle);
                default:
                    throw new IllegalArgumentException("Unrecognized group position: " + groupPosition);
            }
        }

        @Override
        public Object getChild(int groupPosition, int childPosition) {
            switch (groupPosition) {
                case EVENT_GROUP_POSITION:
                    return eventList.get(childPosition);
                case PERSON_GROUP_POSITION:
                    return personList.get(childPosition);
                default:
                    throw new IllegalArgumentException("Unrecognized group position: " + groupPosition);
            }
        }

        @Override
        public long getGroupId(int groupPosition) {
            return groupPosition;
        }

        @Override
        public long getChildId(int groupPosition, int childPosition) {
            return childPosition;
        }

        @Override
        public boolean hasStableIds() {
            return false;
        }

        @Override
        public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
            if(convertView == null) {
                convertView = getLayoutInflater().inflate(R.layout.list_item_group, parent, false);
            }

            TextView titleView = convertView.findViewById(R.id.listTitle);

            switch (groupPosition) {
                case EVENT_GROUP_POSITION:
                    titleView.setText(R.string.eventListTitle);
                    break;
                case PERSON_GROUP_POSITION:
                    titleView.setText(R.string.personListTitle);
                    break;
                default:
                    throw new IllegalArgumentException("Unrecognized group position: " + groupPosition);
            }

            return convertView;
        }

        @Override
        public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
            View itemView;

            switch(groupPosition) {
                case EVENT_GROUP_POSITION:
                    itemView = getLayoutInflater().inflate(R.layout.event_item, parent, false);
                    initializeEventView(itemView, childPosition);
                    break;
                case PERSON_GROUP_POSITION:
                    itemView = getLayoutInflater().inflate(R.layout.person_item, parent, false);
                    initializePersonView(itemView, childPosition);
                    break;
                default:
                    throw new IllegalArgumentException("Unrecognized group position: " + groupPosition);
            }

            return itemView;
        }

        private void initializeEventView(View eventItemView, final int childPosition) {
            Event e = eventList.get(childPosition);

            TextView eventIconView = eventItemView.findViewById(R.id.eventListTitle);
            eventIconView.setClickable(true);
            eventIconView.setOnClickListener((v)-> eventIconClicked(eventItemView, eventList.get(childPosition)));

            if(e.getEventType().equals("birth")){
                eventIconView.setCompoundDrawablesWithIntrinsicBounds(R.drawable.hue_magneta, 0, 0, 0);
            }
            else if(e.getEventType().equals("marriage")){
                eventIconView.setCompoundDrawablesWithIntrinsicBounds(R.drawable.hue_green, 0, 0, 0);
            }
            else if(e.getEventType().equals("death")){
                eventIconView.setCompoundDrawablesWithIntrinsicBounds(R.drawable.hue_orange, 0, 0, 0);
            }
            else{
                int colorMatch = dataCache.getMarkerColor(e);

                if(colorMatch == 0){
                    eventIconView.setCompoundDrawablesWithIntrinsicBounds(R.drawable.hue_azure, 0, 0, 0);
                }
                if(colorMatch == 1){
                    eventIconView.setCompoundDrawablesWithIntrinsicBounds(R.drawable.hue_blue, 0, 0, 0);
                }
                if(colorMatch == 2){
                    eventIconView.setCompoundDrawablesWithIntrinsicBounds(R.drawable.hue_red, 0, 0, 0);
                }
                if(colorMatch == 3){
                    eventIconView.setCompoundDrawablesWithIntrinsicBounds(R.drawable.hue_cyan, 0, 0, 0);
                }
                if(colorMatch == 4){
                    eventIconView.setCompoundDrawablesWithIntrinsicBounds(R.drawable.hue_yellow, 0, 0, 0);
                }
                if(colorMatch == 5){
                    eventIconView.setCompoundDrawablesWithIntrinsicBounds(R.drawable.hue_rose, 0, 0, 0);
                }
                if(colorMatch == 6){
                    eventIconView.setCompoundDrawablesWithIntrinsicBounds(R.drawable.hue_violet, 0, 0, 0);
                }
            }


            TextView eventView = eventItemView.findViewById(R.id.eventCity);
            eventView.setText(e.getEventType().toUpperCase() + ": " + e.getCity() + ", " + e.getCountry() + " (" + e.getYear() + ")");
            eventView.setClickable(true);
            eventView.setOnClickListener((v)-> eventIconClicked(eventItemView, eventList.get(childPosition)));

            TextView personView = eventItemView.findViewById(R.id.eventCountry);
            Person p = dataCache.getPersonById(eventList.get(childPosition).getPersonID());
            personView.setText(p.getFirstName() + " " +p.getLastName());
            personView.setClickable(true);
            personView.setOnClickListener((v)-> eventIconClicked(eventItemView, eventList.get(childPosition)));
        }

        private void initializePersonView(View PersonItemView, final int childPosition) {
            TextView iconView = PersonItemView.findViewById(R.id.personListTitle);
            iconView.setClickable(true);
            iconView.setOnClickListener((v) -> {
                iconButtonClicked(PersonItemView, personList.get(childPosition));});
            //trailNameView.setText(personList.get(childPosition).getFirstName() + " " +personList.get(childPosition).getLastName());
            if(personList.get(childPosition).getGender().equals("m")){
                iconView.setCompoundDrawablesWithIntrinsicBounds(R.drawable.male, 0, 0, 0);
            }
            else {
                iconView.setCompoundDrawablesWithIntrinsicBounds(R.drawable.female, 0, 0, 0);
            }

            TextView nameView = PersonItemView.findViewById(R.id.personFirstName);
            nameView.setText(personList.get(childPosition).getFirstName() + " " +personList.get(childPosition).getLastName());
            nameView.setOnClickListener((v) -> {
                iconButtonClicked(PersonItemView, personList.get(childPosition));});

            TextView relationshipView = PersonItemView.findViewById(R.id.personLastName);

            relationshipView.setOnClickListener((v) -> {
                iconButtonClicked(PersonItemView, personList.get(childPosition));});

            if(relationMap.get(personList.get(childPosition)) != null){
                String relationship = relationMap.get(personList.get(childPosition));
                relationshipView.setText(relationship);
            }
            else {
                relationshipView.setText("Child");
            }

                PersonItemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(PersonActivity.this, getString(R.string.personListToastText, personList.get(childPosition).getPersonID()), Toast.LENGTH_SHORT).show();
                }
            });
        }

        @Override
        public boolean isChildSelectable(int groupPosition, int childPosition) {
            return true;
        }
    }

    public void iconButtonClicked(View v, Person p){
        Intent intent = new Intent(PersonActivity.this, PersonActivity.class);
        intent.putExtra("person", p.getPersonID());
        startActivity(intent);
        ((Activity) PersonActivity.this).overridePendingTransition(0, 0);
    }

    public void eventIconClicked(View v, Event e){
        Intent intent = new Intent(PersonActivity.this, EventActivity.class);
        intent.putExtra("event", e.getEventID());
        startActivity(intent);
        ((Activity) PersonActivity.this).overridePendingTransition(0, 0);
    }

    public List<Event> eventListOrganizer(List<Event> eventList){
        List<Integer> intList = new LinkedList<>();
        List<Event> transferEventList = new LinkedList<>();
        /*
        for(Event e: eventList){
            intList.add(e.getYear());
        }
        Collections.sort(intList);

        for(int i: intList){
            for(Event e: eventList){
                if(e.getYear() == i){
                    transferEventList.add(e);
                }
            }
        }

         */

        Vector<Event> eventVec = new Vector();
        for(Event e: eventList){
            eventVec.add(e);
        }


        //write a function that gets current highest val and then deletes it until vector is empty
        while(eventVec.size() != 0){
            int min = 0;
            int position = 0;
            int currPosition = 0;
            Event tempEvent = null;
            for(Event a: eventVec){
                if(min == 0){
                    min = a.getYear();
                    tempEvent = a;
                    position = currPosition;
                }
                else{
                    if(min > a.getYear()){
                        min = a.getYear();
                        tempEvent = a;
                        position = currPosition;
                    }
                }
                currPosition += 1;
            }
            transferEventList.add(eventVec.elementAt(position));
            eventVec.remove(position);
        }

        return transferEventList;
    }

}