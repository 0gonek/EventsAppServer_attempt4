package com.ogonek.eventsappserver.repository;

import com.ogonek.eventsappserver.entity.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface EventsRep extends JpaRepository<Event, Long> {
    Event findById (long id);
    @Transactional
    void deleteById(long id);
}
