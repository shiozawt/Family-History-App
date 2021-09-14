package com.example.familymap.UserInterface;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.familymap.R;
import com.example.familymap.model.DataCache;
import com.example.familymap.shared.model.Event;
import com.example.familymap.shared.model.Person;
import com.example.familymap.shared.model.User;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Vector;

public class MapsFragment extends Fragment implements OnMapReadyCallback, GoogleMap.OnMapLoadedCallback {
    private GoogleMap map;
    ImageButton search, settings, greenMap, female, male, upButton;
    TextView textView;
    Map<Marker, Event> accessMap = new HashMap<>();
    Map<Event, Marker> eventAccessMap = new HashMap<>();
    List<Polyline> polylines = new ArrayList<>();
    Polyline spouseLine, lifeStory, familyTree, paternal, maternal, maleFilter, femaleFilter;

    @Override
    public View onCreateView(@NonNull LayoutInflater layoutInflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(layoutInflater, container, savedInstanceState);
        View view = layoutInflater.inflate(R.layout.fragment_maps, container, false);

        DataCache data = DataCache.getInstance();

        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        search = (ImageButton)view.findViewById(R.id.search_widget);
        search.setOnClickListener((v) -> {
            searchButtonClicked(view);
        });

        greenMap = (ImageButton)view.findViewById(R.id.green_map);
        male = (ImageButton)view.findViewById(R.id.male_icon);
        female = (ImageButton)view.findViewById(R.id.female_icon);

        settings = (ImageButton)view.findViewById(R.id.settings_widget);
        settings.setOnClickListener((v) -> {
            settingsButtonClicked(view);
        });

        textView = (TextView)view.findViewById(R.id.mapTextView);
        textView.setClickable(true);
        textView.setOnClickListener((v)-> {textViewClickListener(view);});

        DataCache dataCache = DataCache.getInstance();
        dataCache.getGenderLists();

        return view;
    }

    public void textViewClickListener(View a) {
        String text = textView.getText().toString();

        if(text.equals("Click on a marker to see event details")){
        }
        else{
            DataCache dataCache = DataCache.getInstance();
            Event ev = dataCache.getCurrEvent();
            Person p = dataCache.getPersonById(ev.getPersonID());
            //Person p = parserMain(text);
            Intent intent = new Intent(getActivity(), PersonActivity.class);
            intent.putExtra("person", p.getPersonID());
            startActivity(intent);
            ((Activity) getActivity()).overridePendingTransition(0, 0);
        }

    }

    public void markerListener(Marker marker) {

        DataCache data = DataCache.getInstance();

        Event e = accessMap.get(marker);
        Person p = data.getPersonById(e.getPersonID());
        textView.setText(p.getFirstName() + " " + p.getLastName() + '\n'
                + e.getEventType().toUpperCase() + ": " + e.getCity() + ", "
                + e.getCountry() + " (" +e.getYear() + ")");

        data.setCurrEvent(e);


        if(spouseLine != null) {
            spouseLine.remove();
        }

        if(familyTree != null) {
            familyTree.remove();
        }

        for(Polyline poly: polylines){
            poly.remove();
        }


        if(data.spouseLine == true) {
            if (p.getSpouseID() != null) {
                Person spouse = data.getPersonById(p.getSpouseID());
                List<Event> spouseEventList = data.getEventList(spouse.getPersonID());
                if (spouseEventList.size() != 0) {
                    Event spouseEvent = spouseEventList.get(0);
                    for (Event ev : spouseEventList) {
                        if (ev.getEventType().equals("Birth") || ev.getEventType().equals("birth") || ev.getEventType().equals("BIRTH")) {
                            spouseEvent = ev;
                        }
                    }

                    spouseLine = map.addPolyline(new PolylineOptions()
                            .add(new LatLng(e.getLatitude(), e.getLongitude()),
                                    new LatLng(spouseEvent.getLatitude(), spouseEvent.getLongitude()))
                            .width(10)
                            .color(Color.RED));

                    polylines.add(spouseLine);
                }
            }
        }

        if(data.familyTree == true){

            //TODO: display life story lines
            Person original = data.getPersonById(e.getPersonID());
            Event treeEvent = e;
            int lineSize = 13;

            while (original.getFatherID() != null) {
                Person father = data.getPersonById(original.getFatherID());
                List<Event> fatherEventList = data.getEventList(father.getPersonID());

                if (fatherEventList.size() != 0) {
                    Event fatherEvent = fatherEventList.get(0);
                    for (Event ev : fatherEventList) {
                        if (ev.getEventType().equals("Birth") || ev.getEventType().equals("birth") || ev.getEventType().equals("BIRTH")) {
                            fatherEvent = ev;
                        }
                    }

                    familyTree = map.addPolyline(new PolylineOptions()
                            .add(new LatLng(treeEvent.getLatitude(), treeEvent.getLongitude()),
                                    new LatLng(fatherEvent.getLatitude(), fatherEvent.getLongitude()))
                            .width(lineSize)
                            .color(Color.BLUE));

                    polylines.add(familyTree);
                    original = father;
                    treeEvent = fatherEvent;
                }

                if(lineSize > 4){
                    lineSize = lineSize - 4;
                }
            }

                //TODO: display family tree lines
            Person motherOriginal = data.getPersonById(e.getPersonID());
            Event motherTreeEvent = e;
            int motherLineSize = 13;

            while (motherOriginal.getMotherID() != null) {
                Person mother = data.getPersonById(motherOriginal.getMotherID());
                List<Event> motherEventList = data.getEventList(mother.getPersonID());

                if (motherEventList.size() != 0) {
                    Event motherEvent = motherEventList.get(0);
                    for (Event ev : motherEventList) {
                        if (ev.getEventType().equals("Birth") || ev.getEventType().equals("birth") || ev.getEventType().equals("BIRTH")) {
                            motherEvent = ev;
                        }
                    }
                    familyTree = map.addPolyline(new PolylineOptions()
                            .add(new LatLng(motherTreeEvent.getLatitude(), motherTreeEvent.getLongitude()),
                                    new LatLng(motherEvent.getLatitude(), motherEvent.getLongitude()))
                            .width(motherLineSize)
                            .color(Color.BLUE));

                    polylines.add(familyTree);
                    motherOriginal = mother;
                    motherTreeEvent = motherEvent;
                }

                if(motherLineSize > 4){
                    motherLineSize = motherLineSize - 4;
                }
            }
        }

        if(data.lifeStory == true){
            List<Event> lifeEvents = data.getEventList(e.getPersonID());

            lifeEvents = eventListOrganizer(lifeEvents);

            Vector<Event> lifeVec = new Vector<>();

            for(Event transfer: lifeEvents){
                lifeVec.add(transfer);
            }

            for(int i = 0; i < lifeVec.size(); ++i){
                if(lifeVec.size() > i + 1) {
                    lifeStory = map.addPolyline(new PolylineOptions()
                            .add(new LatLng(lifeVec.elementAt(i).getLatitude(), lifeVec.elementAt(i).getLongitude()),
                                    new LatLng(lifeVec.elementAt(i+1).getLatitude(), lifeVec.elementAt(i+1).getLongitude()))
                            .width(10)
                            .color(Color.MAGENTA));

                    polylines.add(lifeStory);
                }
            }
        }

        greenMap.setVisibility(View.GONE);
        male.setVisibility(View.GONE);
        female.setVisibility(View.GONE);

        if(p.getGender().charAt(0) == 'm'){
            male.setVisibility(View.VISIBLE);
        }
        else {
            female.setVisibility(View.VISIBLE);
        }

    }


    public void searchButtonClicked(View a) {
        Intent intent = new Intent(getActivity(), SearchActivity.class);
        startActivity(intent);
        ((Activity) getActivity()).overridePendingTransition(0, 0);
    }

    public void settingsButtonClicked(View a) {
        Intent intent = new Intent(getActivity(), SettingsActivity.class);
        startActivity(intent);
        ((Activity) getActivity()).overridePendingTransition(0, 0);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;
        map.setOnMapLoadedCallback(this);

        // Add a marker in Sydney and move the camera
        /*
        LatLng sydney = new LatLng(-34, 151);
        map.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        map.animateCamera(CameraUpdateFactory.newLatLng(sydney));
         */

        getEventMarkers();

        if (getArguments() != null) {
            String testText = getArguments().getString("testVal");
            DataCache dataCache = DataCache.getInstance();
            Event e = dataCache.getEventById(testText);
            if (e != null) {
                //TODO: finish this
                System.out.print('\n');
                System.out.print("SUCCESSFUL EVENT TESTING PHASE 1");
                System.out.print('\n');

                LatLng eventLocation = new LatLng(e.getLatitude(), e.getLongitude());
                map.animateCamera(CameraUpdateFactory.newLatLng(eventLocation));
                Marker m = getMarkerFromEvent(e);
                if(m == null){
                    System.out.print('\n');
                    System.out.print(e.getEventID() + e.getEventType());
                    System.out.print('\n');
                }
                markerListener(m);
                settings.setVisibility(View.INVISIBLE);
                search.setVisibility(View.INVISIBLE);
            }
        }
    }

    public void getEventMarkers(){
        DataCache instance = DataCache.getInstance();
        Map<String, List<Event>> eventLists = instance.sort_events();
        Person currUser = instance._getUser();
        List<Event> userEvents = eventLists.get(currUser.getPersonID());
        Event birthEvent = null;
        if (userEvents.size() != 0) {
            birthEvent = userEvents.get(0);
        }
        LatLng storedLat = null;

        Map<String, Event> eventMap =  instance._getEvents();
        Set<String> keys = eventMap.keySet();
        for(String s: keys){
            Event e = eventMap.get(s);

            DataCache dataCache = DataCache.getInstance();
            Person person = dataCache.getPersonById(e.getPersonID());

            String location = e.getCity();
            LatLng city = new LatLng(e.getLatitude(), e.getLongitude());

            //TODO: Big if or statement tree here for marker color
            Marker m = map.addMarker(new MarkerOptions()
                            .position(city)
                            .title(person.getFirstName() + " " + person.getLastName()));

            m = getMarkerColor(m, e);

            accessMap.put(m, e);
            eventAccessMap.put(e,m);
            map.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
                @Override
                public boolean onMarkerClick(Marker marker) {
                    markerListener(marker);
                    return false;
                }
            });
            //map.animateCamera(CameraUpdateFactory.newLatLng(city));
            if(birthEvent != null) {
                if (e.getEventID() == birthEvent.getEventID()) {
                    storedLat = city;
                }
            }
        }
        if(storedLat != null) {
            map.animateCamera(CameraUpdateFactory.newLatLng(storedLat));
        }

        if(instance.maternal == true){
            Set<String> maternalIds = instance.getMaternalAncestors();
            for(String s: maternalIds) {
                List<Event> evs = instance.getEventList(s);
                for (Event t : evs) {
                    Marker z = eventAccessMap.get(t);
                    z.setVisible(false);
                }
            }
        }

        if(instance.paternal == true){
            Set<String> paternalIds = instance.getPaternalAncestors();
            for(String s: paternalIds) {
                List<Event> evs = instance.getEventList(s);
                for (Event t : evs) {
                    Marker z = eventAccessMap.get(t);
                    z.setVisible(false);
                }
            }
        }

        if(instance.male == true){
            List<Event> maleEvents = instance.maleEvents;
            if(maleEvents != null) {
                for (Event e : maleEvents) {
                    Marker marker = eventAccessMap.get(e);
                    marker.setVisible(false);
                }
            }
        }

        if(instance.female == true){
            List<Event> femaleEvents = instance.femaleEvents;
            if(femaleEvents != null) {
                for (Event e : femaleEvents) {
                    Marker marker = eventAccessMap.get(e);
                    marker.setVisible(false);
                }
            }
        }
    }


    @Override
    public void onMapLoaded() {
        // You probably don't need this callback. It occurs after onMapReady and I have seen
        // cases where you get an error when adding markers or otherwise interacting with the map in
        // onMapReady(...) because the map isn't really all the way ready. If you see that, just
        // move all code where you interact with the map (everything after
        // map.setOnMapLoadedCallback(...) above) to here.
    }

    public Person parserMain(String input){
        Event e = null;
        String firstName = "", lastName = "", eventType = "", city = "", country = "", year = "";

        /*
        textView.setText(p.getFirstName() + " " + p.getLastName() + '\n'
                + e.getEventType().toUpperCase() + ": " + e.getCity() + ", "
                + e.getCountry() + " (" +e.getYear() + ")");

         */

        StringBuilder str = new StringBuilder(input);

        for(int i = 0; i < str.length(); i++){
            if(!Character.isWhitespace(str.charAt(i))){
                firstName = firstName + str.charAt(i);
            }
            else {
                break;
            }
        }

        str.delete(0, firstName.length() + 1);

        for(int i = 0; i < str.length(); i++){
            if(!Character.isWhitespace(str.charAt(i))){
                lastName = lastName + str.charAt(i);
            }
            else {
                break;
            }
        }

        str.delete(0, lastName.length() + 1);

        for(int i = 0; i < str.length(); i++){
            if(str.charAt(i) != ':'){
                eventType = eventType + str.charAt(i);
            }
            else {
                break;
            }
        }

        str.delete(0, eventType.length() + 2);

        for(int i = 0; i < str.length(); i++){
            if(str.charAt(i) != ','){
                city = city + str.charAt(i);
            }
            else {
                break;
            }
        }

        str.delete(0, city.length() + 2);

        for(int i = 0; i < str.length(); i++){
            if(str.charAt(i) != '('){
                country = country + str.charAt(i);
            }
            else {
                break;
            }
        }

        str.delete(0, country.length());

        for(int i = 0; i < str.length(); i++){
            if(!Character.isWhitespace(str.charAt(i))){
                year = year + str.charAt(i);
            }
        }

        /*
        StringBuilder eStr = new StringBuilder(eventType);
        eStr.deleteCharAt(eStr.length() - 1);
        eventType = eStr.toString();

         */
        eventType = eventType.toLowerCase();

        StringBuilder countryStr = new StringBuilder(country);
        countryStr.deleteCharAt(countryStr.length() - 1);
        country = countryStr.toString();


        StringBuilder yStr = new StringBuilder(year);
        yStr.deleteCharAt(yStr.length() - 1);
        yStr.deleteCharAt(0);
        year = yStr.toString();

        int yearInt = Integer.parseInt(year);

        Set<Marker> mapKeys = accessMap.keySet();
        for(Marker m: mapKeys){
            Event testEvent = accessMap.get(m);

            if(testEvent.getCountry().equals(country) &&
            testEvent.getCity().equals(city) &&
            testEvent.getEventType().equals(eventType) &&
            yearInt == testEvent.getYear()){
                e = testEvent;
            }
        }

        DataCache instance = DataCache.getInstance();
        Person p = instance.getPersonById(e.getPersonID());

        return p;
    }

    public Marker getMarkerFromEvent(Event e){
        Marker marker = null;
        Set<Marker> markerSet = accessMap.keySet();

        for(Marker m: markerSet){
            Event test = accessMap.get(m);
                if (test.equals(e)) {
                    return m;
                }
        }
        return marker;
    }

    public Marker getMarkerColor(Marker m, Event e){
        if(e.getEventType().equals("birth")) {
            m.setIcon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_MAGENTA));
        }

        else if(e.getEventType().equals("marriage")) {
            m.setIcon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN));
        }

        else if(e.getEventType().equals("death")) {
            m.setIcon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE));
        }

        else{

            DataCache dataCache = DataCache.getInstance();
            int colorMatch = dataCache.getMarkerColor(e); //takes event and returns color val based on event type
            if(colorMatch == 0){
                m.setIcon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE));
            }
            if(colorMatch == 1){
                m.setIcon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE));
            }
            if(colorMatch == 2){
                m.setIcon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));
            }
            if(colorMatch == 3){
                m.setIcon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_CYAN));
            }
            if(colorMatch == 4){
                m.setIcon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_YELLOW));
            }
            if(colorMatch == 5){
                m.setIcon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ROSE));
            }
            if(colorMatch == 6){
                m.setIcon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_VIOLET));
            }
        }

        return m;
    }

    public List<Event> eventListOrganizer(List<Event> eventList){
        List<Event> transferEventList = new LinkedList<>();

        Vector<Event> eventVec = new Vector();
        for(Event e: eventList){
            eventVec.add(e);
        }

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