package com.launchcode.AroundTownServer.models;

import lombok.*;
import javax.persistence.*;
import java.util.Objects;

@Getter @Setter @NoArgsConstructor
@Entity
public class Event {

    @Id
    @GeneratedValue
    @Setter(AccessLevel.NONE)
    private int eventId;

    private String name;

    private String description;

    private String address;

    private String city;

    private String state;

    private String zip;

    private String date;

    private String time;

    private String entryCost;

    private boolean familyFriendly;

    public Event(String name, String description, String address, String city, String state, String zip, String date, String time, String entryCost, boolean familyFriendly) {
        this.name = name;
        this.description = description;
        this.address = address;
        this.city = city;
        this.state = state;
        this.zip = zip;
        this.date = date;
        this.time = time;
        this.entryCost = entryCost;
        this.familyFriendly = familyFriendly;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Event)) return false;
        Event event = (Event) o;
        return eventId == event.eventId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(eventId);
    }

}
