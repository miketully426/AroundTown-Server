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

    public EventController(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    @GetMapping("/events")
    public List<Event> getAllEvents() {
        return (List<Event>) eventRepository.findAll();
    }

    @PostMapping("/events")
    void addEvent(@RequestBody Event event) {
        System.out.println(event.getEntryCost());
        eventRepository.save(event);
    }

    @GetMapping("/events/{filter}")
    public List<Event> filterAllEvents(@PathVariable("filter") String filter) {
        List<Event> allEvents = getAllEvents();
        List<Event> matchingEvents = new ArrayList<>();

        if(filter.equalsIgnoreCase("none")) {
            matchingEvents = allEvents;
        }
        if(filter.equalsIgnoreCase("familyFriendly")) {
            for(Event event : allEvents) {
                if(event.isFamilyFriendly()) {
                    matchingEvents.add(event);
                }
            }
        }
        if(filter.equalsIgnoreCase("notFamilyFriendly")) {
            for(Event event : allEvents) {
                if(!event.isFamilyFriendly()) {
                    matchingEvents.add(event);
                }
            }
        }
        return matchingEvents;
    }

    @GetMapping("/events/{searchTerm}/{filter}")
    public List<Event> searchEventsByKeyword(@PathVariable("searchTerm") String searchTerm, @PathVariable("filter") String filter) {
        Iterable<Event> allEvents = this.eventRepository.findAll();
        List<Event> matchingEvents = new ArrayList<>();

        if (filter.equalsIgnoreCase("none")) {
                for (Event event : allEvents) {
                    if (event.getName().toLowerCase().contains(searchTerm)
                            || event.getDescription().toLowerCase().contains(searchTerm)) {
                        matchingEvents.add(event);
                    }
                }
        }
        if (filter.equalsIgnoreCase("familyFriendly")) {
                for (Event event : allEvents) {
                    if ((event.getName().toLowerCase().contains(searchTerm)
                            || event.getDescription().toLowerCase().contains(searchTerm)) && event.isFamilyFriendly()) {
                        matchingEvents.add(event);
                    }
                }
        }
        if (filter.equalsIgnoreCase("notFamilyFriendly")) {
                for (Event event : allEvents) {
                    if ((event.getName().toLowerCase().contains(searchTerm)
                            || event.getDescription().toLowerCase().contains(searchTerm)) && !event.isFamilyFriendly()) {
                        matchingEvents.add(event);
                    }
                }

        }
        return matchingEvents;
    }
}
