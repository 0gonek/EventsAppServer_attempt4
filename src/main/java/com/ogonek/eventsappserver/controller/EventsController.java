package com.ogonek.eventsappserver.controller;

import com.ogonek.eventsappserver.Pojo.*;
import com.ogonek.eventsappserver.entity.Event;
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
    public PojoEventsForMap getEventsBetween(@RequestParam("id") Long userId, @RequestParam("token") String token,
                                             @RequestParam("min_lat") double minLatitude, @RequestParam("max_lat") double maxLatitude,
                                             @RequestParam("min_lon") double minLongitude, @RequestParam("max_lon") double maxLongitude){
        if(usersService.verifyToken(userId, token)) {
            return eventsService.getEventsBetween(userId, minLatitude, maxLatitude, minLongitude, maxLongitude);
        }
        return null;
    }

    @Modifying
    @RequestMapping(value = "/get_between_public", method = RequestMethod.GET)
    public PojoEventsForMap getEventsBetweenPublic(@RequestParam("min_lat") double minLatitude, @RequestParam("max_lat") double maxLatitude,
                                             @RequestParam("min_lon") double minLongitude, @RequestParam("max_lon") double maxLongitude){
        return eventsService.getEventsBetween(null, minLatitude, maxLatitude, minLongitude, maxLongitude);
    }

    @Modifying
    @RequestMapping(value = "/new", method = RequestMethod.POST)
    public long newEvent(@RequestBody PojoNewEvent pojoNewEvent){
        if(usersService.verifyToken(pojoNewEvent.getOwnerId(), pojoNewEvent.getToken())) {
            long eventId = eventsService.addEvent(pojoNewEvent.getName(), pojoNewEvent.getOwnerId(), pojoNewEvent.getLatitude(),
                    pojoNewEvent.getLongitude(), pojoNewEvent.getDate(), pojoNewEvent.getType(),
                    pojoNewEvent.getDate() + pojoNewEvent.getDuration(),
                    pojoNewEvent.getPrivacy(), pojoNewEvent.getDescription(), pojoNewEvent.getPicture(), pojoNewEvent.getGroupId());
            idPairsService.addPair(pojoNewEvent.getOwnerId(), eventId);
            ownerIdPairsService.addOwnerIdPair(pojoNewEvent.getOwnerId(), eventId);
            return eventId;
        }
        return -1;
    }

    @Modifying
    @RequestMapping(value = "/new_participant", method = RequestMethod.GET)
    public boolean newParticipiant(@RequestParam("id") Long id, @RequestParam("token") String token,
                                  @RequestParam("event_id") Long eventId){
        if(usersService.verifyToken(id, token)) {
            return idPairsService.addPair(id, eventId);
        }
        return false;
    }

    @Modifying
    @RequestMapping(value = "/delete", method = RequestMethod.GET)
    public boolean deleteEvent(@RequestParam("id") Long id, @RequestParam("token") String token,
                                  @RequestParam("event_id") Long eventId){
        if(usersService.verifyToken(id, token)) {
            return eventsService.deleteOwnEvent(id, eventId);
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

    @Modifying
    @RequestMapping(value = "/change_event", method = RequestMethod.POST)
    public Boolean changeEvent(@RequestBody PojoChangeEvent pojoChangeEvent){
        if(usersService.verifyToken(pojoChangeEvent.getOwnerId(), pojoChangeEvent.getToken())) {
            return eventsService.changeEvent(pojoChangeEvent);
        }
        return null;
    }
}
