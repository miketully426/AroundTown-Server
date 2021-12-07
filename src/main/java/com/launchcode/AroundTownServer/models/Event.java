package com.launchcode.AroundTownServer.models;


//import javax.validation.constraints.NotNull;

public class Event {

//    @NotNull(message = "Name is required to be inputted!")
    public String name;

    public String description;

    public String location;


    public Event(String name, String description, String location) {
        this.name = name;
        this.description = description;
        this.location = location;
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

    @Override
    public String toString() {
        return name;
    }
}
