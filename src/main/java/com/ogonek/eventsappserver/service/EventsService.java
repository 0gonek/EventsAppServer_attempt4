package com.ogonek.eventsappserver.service;

import com.ogonek.eventsappserver.Pojo.*;
import com.ogonek.eventsappserver.entity.Event;
import com.ogonek.eventsappserver.entity.IdPair;
import com.ogonek.eventsappserver.entity.OwnerIdPair;
import com.ogonek.eventsappserver.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class EventsService {

    @Autowired
    EventsRep eventsRep;

    @Autowired
    GroupUserPairsRep groupUserPairsRep;

    @Autowired
    OwnerIdPairsRep ownerIdPairsRep;

    @Autowired
    IdPairsRep idPairsRep;

    public Iterable<Event> getAll() {
        Iterable<Event> iterable = eventsRep.findAll();
        return iterable;
    }

    public PojoEvent getPojoEvent(long id){
        Event event = eventsRep.findById(id);
        PojoEvent pojoEvent = new PojoEvent(event);
        return pojoEvent;
    }

    public PojoEventsForMap getEventsBetween(Long userId, double minLatitude, double maxLatitude, double minLongitude, double maxLongitude){
        Date date = new Date();
        Long currentTime = date.getTime();
        List<Event> events = eventsRep.findByLatitudeGreaterThanAndLatitudeLessThanAndLongitudeGreaterThanAndLongitudeLessThanOrEndTimeLessThan(minLatitude,
                maxLatitude, minLongitude, maxLongitude, currentTime);
        List<Long> oldEventIds = new ArrayList<>();
        int n = events.size();
        Event event;
        for (int i = 0; i < n; i++) {
            event = events.get(i);
            if(event.getEndTime() < currentTime){
                oldEventIds.add(events.get(i).getId());
                events.remove(i);
                i--;
                n--;
            }
            else{
                if(event.getPrivacy()){
                    if(userId == null || event.getGroupID() == null || groupUserPairsRep.findByUserIdAndGroupId(userId, event.getGroupID()) == null){
                        events.remove(i);
                        i--;
                        n--;
                    }
                }
            }
        }
        for (Long eventId:
             oldEventIds) {
            eventsRep.deleteById(eventId);
            ownerIdPairsRep.deleteById(ownerIdPairsRep.findByEventId(eventId).getId());
            List<IdPair> idPairs = idPairsRep.findAllByEventId(eventId);
            for (IdPair idPair:idPairs) {
                idPairsRep.deleteById(idPair.getId());
            }
        }
        return toPojoEventsForMap(events);
    }

    public PojoSmallEvents getOwnEvents(long ownerId){
        List<OwnerIdPair> pairs = ownerIdPairsRep.findAllByOwnerId(ownerId);
        List<Long> eventsId = new ArrayList<>();
        for (OwnerIdPair pair : pairs) {
            eventsId.add(pair.getEventId());
        }
        List<Event> events = eventsRep.findAll(eventsId);
        return toSmallEvents(events);
    }

    public PojoSmallEvents getPresentEvents(long userId){
        List<IdPair> pairs = idPairsRep.findAllByUserId(userId);
        List<Long> eventsId = new ArrayList<>();
        for (IdPair pair : pairs) {
            eventsId.add(pair.getEventId());
        }
        List<Event> events = eventsRep.findAll(eventsId);
        return toSmallEvents(events);
    }

    public boolean deleteOwnEvent(long userId, long eventId){
        Event event = eventsRep.findById(eventId);
        if(event.getOwnerId() == userId){
            eventsRep.deleteById(eventId);
            ownerIdPairsRep.deleteById(ownerIdPairsRep.findByEventId(eventId).getId());
            List<IdPair> idPairs = idPairsRep.findAllByEventId(eventId);
            for (IdPair idPair:idPairs) {
                idPairsRep.deleteById(idPair.getId());
            }
            return true;
        }
        return false;
    }

    public long addEvent(String name, Long ownerId, Double latitude, Double longitude, Long date, Integer type,
                         Long endTime, Boolean privacy, String description, boolean[] picture, Long groupId){
        String pathToThePicture = "Error";
        //PICTURE!
        Event event = new Event(name, ownerId, latitude, longitude, date, type, endTime, privacy, description, pathToThePicture, groupId);
        eventsRep.save(event);
        return event.getId();
    }

    private PojoEventsForMap toPojoEventsForMap(List<Event> events){
        List<PojoEventForMap> listOfEventsForMap = new ArrayList<PojoEventForMap>();
        for (Event event : events) {
            listOfEventsForMap.add(new PojoEventForMap(event));
        }
        PojoEventForMap[] pojoEventsForMap = new PojoEventForMap[events.size()];
        pojoEventsForMap = listOfEventsForMap.toArray(pojoEventsForMap);
        return new PojoEventsForMap(pojoEventsForMap);
    }

    private PojoSmallEvents toSmallEvents(List<Event> events){
        List<PojoSmallEvent> listOfSmallEvents = new ArrayList<PojoSmallEvent>();
        for (Event event : events) {
            listOfSmallEvents.add(new PojoSmallEvent(event));
        }
        PojoSmallEvent[] pojoSmallEvents = new PojoSmallEvent[events.size()];
        pojoSmallEvents = listOfSmallEvents.toArray(pojoSmallEvents);
        return new PojoSmallEvents(pojoSmallEvents);
    }

    public boolean changeEvent(PojoChangeEvent pojoChangeEvent){
        Event oldEvent = eventsRep.findById(pojoChangeEvent.getId());
        if (oldEvent == null) return false;
        if(pojoChangeEvent.getOwnerId() == oldEvent.getOwnerId()){
            if(pojoChangeEvent.getDate() != null)
                eventsRep.changeEventDate(oldEvent.getId(), pojoChangeEvent.getDate());
            if(pojoChangeEvent.getDuration() != null)
                eventsRep.changeEventEndTime(oldEvent.getId(), oldEvent.getDate() + pojoChangeEvent.getDuration());
            if(pojoChangeEvent.getLatitude() != null)
                eventsRep.changeEventLatitude(oldEvent.getId(), pojoChangeEvent.getLatitude());
            if(pojoChangeEvent.getLongitude() != null)
                eventsRep.changeEventLongitude(oldEvent.getId(), pojoChangeEvent.getLongitude());
            if(pojoChangeEvent.getDescription() != null)
                eventsRep.changeEventDescription(oldEvent.getId(), pojoChangeEvent.getDescription());
            if(pojoChangeEvent.getPicture() != null)
                eventsRep.changeEventPathToThePicture(oldEvent.getId(), "ChangedError");
            return true;
        }
        return false;
    }

    public boolean deleteOld(long time){
        eventsRep.deleteAllByEndTimeGreaterThan(time);
        return true;
    }

    public boolean changeEventName(long id, String newName){
        return eventsRep.changeEventName(id, newName) == 1;
    }

    public boolean changeEventLatitude(long id, double newLatitude){
        return eventsRep.changeEventLatitude(id, newLatitude) == 1;
    }

    public boolean changeEventLongitude(long id, double newLongitude){
        return eventsRep.changeEventLongitude(id, newLongitude) == 1;
    }

    public boolean changeEventDate(long id, long newDate){
        return eventsRep.changeEventDate(id, newDate) == 1;
    }

    public boolean changeEventEndTime(long id, long newEndTime){
        return eventsRep.changeEventEndTime(id, newEndTime) == 1;
    }

    public boolean changeEventDescription(long id, String newDescription){
        return eventsRep.changeEventDescription(id, newDescription) == 1;
    }

    public boolean changeEventPathToThePicture(long id, String newPathToThePicture){
        return eventsRep.changeEventPathToThePicture(id, newPathToThePicture) == 1;
    }

    public boolean changeEventType(long id, Integer newType){
        return eventsRep.changeEventType(id, newType) == 1;
    }

    public boolean changeEventParticipiants(long id, long newParticipiants){
        return eventsRep.changeEventParticipants(id, newParticipiants) == 1;
    }
}
