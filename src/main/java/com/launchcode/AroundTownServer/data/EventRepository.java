package com.launchcode.AroundTownServer.data;

import com.launchcode.AroundTownServer.models.Event;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EventRepository extends CrudRepository<Event, Integer> {

    public List<Event> findByFamilyFriendly(boolean familyFriendly);

    public List<Event> findByEntryCostBetween(String lowPrice, String highPrice);

    public List<Event> findByFamilyFriendlyAndEntryCostBetween(boolean familyFriendly, String lowPrice, String highPrice);

    public List<Event> findByFamilyFriendlyAndEntryCost(boolean familyFriendly, String price);

    public List<Event> findByEntryCost(String price);

    public List<Event> findByEntryCostGreaterThan(String price);
}
