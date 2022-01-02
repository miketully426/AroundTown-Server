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
@RequestMapping("/api/events")
@ResponseBody
public class EventController {

    @Autowired
    private final EventRepository eventRepository;

    public EventController(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    @GetMapping("")
    public List<Event> getAllEvents() {
        return (List<Event>) eventRepository.findAll();
    }

    @PostMapping("")
    void addEvent(@RequestBody Event event) {
        eventRepository.save(event);
    }

    @GetMapping("/filterAllFamFriendly/{famFriendly}")
    public List<Event> filterAllByFamFriendly(@PathVariable("famFriendly") boolean famFriendly) {
        return (List<Event>) eventRepository.findByFamilyFriendly(famFriendly);
    }

    @GetMapping("/searchByKeyword/{searchTerm}")
    public List<Event> searchEventsByKeyword(@PathVariable("searchTerm") String searchTerm) {
        Iterable<Event> allEvents = this.eventRepository.findAll();
        List<Event> matchingEvents = new ArrayList<>();
        for(Event event : allEvents) {
            if (event.getName().toLowerCase().contains(searchTerm)
                    || event.getDescription().toLowerCase().contains(searchTerm)
                    || event.getLocationName().toLowerCase().contains(searchTerm)
            ) {
                matchingEvents.add(event);
            }
        }
        return matchingEvents;
    }

    @GetMapping("/searchByKeywordFamFriendly/{searchTerm}/{famFriendly}")
    public List<Event> searchEventsByKeyWordAndFamFriendly(@PathVariable("searchTerm") String searchTerm,
                                                           @PathVariable("famFriendly") boolean famFriendly) {
        Iterable<Event> famFriendlyEvents = this.eventRepository.findByFamilyFriendly(famFriendly);
        List<Event> matchingEvents = new ArrayList<>();
        for(Event event : famFriendlyEvents) {
            if (event.getName().toLowerCase().contains(searchTerm)
                    || event.getDescription().toLowerCase().contains(searchTerm)
                    || event.getLocationName().toLowerCase().contains(searchTerm)
            ) {
                matchingEvents.add(event);
            }
        }
        return matchingEvents;
    }


}
