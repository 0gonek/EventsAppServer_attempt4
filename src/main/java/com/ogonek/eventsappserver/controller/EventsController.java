package com.ogonek.eventsappserver.controller;

import com.ogonek.eventsappserver.Pojo.PojoEvent;
import com.ogonek.eventsappserver.Pojo.PojoUsersList;
import com.ogonek.eventsappserver.entity.Event;
import com.ogonek.eventsappserver.entity.IdPair;
import com.ogonek.eventsappserver.entity.OwnerIdPair;
import com.ogonek.eventsappserver.repository.EventsRep;
import com.ogonek.eventsappserver.service.EventsService;
import com.ogonek.eventsappserver.service.IdPairsService;
import com.ogonek.eventsappserver.service.OwnerIdPairsService;
import com.ogonek.eventsappserver.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/events")
public class EventsController {

    @Autowired
    EventsService eventsService;

    @Autowired
    IdPairsService idPairsService;

    @Autowired
    UsersService usersService;

    @Autowired
    OwnerIdPairsService ownerIdPairsService;

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

    @Modifying
    @RequestMapping(value = "/get", method = RequestMethod.GET)
    public PojoEvent getEvent(@RequestParam("id") Long userId, @RequestParam("token") String token,
                               @RequestParam("event_id") Long eventId){
        if(usersService.verifyToken(userId, token)) {
            return eventsService.getPojoEvent(eventId);
        }
        return null;
    }

    @Modifying
    @RequestMapping(value = "/new", method = RequestMethod.POST)
    public long changeUserName(@RequestParam("id") Long id, @RequestParam("token") String token, @RequestBody PojoEvent pojoEvent){
        if(usersService.verifyToken(id, token)) {
            long eventId = eventsService.addEvent(pojoEvent.getName(), pojoEvent.getOwnerId(), pojoEvent.getLatitude(),
                    pojoEvent.getLongitude(), pojoEvent.getDate(), pojoEvent.getType(), pojoEvent.getDuration(),
                    pojoEvent.getDescription(), pojoEvent.getPicture() + "ERROR");
            idPairsService.addPair(id, eventId);
            ownerIdPairsService.addOwnerIdPair(id, eventId);
            return eventId;
        }
        return -1;
    }

    @Modifying
    @RequestMapping(value = "/new_perticipant", method = RequestMethod.GET)
    public boolean changeUserName(@RequestParam("id") Long id, @RequestParam("token") String token,
                                  @RequestParam("event_id") Long eventId){
        if(usersService.verifyToken(id, token)) {
            return idPairsService.addPair(id, eventId);
        }
        return false;
    }

    @Modifying
    @RequestMapping(value = "/delete", method = RequestMethod.GET)
    public boolean deleteEvent(@RequestParam("id") Long userId, @RequestParam("token") String token,
                                  @RequestParam("event_id") Long eventId){
        if(usersService.verifyToken(userId, token)) {
            return eventsService.deleteOwnEvent(userId, eventId);
        }
        return false;
    }

    @Modifying
    @RequestMapping(value = "/participants", method = RequestMethod.GET)
    public PojoUsersList getEventParticipants(@RequestParam("id") Long userId, @RequestParam("token") String token,
                                              @RequestParam("event_id") Long eventId){
        if(usersService.verifyToken(userId, token)) {
            return idPairsService.getUsersByEventId(eventId);
        }
        return null;
    }

}
