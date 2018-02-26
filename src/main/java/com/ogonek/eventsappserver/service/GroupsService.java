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

    public PojoSmallGroups FindByName(String part, Long userId, Integer offset, Integer quantity){
        List<Group> groups = groupsRep.findAllByNameContains(part);
        if(groups.size() - offset <= quantity)
            return toSmallGroups(groups.subList(offset, groups.size()-1));
        else
            return toSmallGroups(groups.subList(offset, offset+quantity-1));
    }


    // CHECK INDEXES

    public PojoGroupIdNames GetSomeOwn(String part, Long userId, Integer offset, Integer quantity){
        List<Group> groups = groupsRep.findAllByNameContains(part);
        if(groups.size() - offset <= quantity)
            return toGroupIdNames(groups.subList(offset, groups.size()-1));
        else
            return toGroupIdNames(groups.subList(offset, offset+quantity-1));
    }

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
