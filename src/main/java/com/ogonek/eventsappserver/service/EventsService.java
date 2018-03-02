package com.ogonek.eventsappserver.service;

import com.ogonek.eventsappserver.Pojo.*;
import com.ogonek.eventsappserver.entity.Event;
import com.ogonek.eventsappserver.entity.IdPair;
import com.ogonek.eventsappserver.entity.OwnerIdPair;
import com.ogonek.eventsappserver.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
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

    @Autowired
    GroupsRep groupsRep;

    public Iterable<Event> getAll() {
        Iterable<Event> iterable = eventsRep.findAll();
        return iterable;
    }

    public PojoEvent getPojoEvent(Long eventId, Long userId){
        Event event = eventsRep.findById(eventId);
        boolean isAccepted = false;
        if(idPairsRep.findByUserIdAndEventId(userId, eventId) != null) isAccepted = true;
        String groupName;
        if(event.getGroupID() == null)
            groupName = null;
        else
            groupName = groupsRep.findById(event.getGroupID()).getName();

        PojoEvent pojoEvent = new PojoEvent(event.getId(), event.getName(), event.getOwnerId(), event.getLatitude(), event.getLongitude(),
                event.getDate(), event.getEndTime()-event.getDate(), event.getPrivacy(), event.getDescription(), event.getPathToThePicture(),
                event.getType(), event.getParticipants(), event.getGroupID(), isAccepted, groupName);
        return pojoEvent;
    }

    public PojoEventsForMap getEventsBetween(Long userId, double minLatitude, double maxLatitude, double minLongitude, double maxLongitude){
        Date date = new Date();
        Long currentTime = date.getTime();
        List<Event> events = eventsRep.findByLatitudeGreaterThanAndLatitudeLessThanAndLongitudeGreaterThanAndLongitudeLessThanOrEndTimeLessThan(minLatitude,
                maxLatitude, minLongitude, maxLongitude, currentTime);
        events = removeOldAndPrivate(events, currentTime, userId);
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

    // CHECK INDEXES

    public PojoSmallEvents findByName(String part, Long userId, Integer offset, Integer quantity){
        List<Event> events = eventsRep.findAllByNameContains(part);
        events = removeOldAndPrivate(events, new Date().getTime(), userId);
        if(events.size() == 0) return null;
        PojoSmallEvents pojoSmallEvents;
        if(events.size() - offset <= quantity)
            pojoSmallEvents = toSmallEvents(events.subList(offset, events.size()));
        else
            pojoSmallEvents = toSmallEvents(events.subList(offset, offset+quantity));
        return pojoSmallEvents;
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
                         Long endTime, Boolean privacy, String description, byte[] picture, Long groupId){
        String pathToThePicture = "Error";
        //PICTURE!
        if(groupId != null) {
            if (groupUserPairsRep.findByUserIdAndGroupId(ownerId, groupId) == null)
                return -1;
        }
        Event event = new Event(name, ownerId, latitude, longitude, date, type, endTime, privacy, description, pathToThePicture, groupId);
        eventsRep.save(event);
        String path = savePicture(event.getId(), picture);
        eventsRep.changeEventPathToThePicture(event.getId(), path);
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

    private List<Event> removeOldAndPrivate(List<Event> events, Long currentTime, Long userId){
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
        return events;
    }

    public boolean changeEvent(PojoChangeEvent pojoChangeEvent){
        Event oldEvent = eventsRep.findById(pojoChangeEvent.getId());
        if (oldEvent == null) return false;
        if(pojoChangeEvent.getOwnerId() == oldEvent.getOwnerId()){
            eventsRep.changeEventDate(oldEvent.getId(), pojoChangeEvent.getDate());
            eventsRep.changeEventEndTime(oldEvent.getId(), oldEvent.getDate() + pojoChangeEvent.getDuration());
            eventsRep.changeEventLatitude(oldEvent.getId(), pojoChangeEvent.getLatitude());
            eventsRep.changeEventLongitude(oldEvent.getId(), pojoChangeEvent.getLongitude());
            eventsRep.changeEventDescription(oldEvent.getId(), pojoChangeEvent.getDescription());
            eventsRep.changeEventPathToThePicture(oldEvent.getId(), "ChangedError");
            eventsRep.changeEventPrivacy(oldEvent.getId(), pojoChangeEvent.getPrivacy());
            if(pojoChangeEvent.getPicture() != null){
                if(pojoChangeEvent.getPicture().length == 0){
                    eventsRep.changeEventPathToThePicture(oldEvent.getId(), null);
                }
                else{
                    String path = savePicture(oldEvent.getId(), pojoChangeEvent.getPicture());
                    eventsRep.changeEventPathToThePicture(oldEvent.getId(), path);
                }
            }
            return true;
        }
        return false;
    }

    public String savePicture(Long id, byte[] picture){
        String directory = org.apache.commons.codec.digest.DigestUtils.sha256Hex(id.toString());
        try {
            try (FileOutputStream fos = new FileOutputStream("pictures/" + directory)) {
                fos.write(picture);
                fos.close();
            }
            return directory;
        }
        catch (Exception ex){
            return null;
        }
    }

    public byte[]  getPicture(String dirrectory){
        File imgPath = new File("pictures/" + dirrectory);
        byte[] buffer = new byte[1024];

        try {
            ByteArrayOutputStream sout = new ByteArrayOutputStream();
            FileInputStream fin = new FileInputStream(imgPath);
            int read;
            while ((read = fin.read(buffer)) != -1) {
                sout.write(buffer, 0, read);
            }
            sout.close();
            fin.close();
            byte[] output = sout.toByteArray();
            return output;
        }catch (Exception ex){
            return null;
        }
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
