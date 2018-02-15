package com.ogonek.eventsappserver.service;

import com.ogonek.eventsappserver.Pojo.EventForMap;
import com.ogonek.eventsappserver.entity.Event;
import com.ogonek.eventsappserver.repository.EventsRep;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class EventsService {

    @Autowired
    EventsRep eventsRep;

    public Iterable<Event> getAll() {
        Iterable<Event> iterable = eventsRep.findAll();
        return iterable;
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

    public void addEvent(String name, Long ownerId, Double latitude, Double longitude, Long date, String type, Long duration, String description, String pathToThePicture){
        eventsRep.save(new Event(name, ownerId, latitude, longitude, date, type, duration, description, pathToThePicture));
    }

    public EventForMap toEventForMap(Event event){
        EventForMap eventForMap = new EventForMap(event);
        return eventForMap;
    }

    public List<EventForMap> toListOfEventForMap(List<Event> events){
        List<EventForMap> ListOfEventsForMap = new ArrayList<EventForMap>();
        for (Event event : events) {
            ListOfEventsForMap.add(new EventForMap(event));
        }
        return ListOfEventsForMap;
    }

}
