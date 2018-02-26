package com.ogonek.eventsappserver.service;

import com.ogonek.eventsappserver.Pojo.PojoGroup;
import com.ogonek.eventsappserver.Pojo.PojoNewGroup;
import com.ogonek.eventsappserver.entity.Group;
import com.ogonek.eventsappserver.repository.GroupUserPairsRep;
import com.ogonek.eventsappserver.repository.GroupsRep;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GroupsService {

    @Autowired
    GroupsRep groupsRep;

    @Autowired
    GroupUserPairsRep groupUserPairsRep;

    public Long addGroup(String name, Long ownerId, Boolean privacy, String description, String picture,
                         Integer type){
        Group group = new Group(name, ownerId, privacy, description,picture, type);
        groupsRep.save(group);
        return group.getId();
    }

    public PojoGroup getPojoGroup(long id){
        Group group = groupsRep.findById(id);
        PojoGroup pojoGroup = new PojoGroup(group);
        return pojoGroup;
    }

//    public PojoGroup findByName(String part){
//        List<Group> groups = groupsRep.findAllByNameContains(part);
//        return null;
//    }
}
