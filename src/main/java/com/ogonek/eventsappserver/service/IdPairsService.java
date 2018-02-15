package com.ogonek.eventsappserver.service;

import com.ogonek.eventsappserver.Pojo.PojoNameAndAvatar;
import com.ogonek.eventsappserver.entity.IdPair;
import com.ogonek.eventsappserver.entity.User;
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

    public long addPair(long userId, long eventId){
        IdPair idPair = new IdPair(userId, eventId);
        idPairsRep.save(idPair);
        return idPair.getId();
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
