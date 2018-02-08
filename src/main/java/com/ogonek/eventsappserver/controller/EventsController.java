package com.ogonek.eventsappserver.controller;

import com.ogonek.eventsappserver.entity.Event;
import com.ogonek.eventsappserver.repository.EventsRep;
import com.ogonek.eventsappserver.service.EventsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/events")
public class EventsController {

    @Autowired
    EventsService eventsService;

    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public @ResponseBody Iterable<Event> getAllEvents(){
        Iterable<Event> list = eventsService.getAll();
        return list;
    }

//    @RequestMapping(value = "/name/{name}", method = RequestMethod.GET)
//    public List<Event> getByName(@PathVariable String name){
//        return eventsService.getByName(name);
//    }

    @RequestMapping(value = "/new", method = RequestMethod.GET)
    public @ResponseBody Iterable<Event> newEvent(){
        eventsService.addEvent();
        Iterable<Event> list = eventsService.getAll();
        return list;
    }

}
