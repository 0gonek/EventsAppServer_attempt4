package com.ogonek.eventsappserver.service;

import com.ogonek.eventsappserver.Pojo.PojoNameAndAvatar;
import com.ogonek.eventsappserver.Pojo.PojoUsersList;
import com.ogonek.eventsappserver.entity.IdPair;
import com.ogonek.eventsappserver.entity.User;
import com.ogonek.eventsappserver.repository.EventsRep;
import com.ogonek.eventsappserver.repository.IdPairsRep;
import com.ogonek.eventsappserver.repository.UsersRep;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

@Service
public class IdPairsService {

    @Autowired
    IdPairsRep idPairsRep;
    @Autowired
    UsersRep usersRep;
    @Autowired
    UsersService usersService;
    @Autowired
    EventsRep eventsRep;

    public boolean addPair(long userId, long eventId){
        if(idPairsRep.findByUserIdAndEventId(userId,eventId) == null){
            IdPair idPair = new IdPair(userId, eventId);
            idPairsRep.save(idPair);
            long participants = eventsRep.findById(eventId).getParticipants();
            eventsRep.changeEventParticipants(eventId, participants+1);
            return true;
        }
        return false;
    }

    private class UserIdNameIntegrationId{
        public long id;
        public String name;

        public UserIdNameIntegrationId(long id, String name) {
            this.id = id;
            this.name = name;
        }
    }

    public PojoUsersList getUsersByEventId(long eventId){
        List<IdPair> idPairs = idPairsRep.findAllByEventId(eventId);
        if(idPairs.size() == 0) return null;
        List<Long> userIds = new ArrayList<>();
        for (IdPair idPair: idPairs) {
            userIds.add(idPair.getUserId());
        }
        List<Long> ids = new ArrayList<>();
        List<String> names = new ArrayList<>();
        for (Long userId:userIds) {
            User user = usersRep.findById(userId);
            ids.add(userId);
            names.add(user.getName());
        }

        Long[] userIdsArray = new Long[userIds.size()];
        userIdsArray = userIds.toArray(userIdsArray);
        List<String> avatars = usersService.getSmallAvatarsVK(userIdsArray);

        PojoNameAndAvatar[] pojoNameAndAvatars = new PojoNameAndAvatar[avatars.size()];
        for (int i = 0; i < avatars.size(); i++) {
            pojoNameAndAvatars[i] = new PojoNameAndAvatar(ids.get(i), names.get(i), avatars.get(i));
        }
        return new PojoUsersList(pojoNameAndAvatars);
    }

    public Iterable<IdPair> getAll(){
        Iterable<IdPair> allPairs = idPairsRep.findAll();
        return allPairs;
    }

    public IdPair getById(long iD){
        IdPair pair = idPairsRep.findById(iD);
        return pair;
    }

    public List<IdPair> getAllByOwnerId(long userId){
        List<IdPair> pairs = idPairsRep.findAllByUserId(userId);
        return pairs;
    }

    public List<IdPair> getByEventId(long eventId){
        List<IdPair> pairs = idPairsRep.findAllByEventId(eventId);
        return pairs;
    }

    public boolean deleteIdPair(long iD){
        idPairsRep.deleteById(iD);
        return true;
    }

    public List<PojoNameAndAvatar> getNamesAndAvatars(long eventId){
        List<IdPair> idPairs = idPairsRep.findAllByEventId(eventId);
        List<PojoNameAndAvatar> pojoNameAndAvatars = new LinkedList<PojoNameAndAvatar>();
        for (IdPair idPair : idPairs) {
            long userId = idPair.getUserId();
            User user = usersRep.findById(userId);
            pojoNameAndAvatars.add(new PojoNameAndAvatar(userId, user.getName(), usersService.getBigAvatarVK(userId)));
        }
        return pojoNameAndAvatars;
    }

}
