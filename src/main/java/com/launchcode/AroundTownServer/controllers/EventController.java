//package com.launchcode.AroundTownServer.controllers;
//
//import com.launchcode.AroundTownServer.data.EventRepository;
//import com.launchcode.AroundTownServer.models.Event;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//
//    @RestController
//    @CrossOrigin(origins = "http://localhost:4200")
//    @RequestMapping("/api")
//    @ResponseBody
//    public class EventController {
//
//        @Autowired
//        private final EventRepository eventRepository;
//
//        public EventController(EventRepository userRepository) {
//            this.eventRepository = userRepository;
//        }
//
//        @GetMapping("/events")
//        public List<Event> getAllEvents() {
//            return (List<Event>) eventRepository.findAll();
//        }
//
//        @PostMapping("/events")
//        void addEvent(@RequestBody Event event) {
//            eventRepository.save(event);
//        }
//
//        @GetMapping("/getName")
//        Event getName(@RequestBody Event event) {
//            return eventRepository.findById(event.eventId).get();
//        }
//    }

package com.launchcode.AroundTownServer.controllers;

import com.launchcode.AroundTownServer.data.EventRepository;
import com.launchcode.AroundTownServer.models.Event;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
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

    @GetMapping("/eventid")
        Event getEventById(@RequestBody Event event) {
            return eventRepository.findById(event.eventId).get();
        }

}

