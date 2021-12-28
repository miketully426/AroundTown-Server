package com.launchcode.AroundTownServer.controllers;

import com.launchcode.AroundTownServer.data.EventRepository;
import com.launchcode.AroundTownServer.models.Event;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

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

//    @GetMapping("/events/{eventId}")
//    public Optional<Event> getEventById(@PathVariable("eventId") int eventId) {
//        return eventRepository.findById(eventId);
//    }

    @GetMapping("/events/{eventId}")
    public Optional<Event> getEventById(@PathVariable int eventId) {
        return eventRepository.findById(eventId);
    }

    //can we have @GetMapping in here for multiple functions
}

