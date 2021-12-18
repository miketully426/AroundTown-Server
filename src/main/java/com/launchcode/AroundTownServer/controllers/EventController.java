package com.launchcode.AroundTownServer.controllers;

import com.launchcode.AroundTownServer.data.EventRepository;
import com.launchcode.AroundTownServer.models.Event;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.objenesis.ObjenesisSerializer;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Observable;
import java.util.Optional;


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

    @GetMapping("/events/{eventId}")
    public Optional<Event> getEventById(@PathVariable("eventId") int eventId) {
        return eventRepository.findById(eventId);
    }

    @PutMapping("/events/{eventId}")
    public ResponseEntity<Event> updateEvent(@PathVariable("eventId") int eventId, @Valid @RequestBody Event eventDetails) {
        Optional<Event> eventData = getEventById(eventId);

            Event updatedEvent = eventData.get();
            updatedEvent.setName(eventData.get().getName());
            updatedEvent.setDescription(eventData.get().getDescription());
            updatedEvent.setLocation(eventData.get().getLocation());
            updatedEvent.setDate(eventData.get().getDate());
            updatedEvent.setTime(eventData.get().getTime());
            updatedEvent.setEntryCost(eventData.get().getEntryCost());
            updatedEvent.setFamilyFriendly(eventData.get().getFamilyFriendly());

            final Event savedEvent = eventRepository.save(updatedEvent);
            return ResponseEntity.ok(savedEvent);

    }
}
