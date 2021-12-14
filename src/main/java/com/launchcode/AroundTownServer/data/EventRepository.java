package com.launchcode.AroundTownServer.data;

import com.launchcode.AroundTownServer.models.Event;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EventRepository extends CrudRepository<Event, Integer> {
}
