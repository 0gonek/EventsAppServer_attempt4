package com.ogonek.eventsappserver.service;

import com.ogonek.eventsappserver.Pojo.*;
import com.ogonek.eventsappserver.entity.Group;
import com.ogonek.eventsappserver.entity.GroupUserPair;
import com.ogonek.eventsappserver.repository.GroupUserPairsRep;
import com.ogonek.eventsappserver.repository.GroupsRep;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class GroupsService {

    @Autowired
    GroupsRep groupsRep;

    @Autowired
    GroupUserPairsRep groupUserPairsRep;

    public Long addGroup(String name, Long ownerId, Boolean privacy, String description, String picture,
                         Integer type){
        String pathToThePicture = "Error";
        Group group = new Group(name, ownerId, privacy, description, pathToThePicture, type);
        groupsRep.save(group);
        return group.getId();
    }

    public boolean deleteOwnGroup(long userId, long groupId){
        Group group = groupsRep.findById(groupId);
        if(group.getOwnerId() == userId){
            groupsRep.deleteById(groupId);
            List<GroupUserPair> groupUserPairs = groupUserPairsRep.findAllByGroupId(groupId);
            for (GroupUserPair groupUserPair : groupUserPairs) {
                groupUserPairsRep.deleteById(groupUserPair.getId());
            }
            return true;
        }
        return false;
    }

    public PojoGroup getPojoGroup(long id){
        Group group = groupsRep.findById(id);
        PojoGroup pojoGroup = new PojoGroup(group);
        return pojoGroup;
    }

    // CHECK INDEXES

    public PojoSmallGroups findByName(String part, Integer offset, Integer quantity){
        List<Group> groups = groupsRep.findAllByNameContains(part);
        if(groups.size() == 0) return null;
        PojoSmallGroups pojoSmallGroups;
        if(groups.size() - offset <= quantity)
            pojoSmallGroups = toSmallGroups(groups.subList(offset, groups.size()));
        else
            pojoSmallGroups = toSmallGroups(groups.subList(offset, offset+quantity));
        return pojoSmallGroups;
    }
//
//    public PojoGroupIdNames getOwn(Long userId){
//        List<GroupUserPair> groupIds = groupUserPairsRep.findAllByUserId(userId);
//        for (GroupUserPair pairs:
//             ) {
//
//        }
//        List<Group> groups = groupsRep.findAllByNameContains(part);
//        for (Group group:groups
//                ) {
//            if(groupUserPairsRep.findByUserIdAndGroupId(userId, group.getId()) == null)
//                groups.remove(group);
//        }
//        if(groups.size() == 0) return null;
//        PojoGroupIdNames pojoGroupIdNames;
//        if(groups.size() - offset <= quantity)
//            pojoGroupIdNames = toGroupIdNames(groups.subList(offset, groups.size()));
//        else
//            pojoGroupIdNames = toGroupIdNames(groups.subList(offset, offset+quantity));
//        return pojoGroupIdNames;
//    }

    // CHECK INDEXES

    public PojoGroupIdNames findSomeOwn(String part, Long userId, Integer offset, Integer quantity){
        List<Group> groups = groupsRep.findAllByNameContains(part);
        for (Group group:groups
             ) {
            if(groupUserPairsRep.findByUserIdAndGroupId(userId, group.getId()) == null)
                groups.remove(group);
        }
        if(groups.size() == 0) return null;
        PojoGroupIdNames pojoGroupIdNames;
        if(groups.size() - offset <= quantity)
            pojoGroupIdNames = toGroupIdNames(groups.subList(offset, groups.size()));
        else
            pojoGroupIdNames = toGroupIdNames(groups.subList(offset, offset+quantity));
        return pojoGroupIdNames;
    }

    //public PojoGroupIdNames getOwn(Long)

    private PojoSmallGroups toSmallGroups(List<Group> groups){
        List<PojoSmallGroup> listOsSmallGroups = new ArrayList<PojoSmallGroup>();
        for (Group group : groups) {
            listOsSmallGroups.add(new PojoSmallGroup(group));
        }
        PojoSmallGroup[] pojoSmallGroups = new PojoSmallGroup[groups.size()];
        pojoSmallGroups = listOsSmallGroups.toArray(pojoSmallGroups);
        return new PojoSmallGroups(pojoSmallGroups);
    }

    private PojoGroupIdNames toGroupIdNames(List<Group> groups){
        List<PojoGroupIdName> listOfPojoGroupIdName = new ArrayList<PojoGroupIdName>();
        for (Group group : groups) {
            listOfPojoGroupIdName.add(new PojoGroupIdName(group));
        }
        PojoGroupIdName[] pojoGroupIdNames = new PojoGroupIdName[groups.size()];
        pojoGroupIdNames = listOfPojoGroupIdName.toArray(pojoGroupIdNames);
        return new PojoGroupIdNames(pojoGroupIdNames);
    }

//    public PojoGroup findByName(String part){
//        List<Group> groups = groupsRep.findAllByNameContains(part);
//        return null;
//    }
}
