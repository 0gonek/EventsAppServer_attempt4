package com.ogonek.eventsappserver.service;

import com.ogonek.eventsappserver.Pojo.PojoEvent;
import com.ogonek.eventsappserver.Pojo.PojoEventForMap;
import com.ogonek.eventsappserver.entity.Event;
import com.ogonek.eventsappserver.entity.IdPair;
import com.ogonek.eventsappserver.repository.EventsRep;
import com.ogonek.eventsappserver.repository.IdPairsRep;
import com.ogonek.eventsappserver.repository.OwnerIdPairsRep;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class EventsService {

    @Autowired
    EventsRep eventsRep;

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
        PojoEvent pojoEvent = new PojoEvent(event.getId(), event.getName(), event.getOwnerId(), event.getLatitude(),
                event.getLongitude(), event.getDate(), event.getDuration(), event.getDescription(), event.getPathToThePicture(),
                event.getType(), event.getParticipants());
        return pojoEvent;
    }

//    public List<Long> getAllId(){
//        List<Event> allEvents = eventsRep.findByLatitudeGreaterThanAndLatitudeLessThanAndLongitudeGreaterThanAndLongitudeLessThan(-200, 200,-200,200);
//        List<Long> output = new ArrayList<>();
//        for (Event e:allEvents
//             ) {
//            output.add(e.getId());
//        }
//        return output;
//    }

    public List<Event> getEventsBetween(double minLatitude, double maxLatitude, double minLongitude, double maxLongitude){
        List<Event> events = eventsRep.findByLatitudeGreaterThanAndLatitudeLessThanAndLongitudeGreaterThanAndLongitudeLessThan(minLatitude, maxLatitude, minLongitude, maxLongitude);
        return events;
    }

    public List<Event> getOwnEvents(long ownerId){
        List<Event> events = eventsRep.findAllByOwnerId(ownerId);
        return  events;
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

    public boolean changeEventDuration(long id, long newDuration){
        return eventsRep.changeEventDuration(id, newDuration) == 1;
    }

    public boolean changeEventDescription(long id, String newDescription){
        return eventsRep.changeEventDescription(id, newDescription) == 1;
    }

    public boolean changeEventPathToThePicture(long id, String newPathToThePicture){
        return eventsRep.changeEventPathToThePicture(id, newPathToThePicture) == 1;
    }

    public boolean changeEventType(long id, String newType){
        return eventsRep.changeEventType(id, newType) == 1;
    }

    public boolean changeEventParticipiants(long id, long newParticipiants){
        return eventsRep.changeEventParticipants(id, newParticipiants) == 1;
    }

    public long addEvent(String name, Long ownerId, Double latitude, Double longitude, Long date, String type, Long duration, String description, String pathToThePicture){
        Event event = new Event(name, ownerId, latitude, longitude, date, type, duration, description, pathToThePicture);
        eventsRep.save(event);
        return event.getId();
    }

    public PojoEventForMap toEventForMap(Event event){
        PojoEventForMap pojoEventForMap = new PojoEventForMap(event);
        return pojoEventForMap;
    }

    public List<PojoEventForMap> toListOfEventForMap(List<Event> events){
        List<PojoEventForMap> ListOfEventsForMap = new ArrayList<PojoEventForMap>();
        for (Event event : events) {
            ListOfEventsForMap.add(new PojoEventForMap(event));
        }
        return ListOfEventsForMap;
    }

}
