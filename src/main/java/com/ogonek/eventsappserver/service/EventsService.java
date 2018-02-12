package com.ogonek.eventsappserver.service;

import com.ogonek.eventsappserver.entity.Event;
import com.ogonek.eventsappserver.repository.EventsRep;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EventsService {

    @Autowired
    EventsRep eventsRep;

    public Iterable<Event> getAll() {
        Iterable<Event> iterable = eventsRep.findAll();
        return iterable;
    }

//    public List<Event> getByName(String name){
//        List<Event> list = eventsRep.findByName(name);
//        return list;
//    }

    public void addEvent(){
        eventsRep.save(new Event());
    }

}
