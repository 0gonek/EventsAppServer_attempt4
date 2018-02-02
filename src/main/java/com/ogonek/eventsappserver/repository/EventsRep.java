package com.ogonek.eventsappserver.repository;

import com.ogonek.eventsappserver.entity.Event;
import org.springframework.data.repository.CrudRepository;

public interface EventsRep extends CrudRepository<Event, Long> {
    String findById (long id);
}
