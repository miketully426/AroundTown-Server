package com.launchcode.AroundTownServer.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;


//import javax.validation.constraints.NotNull;

@Entity
public class Event {

    public String name;

    public String description;

    public String location;

    @Id
    @GeneratedValue
    public int eventId;


    public Event(String name, String description, String location, int eventId) {
        this.name = name;
        this.description = description;
        this.location = location;
    }

    public Event() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public int getEventId() {
        return eventId;
    }

    @Override
    public String toString() {
        return name;
    }
}
