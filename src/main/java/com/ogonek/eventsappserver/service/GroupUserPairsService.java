package com.ogonek.eventsappserver.service;

import com.ogonek.eventsappserver.entity.GroupUserPair;
import com.ogonek.eventsappserver.repository.GroupUserPairsRep;
import com.ogonek.eventsappserver.repository.GroupsRep;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GroupUserPairsService {
    @Autowired
    GroupsRep groupsRep;

    @Autowired
    GroupUserPairsRep groupUserPairsRep;

    public boolean addGroupUserPair(long userId, long groupId){
        if(groupUserPairsRep.findByUserIdAndGroupId(userId, groupId) == null){
            GroupUserPair groupUserPair = new GroupUserPair(userId, groupId);
            if(groupsRep.findById(groupId) == null) return false;
            groupUserPairsRep.save(groupUserPair);
            Long participants = groupsRep.findById(groupId).getParticipants();
            groupsRep.changeGroupParticipants(groupId, participants+1);
            return true;
        }
        return false;
    }
}
