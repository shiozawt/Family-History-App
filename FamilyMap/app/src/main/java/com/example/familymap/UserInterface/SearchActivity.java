package com.example.familymap.UserInterface;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.familymap.R;
import com.example.familymap.model.DataCache;
import com.example.familymap.shared.model.Event;
import com.example.familymap.shared.model.Person;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class SearchActivity extends AppCompatActivity {

    private static final int EventItemType = 0;
    private static final int PersonItemType = 1;

    List<Event> events;
    List<Person> people;
    EditText searchBar;
    SearchAdapter adapter;
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView((com.example.familymap.R.layout.activity_search));

        searchBar = (EditText) findViewById(R.id.search_bar);
        searchBar.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                //filterResults(editable.toString());
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                //filterResults(editable.toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {
                filterResults(editable.toString());
            }
        });

        recyclerView = findViewById(R.id.RecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(SearchActivity.this));

        DataCache data = DataCache.getInstance();
        events = data.getEventList();
        people = data.getPersonList();

        if(data.maternal == true){
            Set<String> set = data.getMaternalAncestors();

            for(String s: set){
                Person p = data.getPersonById(s);
                List<Event> list = data.getEventList(p.getPersonID());
                for(Event e: list){
                    if(events.contains(e)){
                        events.remove(e);
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
                    if(events.contains(e)){
                        events.remove(e);
                    }
                }
            }
        }

        if(data.male == true){
            List<Event> eventList = data.maleEvents;
            for(Event e: eventList){
                if(events.contains(e)){
                    events.remove(e);
                }
            }
        }

        if(data.female == true){
            List<Event> eventList = data.femaleEvents;
            for(Event e: eventList){
                if(events.contains(e)){
                    events.remove(e);
                }
            }
        }

        /*
        adapter = new SearchAdapter(events, people);
        recyclerView.setAdapter(adapter);

         */
    }

    private class SearchAdapter extends RecyclerView.Adapter<SearchViewHolder> {
        private final List<Event> events;
        private final List<Person> people;

        SearchAdapter(List<Event> events, List<Person> people) {
            this.events = events;
            this.people = people;
        }

        @Override
        public int getItemViewType(int position) {
            return position < events.size() ? EventItemType : PersonItemType;
        }

        @NonNull
        @Override
        public SearchViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view;

            if(viewType == EventItemType) {
                view = getLayoutInflater().inflate(R.layout.recycler_event_item, parent, false);
            } else {
                view = getLayoutInflater().inflate(R.layout.recycler_person_item, parent, false);
            }

            return new SearchViewHolder(view, viewType);
        }

        @Override
        public void onBindViewHolder(@NonNull SearchViewHolder holder, int position) {
            if(position < events.size()) {
                holder.bind(events.get(position));
            } else {
                holder.bind(people.get(position - events.size()));
            }
        }

        @Override
        public int getItemCount() {
            return events.size() + people.size();
        }
    }

    private class SearchViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private final TextView icon;
        private final TextView line_one;
        private final TextView line_two;

        private final int viewType;
        private Event event;
        private Person person;

        SearchViewHolder(View view, int viewType) {
            super(view);
            this.viewType = viewType;

            itemView.setOnClickListener(this);

            if(viewType == EventItemType) {
                icon = itemView.findViewById(R.id.title_icon);
                line_one = itemView.findViewById(R.id.event_line_one);
                line_two = itemView.findViewById(R.id.event_line_two);
            } else {
                icon = itemView.findViewById(R.id.person_icon);
                line_one = itemView.findViewById(R.id.person_name);
                line_two = null;
            }
        }

        private void bind(Event event) {
            this.event = event;
            icon.setClickable(true);
            icon.setOnClickListener((v)-> eventClicked(event));

            DataCache dataCache = DataCache.getInstance();

            if(event.getEventType().equals("birth")){
                icon.setCompoundDrawablesWithIntrinsicBounds(R.drawable.hue_magneta, 0, 0, 0);
            }
            else if(event.getEventType().equals("marriage")){
                icon.setCompoundDrawablesWithIntrinsicBounds(R.drawable.hue_green, 0, 0, 0);
            }
            else if(event.getEventType().equals("death")){
                icon.setCompoundDrawablesWithIntrinsicBounds(R.drawable.hue_orange, 0, 0, 0);
            }
            else{
                int colorMatch = dataCache.getMarkerColor(event);

                if(colorMatch == 0){
                    icon.setCompoundDrawablesWithIntrinsicBounds(R.drawable.hue_azure, 0, 0, 0);
                }
                if(colorMatch == 1){
                    icon.setCompoundDrawablesWithIntrinsicBounds(R.drawable.hue_blue, 0, 0, 0);
                }
                if(colorMatch == 2){
                    icon.setCompoundDrawablesWithIntrinsicBounds(R.drawable.hue_red, 0, 0, 0);
                }
                if(colorMatch == 3){
                    icon.setCompoundDrawablesWithIntrinsicBounds(R.drawable.hue_cyan, 0, 0, 0);
                }
                if(colorMatch == 4){
                    icon.setCompoundDrawablesWithIntrinsicBounds(R.drawable.hue_yellow, 0, 0, 0);
                }
                if(colorMatch == 5){
                    icon.setCompoundDrawablesWithIntrinsicBounds(R.drawable.hue_rose, 0, 0, 0);
                }
                if(colorMatch == 6){
                    icon.setCompoundDrawablesWithIntrinsicBounds(R.drawable.hue_violet, 0, 0, 0);
                }
            }

            String eventType = event.getEventType().toUpperCase();
            line_one.setText(eventType + ": " + event.getCity() + ", " + event.getCountry() + " (" + event.getYear() + ")");
            line_one.setClickable(true);
            line_one.setOnClickListener((v)-> eventClicked(event));

            Person p = dataCache.getPersonById(event.getPersonID());
            line_two.setText(p.getFirstName() + " " + p.getLastName());
            line_two.setClickable(true);
            line_two.setOnClickListener((v)-> eventClicked(event));
        }

        private void bind(Person person) {
            this.person = person;
            icon.setClickable(true);
            icon.setOnClickListener((v)-> personClicked(person));

            line_one.setText(person.getFirstName() + " " + person.getLastName());
            line_one.setClickable(true);
            line_one.setOnClickListener((v)-> personClicked(person));

            if(person.getGender().equals("m")){
                icon.setCompoundDrawablesWithIntrinsicBounds(R.drawable.male, 0, 0, 0);
            }
            else {
                icon.setCompoundDrawablesWithIntrinsicBounds(R.drawable.female, 0, 0, 0);
            }
        }

        @Override
        public void onClick(View view) {
            if(viewType == EventItemType) {
                // This is were we could pass the skiResort to a ski resort detail activity

                Toast.makeText(SearchActivity.this, String.format("Event type: %s",
                        event.getEventType()), Toast.LENGTH_SHORT).show();
            } else {
                // This is were we could pass the hikingTrail to a hiking trail detail activity

                Toast.makeText(SearchActivity.this, String.format("Person selected: %s %s.",
                        person.getFirstName(), person.getLastName()), Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void updateEventList(List<Event> eventList, List<Person> personList){
        //events = eventList;
        //people = personList;
        String s = searchBar.getText().toString();
            if (adapter != null) {
                adapter.notifyDataSetChanged();
            }
            adapter = new SearchAdapter(eventList, personList);
            recyclerView.setAdapter(adapter);

    }

    public void filterResults(String s){
        DataCache dataCache = DataCache.getInstance();
        List<Event> filteredEvents = new ArrayList();
        String lc = s.toLowerCase();
        String uc = s.toUpperCase();
        for(Event e: events){

            Person person = dataCache.getPersonById(e.getPersonID());

            String curYear = new Integer(e.getYear()).toString();

            if(e.getEventType().contains(s)){
                filteredEvents.add(e);
            }
            else if(e.getEventType().toLowerCase().contains(lc)){
                filteredEvents.add(e);
            }
            else if(e.getEventType().toUpperCase().contains(uc)){
                filteredEvents.add(e);
            }
            else if(e.getCountry().contains(s)){
                filteredEvents.add(e);
            }
            else if(e.getCountry().toLowerCase().contains(lc)){
                filteredEvents.add(e);
            }
            else if(e.getCountry().toUpperCase().contains(uc)){
                filteredEvents.add(e);
            }
            else if(e.getCity().contains(s)){
                filteredEvents.add(e);
            }
            else if(e.getCity().toLowerCase().contains(lc)){
                filteredEvents.add(e);
            }
            else if(e.getCity().toUpperCase().contains(uc)){
                filteredEvents.add(e);
            }
            else if(curYear.contains(s)) {
                filteredEvents.add(e);
            }
            else if (person.getFirstName().contains(s)) {
                filteredEvents.add(e);
            }
            else if(person.getFirstName().toLowerCase().contains(s)){
                filteredEvents.add(e);
            }
            else if(person.getFirstName().toUpperCase().contains(s)){
                filteredEvents.add(e);
            }
            else if (person.getLastName().contains(s)) {
                filteredEvents.add(e);
            }
            else if(person.getLastName().toLowerCase().contains(s)){
                filteredEvents.add(e);
            }
            else if(person.getLastName().toUpperCase().contains(s)){
                filteredEvents.add(e);
            }
            else{
                System.out.print("Nothing found here!");
            }
        }
        //update recyclerview

        List<Person> filteredPersons = new ArrayList<>();

        for(Person person: people) {

            if (person.getFirstName().contains(s)) {
                filteredPersons.add(person);
            }
            else if(person.getFirstName().toLowerCase().contains(s)){
                filteredPersons.add(person);
            }
            else if(person.getFirstName().toUpperCase().contains(s)){
                filteredPersons.add(person);
            }
            else if (person.getLastName().contains(s)) {
                filteredPersons.add(person);
            }
            else if(person.getLastName().toLowerCase().contains(s)){
                filteredPersons.add(person);
            }
            else if(person.getLastName().toUpperCase().contains(s)){
                filteredPersons.add(person);
            }
            else {
                System.out.print("Nothing found here!");
            }
        }

        updateEventList(filteredEvents, filteredPersons);
        }

        public void eventClicked(Event event){
            Intent intent = new Intent(SearchActivity.this, EventActivity.class);
            intent.putExtra("event", event.getEventID());
            startActivity(intent);
            ((Activity) SearchActivity.this).overridePendingTransition(0, 0);
        }

        public void personClicked(Person person){
            Intent intent = new Intent(SearchActivity.this, PersonActivity.class);
            intent.putExtra("person", person.getPersonID());
            startActivity(intent);
            ((Activity) SearchActivity.this).overridePendingTransition(0, 0);
        }
}