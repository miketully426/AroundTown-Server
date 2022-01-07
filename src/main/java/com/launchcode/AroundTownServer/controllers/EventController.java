package com.launchcode.AroundTownServer.controllers;

import com.launchcode.AroundTownServer.data.EventRepository;
import com.launchcode.AroundTownServer.models.Event;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

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
                                            @PathVariable(required = false) Integer highPrice ) {
       //until we find a way to successfully save a price as a double coming from JS, the number conversion needs to happen
        //if we can figure out double storage, we can use the methods in event repository.
        Iterable<Event> allEvents = this.eventRepository.findAll();
        List<Event> matchingEvents = new ArrayList<>();

        if(highPrice == null && lowPrice == 0) {
            for(Event event : allEvents) {
                if(parseDouble(event.getEntryCost()) == lowPrice) {
                    matchingEvents.add(event);
                }
            }
        } else if (highPrice == null && lowPrice == 100) {
            for(Event event : allEvents) {
                if(parseDouble(event.getEntryCost()) > lowPrice) {
                    matchingEvents.add(event);
                }
            }

        } else {
            for(Event event : allEvents) {
                if(parseDouble(event.getEntryCost()) > lowPrice && parseDouble(event.getEntryCost()) < highPrice) {
                    matchingEvents.add(event);
                }
            }
        }
        return matchingEvents;
    }

    @GetMapping({"/filterAllFamFriendlyEntryCost/{famFriendly}/{lowPrice}/{highPrice}",
                "/filterAllFamFriendlyEntryCost/{famFriendly}/{lowPrice}" })
    public List<Event>filterByFamFriendlyAndEntryCost(@PathVariable("famFriendly") boolean famFriendly,
                                                      @PathVariable("lowPrice") Integer lowPrice,
                                                      @PathVariable(required = false) Integer highPrice) {
        //same comments about being unable to save price as a double to effectively search with repository function
        Iterable<Event> famFriendlyEvents = this.eventRepository.findByFamilyFriendly(famFriendly);
        List<Event> matchingEvents = new ArrayList<>();

        if(highPrice == null && lowPrice == 0) {
            for(Event event : famFriendlyEvents) {
                if(parseDouble(event.getEntryCost()) == lowPrice) {
                    matchingEvents.add(event);
                }
            }
        } else if (highPrice == null && lowPrice == 100) {
            for(Event event : famFriendlyEvents) {
                if(parseDouble(event.getEntryCost()) > lowPrice) {
                    matchingEvents.add(event);
                }
            }

        } else {
            for(Event event : famFriendlyEvents) {
                if(parseDouble(event.getEntryCost()) > lowPrice && parseDouble(event.getEntryCost()) < highPrice) {
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
        for(Event event : allEvents) {
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
        for(Event event : famFriendlyEvents) {
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
        if(highPrice == null && lowPrice == 0) {
            for(Event event : allEvents) {
                if(parseDouble(event.getEntryCost()) == lowPrice
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
            for(Event event : allEvents) {
                if(parseDouble(event.getEntryCost()) > lowPrice &&
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
            for(Event event : allEvents) {
                if(parseDouble(event.getEntryCost()) > lowPrice && parseDouble(event.getEntryCost()) < highPrice
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
        if(highPrice == null && lowPrice == 0) {
            for(Event event : famFriendlyEvents) {
                if(parseDouble(event.getEntryCost()) == lowPrice
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
            for(Event event : famFriendlyEvents) {
                if(parseDouble(event.getEntryCost()) > lowPrice &&
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
            for(Event event : famFriendlyEvents) {
                if(parseDouble(event.getEntryCost()) > lowPrice && parseDouble(event.getEntryCost()) < highPrice
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

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Map<String, Boolean>> deleteEvent(@PathVariable int eventId){
        Optional<Event> event = eventRepository.findById(eventId);
        eventRepository.deleteById(eventId);
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return ResponseEntity.ok(response);
    }

}
