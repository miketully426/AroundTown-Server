package com.launchcode.AroundTownServer.controllers;

import com.launchcode.AroundTownServer.data.EventRepository;
import com.launchcode.AroundTownServer.models.Event;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/api")
@ResponseBody
public class EventController {

    @Autowired
    private final EventRepository eventRepository;

    public EventController (EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    @GetMapping("/events")
    public List<Event> getAllEvents() {
        return (List<Event>) eventRepository.findAll();
    }

    @PostMapping("/events")
    void addEvent(@RequestBody Event event) {
        eventRepository.save(event);
    }

    @GetMapping("/events/{searchTerm}")
    public List<Event> searchEventsByKeyword(@PathVariable("searchTerm") String searchTerm) {
        Iterable<Event> allEvents = this.eventRepository.findAll();
        List<Event> matchingEvents = new ArrayList<>();
        for(Event event : allEvents) {
            if (event.getName().toLowerCase().contains(searchTerm)
                    || event.getDescription().toLowerCase().contains(searchTerm)
                    || event.getLocationName().toLowerCase().contains(searchTerm)
                    || event.getZipCode().equals(searchTerm)
                    || event.getCity().toLowerCase().contains(searchTerm)
                    || event.getState().toLowerCase().contains(searchTerm)
            ) {
                matchingEvents.add(event);
            }
        }
        return matchingEvents;
    }

}
