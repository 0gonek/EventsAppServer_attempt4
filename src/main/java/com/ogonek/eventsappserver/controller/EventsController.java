package com.ogonek.eventsappserver.controller;

import com.ogonek.eventsappserver.Pojo.PojoEvent;
import com.ogonek.eventsappserver.Pojo.PojoMidEvents;
import com.ogonek.eventsappserver.Pojo.PojoSmallEvents;
import com.ogonek.eventsappserver.Pojo.PojoUsersList;
import com.ogonek.eventsappserver.service.EventsService;
import com.ogonek.eventsappserver.service.IdPairsService;
import com.ogonek.eventsappserver.service.OwnerIdPairsService;
import com.ogonek.eventsappserver.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.web.bind.annotation.*;

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
    @RequestMapping(value = "/get_profile_events", method = RequestMethod.GET)
    public PojoSmallEvents getOwnEvents(@RequestParam("type") int type, @RequestParam("id") Long userId, @RequestParam("token") String token){
        if(usersService.verifyToken(userId, token)) {
            switch (type){
                case 0:
                    return eventsService.getOwnEvents(userId);
                case 1:
                    return eventsService.getPresentEvents(userId);
            }
        }
        return null;
    }

    @Modifying
    @RequestMapping(value = "/get_between", method = RequestMethod.GET)
    public PojoMidEvents getEventsBetween(@RequestParam("id") Long userId, @RequestParam("token") String token,
                                          @RequestParam("minLatitude") double minLatitude, @RequestParam("maxLatitude") double maxLatitude,
                                          @RequestParam("minLongitude") double minLongitude, @RequestParam("maxLongitude") double maxLongitude){
        if(usersService.verifyToken(userId, token)) {
            return eventsService.getEventsBetween(minLatitude, maxLatitude, minLongitude, maxLongitude);
        }
        return null;
    }

    @Modifying
    @RequestMapping(value = "/new", method = RequestMethod.POST)
    public long newEvent(@RequestParam("id") Long id, @RequestParam("token") String token, @RequestBody PojoEvent pojoEvent){
        if(usersService.verifyToken(id, token)) {
            long eventId = eventsService.addEvent(pojoEvent.getName(), id, pojoEvent.getLatitude(),
                    pojoEvent.getLongitude(), pojoEvent.getDate(), pojoEvent.getType(), pojoEvent.getDate()+pojoEvent.getDuration(),
                    pojoEvent.getDescription(), pojoEvent.getPicture() + "ERROR", pojoEvent.getGroupId());
            idPairsService.addPair(id, eventId);
            ownerIdPairsService.addOwnerIdPair(id, eventId);
            return eventId;
        }
        return -1;
    }

    @Modifying
    @RequestMapping(value = "/new_perticipant", method = RequestMethod.GET)
    public boolean newParticipiant(@RequestParam("id") Long id, @RequestParam("token") String token,
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
    @RequestMapping(value = "/get_participants", method = RequestMethod.GET)
    public PojoUsersList getEventParticipants(@RequestParam("id") Long userId, @RequestParam("token") String token,
                                              @RequestParam("event_id") Long eventId){
        if(usersService.verifyToken(userId, token)) {
            return idPairsService.getUsersByEventId(eventId);
        }
        return null;
    }

}
