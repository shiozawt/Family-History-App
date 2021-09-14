package com.example.familymap.shared.model;

/**
 * stores data classes for Event objects
 */
public class Event {
    private String eventID;
    private String associatedUsername;
    private String personID;
    private float latitude;
    private float longitude;
    private String country;
    private String city;
    private String eventType;
    private int year;

    /**
     *
     * @param eventID
     * @param associatedUsername
     * @param personID
     * @param latitude
     * @param longitude
     * @param country
     * @param city
     * @param eventType
     * @param year
     */
    public Event(String eventID, String associatedUsername, String personID, float latitude, float longitude,
                 String country, String city, String eventType, int year){
        this.eventID = eventID;
        this.associatedUsername = associatedUsername;
        this.personID = personID;
        this.latitude = latitude;
        this.longitude = longitude;
        this.country = country;
        this.city = city;
        this.eventType = eventType;
        this.year = year;
    }


    /**
     * Get the event id
     */
    public String getEventID(){
        return eventID;
    }

    /**
     * Set the event id
     */
    public void setEventID(){
        this.eventID = eventID;
    }

    /**
     * Get username
     */
    public String getAssociatedUsername() {
        return associatedUsername;
    }

    /**
     * Set username
     */
    public void setAssociatedUsername(String associatedUsername) {
        this.associatedUsername = associatedUsername;
    }

    /**
     * Get the person id
     */
    public String getPersonID() {
        return personID;
    }

    /**
     * Set the person id
     */
    public void setPersonID(String personID) {
        this.personID = personID;
    }

    /**
     * Get the latitude
     */
    public float getLatitude() {
        return latitude;
    }

    /**
     * Set the latitude
     */
    public void setLatitude(float latitude) {
        this.latitude = latitude;
    }

    /**
     * Get the longitude
     */
    public float getLongitude() {
        return longitude;
    }

    /**
     * Set the longitude
     */
    public void setLongitude(float longitude) {
        this.longitude = longitude;
    }

    /**
     * Get country
     */
    public String getCountry() {
        return country;
    }

    /**
     * Set country
     */
    public void setCountry(String Country) { this.country = Country; }

    /**
     * Get city
     */
    public String getCity() {
        return city;
    }

    /**
     * Set city
     */
    public void setCity(String city) {
        this.city = city;
    }

    /**
     * Get event type
     */
    public String getEventType() {
        return eventType;
    }

    /**
     * Set event type
     */
    public void setEventType(String eventType) {
        this.eventType = eventType;
    }

    /**
     * Get year
     */
    public int getYear() {
        return year;
    }

    /**
     * Set year
     */
    public void setYear(int year) {
        this.year = year;
    }

    /**
     *
     * @param obj
     * @return
     */
    @Override
    public boolean equals(Object obj) {
        if(obj == null){
            return false;
        }
        if(obj instanceof Event){
            Event eventObj = (Event)obj;
            return eventObj.getEventID().equals(getEventID()) &&
                    eventObj.getAssociatedUsername().equals(getAssociatedUsername()) &&
                    eventObj.getPersonID().equals(getPersonID()) &&
                    eventObj.getLatitude() == getLatitude() &&
                    eventObj.getLongitude() == getLongitude() &&
                    eventObj.getCountry().equals(getCountry()) &&
                    eventObj.getCity().equals(getCity()) &&
                    eventObj.getEventType().equals(getEventType()) &&
                    eventObj.getYear() == (getYear());
        }
        else{
            return false;
        }
    }
}
