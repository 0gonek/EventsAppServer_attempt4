package com.ogonek.eventsappserver.service;

import com.ogonek.eventsappserver.entity.IdPair;
import com.ogonek.eventsappserver.repository.IdPairsRep;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class IdPairsService {

    @Autowired
    IdPairsRep idPairsRep;

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

}
