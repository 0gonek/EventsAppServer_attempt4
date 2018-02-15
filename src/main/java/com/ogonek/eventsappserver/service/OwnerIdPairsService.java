package com.ogonek.eventsappserver.service;

import com.ogonek.eventsappserver.entity.OwnerIdPair;
import com.ogonek.eventsappserver.repository.OwnerIdPairsRep;
import com.ogonek.eventsappserver.repository.UsersRep;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OwnerIdPairsService {

    @Autowired
    OwnerIdPairsRep ownerIdPairsRep;

    @Autowired
    UsersRep usersRep;

    public long addOwnerIdPair(long userId, long eventId) {
        OwnerIdPair ownerIdPair = new OwnerIdPair(userId, eventId);
        ownerIdPairsRep.save(ownerIdPair);
        return ownerIdPair.getId();
    }

    public Iterable<OwnerIdPair> getAll(){
        Iterable<OwnerIdPair> allOwnerIdPairs = ownerIdPairsRep.findAll();
        return allOwnerIdPairs;
    }

    public OwnerIdPair getById(long iD){
        OwnerIdPair pair = ownerIdPairsRep.findById(iD);
        return pair;
    }

    public List<OwnerIdPair> getAllByOwnerId(long ownerId){
        List<OwnerIdPair> pairs = ownerIdPairsRep.findAllByOwnerId(ownerId);
        return pairs;
    }

    public OwnerIdPair getByEventId(long eventId){
        OwnerIdPair pair = ownerIdPairsRep.findByEventId(eventId);
        return pair;
    }

    public boolean deleteOwnerIdPair(long iD){
        ownerIdPairsRep.deleteById(iD);
        return true;
    }
}
