package com.ogonek.eventsappserver.service;

import com.ogonek.eventsappserver.Pojo.*;
import com.ogonek.eventsappserver.entity.Group;
import com.ogonek.eventsappserver.entity.GroupUserPair;
import com.ogonek.eventsappserver.repository.GroupUserPairsRep;
import com.ogonek.eventsappserver.repository.GroupsRep;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

@Service
public class GroupsService {

    @Autowired
    GroupsRep groupsRep;

    @Autowired
    GroupUserPairsRep groupUserPairsRep;

    @Autowired
    EventsService eventsService;

    public Long addGroup(String name, Long ownerId, Boolean privacy, String description, byte[] picture,
                         Integer type){
        String pathToThePicture = "Error";
        Group group = new Group(name, ownerId, privacy, description, pathToThePicture, type);
        groupsRep.save(group);
        String path = eventsService.savePicture(group.getId(), picture);
        groupsRep.changeGroupPathToThePicture(group.getId(), path);
        return group.getId();
    }

    public boolean ChangeGroup(PojoChangeGroup pojoChangeGroup){
        Group oldGroup = groupsRep.findById(pojoChangeGroup.getId());
        if(oldGroup == null) return false;
        if(pojoChangeGroup.getOwnerId() == oldGroup.getOwnerId()){
            groupsRep.changeGroupDescription(oldGroup.getId(), pojoChangeGroup.getDescription());
            groupsRep.changeGroupType(oldGroup.getId(), pojoChangeGroup.getType());
            if(pojoChangeGroup.getPicture() != null){
                if(pojoChangeGroup.getPicture().length == 0){
                    groupsRep.changeGroupPathToThePicture(oldGroup.getId(), null);
                }
                else{
                    String path = eventsService.savePicture(oldGroup.getId(), pojoChangeGroup.getPicture());
                    groupsRep.changeGroupPathToThePicture(oldGroup.getId(), path);
                }
            }
            return true;
        }
        return false;
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

    public PojoGroup getPojoGroup(long id, long userId){
        Group group = groupsRep.findById(id);
        PojoGroup pojoGroup = new PojoGroup(group);
        pojoGroup.setAccepted(groupUserPairsRep.findByUserIdAndGroupId(userId,id) != null);
        return pojoGroup;
    }

    // CHECK INDEXES

    public PojoGroupIdNames findByName(String part, Integer offset, Integer quantity){
        List<Group> groups = groupsRep.findAllByNameContains(part);
        if(groups.size() == 0) return null;
        PojoGroupIdNames pojoGroupIdNames;
        if(groups.size() - offset <= quantity)
            pojoGroupIdNames = toGroupIdNames(groups.subList(offset, groups.size()));
        else
            pojoGroupIdNames = toGroupIdNames(groups.subList(offset, offset+quantity));
        return pojoGroupIdNames;
    }

    public PojoGroupIdNames getOwn(Long userId){
        List<GroupUserPair> groupIds = groupUserPairsRep.findAllByUserId(userId);
        List<Group> groups = new ArrayList<>();
        for (GroupUserPair pair:groupIds
             ) {
            groups.add(groupsRep.findById(pair.getGroupId()));
        }
        if(groups.size() == 0) return null;
        PojoGroupIdNames pojoGroupIdNames = toGroupIdNames(groups);
        return pojoGroupIdNames;
    }

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
