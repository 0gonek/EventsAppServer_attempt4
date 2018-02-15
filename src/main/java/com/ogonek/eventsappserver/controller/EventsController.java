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

//    @RequestMapping(value = "/all", method = RequestMethod.GET)
//    public @ResponseBody Iterable<Long> getAllEvents(){
//        Iterable<Long> list = eventsService.getAllId();
//        return list;
//    }

//    @RequestMapping(value = "/name/{name}", method = RequestMethod.GET)
//    public List<Event> getByName(@PathVariable String name){
//        return eventsService.getByName(name);
//    }

//    @RequestMapping(value = "/new", method = RequestMethod.POST)
//    public @ResponseBody Iterable<Event> newEvent(@RequestParam("name") String name, @RequestParam("ownerId") String ownerId,
//                                                  @RequestParam("latitude") Double latitude, @RequestParam("longitude") Double longitude,
//                                                  @RequestParam("date") long date, @RequestParam("duration") long duration){
//    public @ResponseBody Iterable<Event> newEvent(){
//        //eventsService.addEvent(name, ownerId, latitude, longitude,date, duration, null, null);
//        eventsService.addEvent("testName", 12L, 12D, 1200D,123L, 1234L, null, null);
//        Iterable<Event> list = eventsService.getAll();
//        return list;
//    }

}
