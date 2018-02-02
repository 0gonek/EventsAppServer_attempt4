package com.ogonek.eventsappserver.controller;

import com.ogonek.eventsappserver.entity.Event;
import com.ogonek.eventsappserver.repository.EventsRep;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/events")
public class EventsController {

    @Autowired
    EventsRep eventsRep;

    @RequestMapping("/save")
    public String Save(){
        eventsRep.save(new Event("Test"));
        return "Saved";
    }

    @RequestMapping("/get")
    public String get1(){
        return eventsRep.findById(2).toString();
    }
}
