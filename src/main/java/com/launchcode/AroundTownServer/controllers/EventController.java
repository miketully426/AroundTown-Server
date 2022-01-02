package com.launchcode.AroundTownServer.controllers;

import com.launchcode.AroundTownServer.data.EventRepository;
import com.launchcode.AroundTownServer.models.Event;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("api")
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

    @GetMapping("/searchByKeyword/{searchTerm}")
    public List<Event> searchEventsByKeyword(@PathVariable("searchTerm") String searchTerm) {
        Iterable<Event> allEvents = this.eventRepository.findAll();
        List<Event> matchingEvents = new ArrayList<>();
        for(Event event : allEvents) {
            if (event.getName().toLowerCase().contains(searchTerm)
                    || event.getDescription().toLowerCase().contains(searchTerm)
                    || event.getZipCode().contains(searchTerm)
            ) {
                matchingEvents.add(event);
            }
        }
        return matchingEvents;
    }

}
