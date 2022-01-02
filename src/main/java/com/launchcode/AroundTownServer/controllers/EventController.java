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

import static java.lang.Double.parseDouble;


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
    @GetMapping("/events/getPriceById/{eventId}")
    public Double getPriceByEventId(@PathVariable("eventId") int eventId) {
        Event event = eventRepository.findById(eventId).get();
        return parseDouble(event.getEntryCost());
        //this function will be useless when we are able to save entryCost as a double from front,
        //as we won't need to pull and convert the price specifically. Right now this is not ideal.
    }

    @PutMapping("/events/{eventId}")
    public ResponseEntity<Event> updateEvent(@PathVariable("eventId") int eventId, @Valid @RequestBody Event eventDetails) {
        Optional<Event> event = eventRepository.findById(eventId);
        Event eventData = event.get();

        if(event.isPresent()) {
            Event updatedEvent = eventDetails;
            eventData.setName(updatedEvent.getName());
            eventData.setDescription(updatedEvent.getDescription());
            eventData.setLocationName(updatedEvent.getLocationName());
            eventData.setAddress((updatedEvent.getAddress()));
            eventData.setCity(updatedEvent.getCity());
            eventData.setState(updatedEvent.getState());
            eventData.setZip(updatedEvent.getZip());
            eventData.setDate(updatedEvent.getDate());
            eventData.setTime(updatedEvent.getTime());
            eventData.setEntryCost((updatedEvent.getEntryCost()));
            eventData.setFamilyFriendly(updatedEvent.isFamilyFriendly());
            return new ResponseEntity<>(eventRepository.save(eventData), HttpStatus.OK);

        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
