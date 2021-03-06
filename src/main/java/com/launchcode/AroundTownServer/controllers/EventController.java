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

    @PostMapping("/saveEvent")
    public Event  addEvent(@RequestBody Event event) {
        eventRepository.save(event);
        return event;

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

    @PostMapping("/filterAllDate")
    //Maybe we could parse the date on the front and send just numbers for year, month, day, then match back here
            //if this doesn't work
    public List<Event> filterAllByDate(@RequestBody HashMap<String, Number> date) {
        return (List<Event>) eventRepository.findByDate(date);
    }

    @PostMapping("/filterAllFamFriendDate/{famFriendly}")
    public List<Event> filterAllByFamFriendlyAndDate(@PathVariable("famFriendly") boolean famFriendly,
                                                     @RequestBody HashMap<String, Number> date){
        return (List<Event>) eventRepository.findByFamilyFriendlyAndDate(famFriendly, date);
    }

    @PostMapping({"/filterAllPriceDate/{lowPrice}/{highPrice}",
            "/filterAllFamFriendlyEntryCost/{lowPrice}"})
    public List<Event> filterByPriceAndDate(@RequestBody HashMap<String, Number> date,
                                            @PathVariable("lowPrice") Integer lowPrice,
                                            @PathVariable(required = false) Integer highPrice) {

        Iterable<Event> dateEvents = this.eventRepository.findByDate(date);
        List<Event> matchingEvents = new ArrayList<>();

        if (highPrice == null && lowPrice == 0) {
            for (Event event : dateEvents) {
                if(parseDouble(event.getEntryCost()) == lowPrice) {
                    matchingEvents.add(event);
                }
            }
        } else if (highPrice == null && lowPrice == 100) {
            for (Event event : dateEvents) {
                if (parseDouble(event.getEntryCost()) > lowPrice) {
                    matchingEvents.add(event);
                }
            }
        } else {
            for (Event event : dateEvents) {
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

    @PostMapping({"/filterAllFamFriendlyPriceDate/{famFriendly}/{lowPrice}/{highPrice}",
            "/filterAllFamFriendlyPriceDate/{famFriendly}/{lowPrice}"})
    public List<Event> filterAllByFamFriendlyPriceAndDate(@RequestBody HashMap<String, Number> date,
                                                          @PathVariable("famFriendly") boolean famFriendly,
                                                          @PathVariable("lowPrice") Integer lowPrice,
                                                          @PathVariable(required = false) Integer highPrice) {
       //This can be simplified once prices are saved as numbers
        Iterable<Event> dateEvents = this.eventRepository.findByDate(date);
        List<Event> matchingEvents = new ArrayList<>();

        if (highPrice == null && lowPrice == 0) {
            for (Event event : dateEvents) {
                if(parseDouble(event.getEntryCost()) == lowPrice && event.isFamilyFriendly() == famFriendly) {
                    matchingEvents.add(event);
                }
            }
        }  else if (highPrice == null && lowPrice == 100) {
            for (Event event : dateEvents) {
                if (parseDouble(event.getEntryCost()) > lowPrice && event.isFamilyFriendly() == famFriendly) {
                    matchingEvents.add(event);
                }
            }
        }   else {
                for (Event event : dateEvents) {
                    if (parseDouble(event.getEntryCost()) > lowPrice && parseDouble(event.getEntryCost()) < highPrice
                            && event.isFamilyFriendly() == famFriendly) {
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

    @PostMapping("/searchKeywordDate/{searchTerm}")
    public List<Event> searchByKeywordAndDate(@PathVariable("searchTerm") String searchTerm,
                                              @RequestBody HashMap<String, Number> date) {
        Iterable<Event> dateEvents = this.eventRepository.findByDate(date);
        List<Event> matchingEvents = new ArrayList<>();
        String searchTermLowerCase = searchTerm.toLowerCase();

        for (Event event: dateEvents) {
            if (event.getName().toLowerCase().contains(searchTermLowerCase)
                    || event.getDescription().toLowerCase().contains(searchTermLowerCase)
                    || event.getLocationName().toLowerCase().contains(searchTermLowerCase)
                    || event.getZipCode().equals(searchTermLowerCase)
                    || event.getCity().toLowerCase().equals(searchTermLowerCase)
                    || event.getState().toLowerCase().equals(searchTermLowerCase)) {
                matchingEvents.add(event);
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

    @PostMapping("/searchKeywordDateFamFriendly/{searchTerm}/{famFriendly}")
    public List<Event> searchByKeywordByDateAndFamFriendly(@PathVariable("searchTerm") String searchTerm,
                                                           @PathVariable("famFriendly") boolean famFriendly,
                                                           @RequestBody HashMap<String, Number> date) {
        Iterable<Event> dateEvents = this.eventRepository.findByDate(date);
        List<Event> matchingEvents = new ArrayList<>();
        String searchTermLowerCase = searchTerm.toLowerCase();

        for (Event event: dateEvents) {
            if (event.isFamilyFriendly() == famFriendly && (event.getName().toLowerCase().contains(searchTermLowerCase)
                    || event.getDescription().toLowerCase().contains(searchTermLowerCase)
                    || event.getLocationName().toLowerCase().contains(searchTermLowerCase)
                    || event.getZipCode().equals(searchTermLowerCase)
                    || event.getCity().toLowerCase().equals(searchTermLowerCase)
                    || event.getState().toLowerCase().equals(searchTermLowerCase))) {
                matchingEvents.add(event);
            }
        }
        return matchingEvents;

    }

    @PostMapping({"/searchByKeywordDatePrice/{searchTerm}/{lowPrice}/{highPrice}",
                    "/searchByKeywordDatePrice/{searchTerm}/{lowPrice}"})
    public List<Event> searchByKeywordByDateAndPrice(@RequestBody HashMap<String, Number> date,
                                                     @PathVariable("searchTerm") String searchTerm,
                                                     @PathVariable("lowPrice") Integer lowPrice,
                                                     @PathVariable(required = false) Integer highPrice) {
        Iterable<Event> dateEvents = this.eventRepository.findByDate(date);
        List<Event> matchingEvents = new ArrayList<>();
        String searchTermLowerCase = searchTerm.toLowerCase();
        if (highPrice == null && lowPrice == 0) {
            for (Event event : dateEvents) {
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
            for (Event event : dateEvents) {
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
            for (Event event : dateEvents) {
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

    @PostMapping({"/searchByKeywordFamFriendlyPrice/{searchTerm}/{famFriendly}/{lowPrice}/{highPrice}",
                    "/searchByKeywordFamFriendlyPrice/{searchTerm}/{famFriendly}/{lowPrice}/"})
    public List<Event> searchByKeywordAndDatePriceAndFamFriendly(@RequestBody HashMap<String, Number> date,
                                                                 @PathVariable("famFriendly") boolean famFriendly,
                                                                 @PathVariable("searchTerm") String searchTerm,
                                                                 @PathVariable("lowPrice") Integer lowPrice,
                                                                 @PathVariable(required = false) Integer highPrice){

        Iterable<Event> dateEvents = this.eventRepository.findByDate(date);
        List<Event> matchingEvents = new ArrayList<>();
        String searchTermLowerCase = searchTerm.toLowerCase();
        if (highPrice == null && lowPrice == 0) {
            for (Event event : dateEvents) {
                if (parseDouble(event.getEntryCost()) == lowPrice
                        && event.isFamilyFriendly() == famFriendly
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
            for (Event event : dateEvents) {
                if (parseDouble(event.getEntryCost()) > lowPrice
                        && event.isFamilyFriendly() == famFriendly
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

        } else {
            for (Event event : dateEvents) {
                if (parseDouble(event.getEntryCost()) > lowPrice && parseDouble(event.getEntryCost()) < highPrice
                        && event.isFamilyFriendly() == famFriendly
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

    @PutMapping("/edit/{eventId}")
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
//test post saveEvent function to return event??