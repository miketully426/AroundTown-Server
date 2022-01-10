package com.launchcode.AroundTownServer.controllers;

import com.launchcode.AroundTownServer.data.EventRepository;
import com.launchcode.AroundTownServer.models.Event;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import static java.lang.Double.parseDouble;


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

    @GetMapping({"filterAllEntryCost/{lowPrice}",
            "/filterAllEntryCost/{lowPrice}/{highPrice}"})

    public List<Event> filterAllByEntryCost(@PathVariable("lowPrice") Integer lowPrice,
                                            @PathVariable(required = false) Integer highPrice) {
        //until we find a way to successfully save a price as a double coming from JS, the number conversion needs to happen
        //if we can figure out double storage, we can use the methods in event repository.
        Iterable<Event> allEvents = this.eventRepository.findAll();
        List<Event> matchingEvents = new ArrayList<>();

        if (highPrice == null && lowPrice == 0) {
            for (Event event : allEvents) {
                if (parseDouble(event.getEntryCost()) == lowPrice) {
                    matchingEvents.add(event);
                }
            }
        } else if (highPrice == null && lowPrice == 100) {
            for (Event event : allEvents) {
                if (parseDouble(event.getEntryCost()) > lowPrice) {
                    matchingEvents.add(event);
                }
            }

        } else {
            for (Event event : allEvents) {
                if (parseDouble(event.getEntryCost()) > lowPrice && parseDouble(event.getEntryCost()) < highPrice) {
                    matchingEvents.add(event);
                }
            }
        }
        return matchingEvents;
    }

    @GetMapping({"/filterAllFamFriendlyEntryCost/{famFriendly}/{lowPrice}/{highPrice}",
            "/filterAllFamFriendlyEntryCost/{famFriendly}/{lowPrice}"})
    public List<Event> filterByFamFriendlyAndEntryCost(@PathVariable("famFriendly") boolean famFriendly,
                                                       @PathVariable("lowPrice") Integer lowPrice,
                                                       @PathVariable(required = false) Integer highPrice) {
        //same comments about being unable to save price as a double to effectively search with repository function
        Iterable<Event> famFriendlyEvents = this.eventRepository.findByFamilyFriendly(famFriendly);
        List<Event> matchingEvents = new ArrayList<>();

        if (highPrice == null && lowPrice == 0) {
            for (Event event : famFriendlyEvents) {
                if (parseDouble(event.getEntryCost()) == lowPrice) {
                    matchingEvents.add(event);
                }
            }
        } else if (highPrice == null && lowPrice == 100) {
            for (Event event : famFriendlyEvents) {
                if (parseDouble(event.getEntryCost()) > lowPrice) {
                    matchingEvents.add(event);
                }
            }

        } else {
            for (Event event : famFriendlyEvents) {
                if (parseDouble(event.getEntryCost()) > lowPrice && parseDouble(event.getEntryCost()) < highPrice) {
                    matchingEvents.add(event);
                }
            }
        }
        return matchingEvents;
    }

    @GetMapping("/searchByKeyword/{searchTerm}")
    public List<Event> searchEventsByKeyword(@PathVariable("searchTerm") String searchTerm) {
        Iterable<Event> allEvents = this.eventRepository.findAll();
        List<Event> matchingEvents = new ArrayList<>();
        //change all following searchTerm variable in each method to searchTerm case to create case insensitivity :)
        String searchTermLowerCase = searchTerm.toLowerCase();
        for (Event event : allEvents) {
            if (event.getName().toLowerCase().contains(searchTermLowerCase)
                    || event.getDescription().toLowerCase().contains(searchTermLowerCase)
                    || event.getLocationName().toLowerCase().contains(searchTermLowerCase)
                    || event.getZipCode().equals(searchTermLowerCase)
                    || event.getCity().toLowerCase().equals(searchTermLowerCase)
                    || event.getState().toLowerCase().equals(searchTermLowerCase)
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
        String searchTermLowerCase = searchTerm.toLowerCase();
        for (Event event : famFriendlyEvents) {
            if (event.getName().toLowerCase().contains(searchTermLowerCase)
                    || event.getDescription().toLowerCase().contains(searchTermLowerCase)
                    || event.getLocationName().toLowerCase().contains(searchTermLowerCase)
                    || event.getZipCode().equals(searchTermLowerCase)
                    || event.getCity().toLowerCase().equals(searchTermLowerCase)
                    || event.getState().toLowerCase().equals(searchTermLowerCase)
            ) {
                matchingEvents.add(event);
            }
        }
        return matchingEvents;
    }

    @GetMapping({"/searchByKeywordPrice/{searchTerm}/{lowPrice}",
            "/searchByKeywordPrice/{searchTerm}/{lowPrice}/{highPrice}"})
    public List<Event> searchByKeywordByPrice(@PathVariable("searchTerm") String searchTerm,
                                              @PathVariable("lowPrice") Integer lowPrice,
                                              @PathVariable(required = false) Integer highPrice) {
        //same comments about being unable to save price as a double to effectively search with repository function
        Iterable<Event> allEvents = this.eventRepository.findAll();
        List<Event> matchingEvents = new ArrayList<>();
        String searchTermLowerCase = searchTerm.toLowerCase();
        if (highPrice == null && lowPrice == 0) {
            for (Event event : allEvents) {
                if (parseDouble(event.getEntryCost()) == lowPrice
                        && (event.getName().toLowerCase().contains(searchTermLowerCase)
                        || event.getDescription().toLowerCase().contains(searchTermLowerCase)
                        || event.getLocationName().toLowerCase().contains(searchTermLowerCase)
                        || event.getZipCode().equals(searchTermLowerCase)
                        || event.getCity().toLowerCase().equals(searchTermLowerCase)
                        || event.getState().toLowerCase().equals(searchTermLowerCase))
                ) {
                    matchingEvents.add(event);
                }
            }
        } else if (highPrice == null && lowPrice == 100) {
            for (Event event : allEvents) {
                if (parseDouble(event.getEntryCost()) > lowPrice &&
                        (event.getName().toLowerCase().contains(searchTermLowerCase)
                                || event.getDescription().toLowerCase().contains(searchTermLowerCase)
                                || event.getLocationName().toLowerCase().contains(searchTermLowerCase)
                                || event.getZipCode().equals(searchTermLowerCase)
                                || event.getCity().toLowerCase().equals(searchTermLowerCase)
                                || event.getState().toLowerCase().equals(searchTermLowerCase))
                ) {
                    matchingEvents.add(event);
                }
            }

        } else {
            for (Event event : allEvents) {
                if (parseDouble(event.getEntryCost()) > lowPrice && parseDouble(event.getEntryCost()) < highPrice
                        && (event.getName().toLowerCase().contains(searchTermLowerCase)
                        || event.getDescription().toLowerCase().contains(searchTermLowerCase)
                        || event.getLocationName().toLowerCase().contains(searchTermLowerCase)
                        || event.getZipCode().equals(searchTermLowerCase)
                        || event.getCity().toLowerCase().equals(searchTermLowerCase)
                        || event.getState().toLowerCase().equals(searchTermLowerCase))
                ) {
                    matchingEvents.add(event);
                }
            }
        }
        return matchingEvents;
    }

    @GetMapping({"/searchByKeywordFamFriendlyPrice/{searchTerm}/{famFriendly}/{lowPrice}",
            "/searchByKeywordFamFriendlyPrice/{searchTerm}/{famFriendly}/{lowPrice}/{highPrice}"})
    public List<Event> searchByKeywordByFamFriendlyAndPrice(@PathVariable("searchTerm") String searchTerm,
                                                            @PathVariable("famFriendly") boolean famFriendly,
                                                            @PathVariable("lowPrice") Integer lowPrice,
                                                            @PathVariable(required = false) Integer highPrice) {
        //same comments about being unable to save price as a double to effectively search with repository function
        Iterable<Event> famFriendlyEvents = this.eventRepository.findByFamilyFriendly(famFriendly);
        List<Event> matchingEvents = new ArrayList<>();
        String searchTermLowerCase = searchTerm.toLowerCase();
        if (highPrice == null && lowPrice == 0) {
            for (Event event : famFriendlyEvents) {
                if (parseDouble(event.getEntryCost()) == lowPrice
                        && (event.getName().toLowerCase().contains(searchTermLowerCase)
                        || event.getDescription().toLowerCase().contains(searchTermLowerCase)
                        || event.getLocationName().toLowerCase().contains(searchTermLowerCase)
                        || event.getZipCode().equals(searchTermLowerCase)
                        || event.getCity().toLowerCase().equals(searchTermLowerCase)
                        || event.getState().toLowerCase().equals(searchTermLowerCase))
                ) {
                    matchingEvents.add(event);
                }
            }
        } else if (highPrice == null && lowPrice == 100) {
            for (Event event : famFriendlyEvents) {
                if (parseDouble(event.getEntryCost()) > lowPrice &&
                        (event.getName().toLowerCase().contains(searchTermLowerCase)
                                || event.getDescription().toLowerCase().contains(searchTermLowerCase)
                                || event.getLocationName().toLowerCase().contains(searchTermLowerCase)
                                || event.getZipCode().equals(searchTermLowerCase)
                                || event.getCity().toLowerCase().equals(searchTermLowerCase)
                                || event.getState().toLowerCase().equals(searchTermLowerCase))
                ) {
                    matchingEvents.add(event);
                }
            }

        } else {
            for (Event event : famFriendlyEvents) {
                if (parseDouble(event.getEntryCost()) > lowPrice && parseDouble(event.getEntryCost()) < highPrice
                        && (event.getName().toLowerCase().contains(searchTermLowerCase)
                        || event.getDescription().toLowerCase().contains(searchTermLowerCase)
                        || event.getLocationName().toLowerCase().contains(searchTermLowerCase)
                        || event.getZipCode().equals(searchTermLowerCase)
                        || event.getCity().toLowerCase().equals(searchTermLowerCase)
                        || event.getState().toLowerCase().equals(searchTermLowerCase))
                ) {
                    matchingEvents.add(event);
                }
            }
        }
        return matchingEvents;
    }

    @GetMapping("/{eventId}")
    public Optional<Event> getEventById(@PathVariable("eventId") int eventId) {
        return eventRepository.findById(eventId);
    }
  
    @GetMapping("/getPriceById/{eventId}")
    public Double getPriceByEventId(@PathVariable("eventId") int eventId) {
        Event event = eventRepository.findById(eventId).get();
        return parseDouble(event.getEntryCost());
        //this function will be useless when we are able to save entryCost as a double from front,
        //as we won't need to pull and convert the price specifically. Right now this is not ideal.
    }

    @GetMapping("/getDateById/{eventId}")
    public HashMap<String, Number> getDateByEventId(@PathVariable("eventId") int eventId) {
        Event event = eventRepository.findById(eventId).get();
        return event.getDate();
    }

    @GetMapping("/getTimeById/{eventId}")
    public HashMap<String, Number> getTimeByEventId(@PathVariable("eventId") int eventId) {
        Event event = eventRepository.findById(eventId).get();
        return event.getTime();
    }

    @PutMapping("/{eventId}")
    public ResponseEntity<Event> updateEvent(@PathVariable("eventId") int eventId, @Valid @RequestBody Event eventDetails) {
        Optional<Event> event = eventRepository.findById(eventId);
        Event eventData = event.get();

        if (event.isPresent()) {
            Event updatedEvent = eventDetails;
            eventData.setName(updatedEvent.getName());
            eventData.setDescription(updatedEvent.getDescription());
            eventData.setLocationName(updatedEvent.getLocationName());
            eventData.setAddress((updatedEvent.getAddress()));
            eventData.setCity(updatedEvent.getCity());
            eventData.setState(updatedEvent.getState());
            eventData.setZipCode(updatedEvent.getZipCode());
            eventData.setDate(updatedEvent.getDate());
            eventData.setTime(updatedEvent.getTime());
            eventData.setEntryCost((updatedEvent.getEntryCost()));
            eventData.setFamilyFriendly(updatedEvent.isFamilyFriendly());
            return new ResponseEntity<>(eventRepository.save(eventData), HttpStatus.OK);

        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

        @DeleteMapping("/delete/{eventId}")
        public ResponseEntity<Map<String, Boolean>> deleteEvent ( @PathVariable int eventId){
            Optional<Event> event = eventRepository.findById(eventId);
            eventRepository.deleteById(eventId);
            Map<String, Boolean> response = new HashMap<>();
            response.put("deleted", Boolean.TRUE);
            return ResponseEntity.ok(response);
        }

}
