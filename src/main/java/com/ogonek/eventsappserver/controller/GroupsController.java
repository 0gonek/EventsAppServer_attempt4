package com.ogonek.eventsappserver.controller;

import com.ogonek.eventsappserver.Pojo.PojoGroup;
import com.ogonek.eventsappserver.Pojo.PojoNewGroup;
import com.ogonek.eventsappserver.service.GroupUserPairsService;
import com.ogonek.eventsappserver.service.GroupsService;
import com.ogonek.eventsappserver.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/groups")
public class GroupsController {

    @Autowired
    GroupsService groupsService;

    @Autowired
    GroupUserPairsService groupUserPairsService;

    @Autowired
    UsersService usersService;

    @Modifying
    @RequestMapping(value = "/new", method = RequestMethod.POST)
    public long newGroup(@RequestBody PojoNewGroup pojoNewGroup){
        if(usersService.verifyToken(pojoNewGroup.getOwnerId(), pojoNewGroup.getToken())) {
            long groupId = groupsService.addGroup(pojoNewGroup.getName(), pojoNewGroup.getOwnerId(), pojoNewGroup.getPrivacy(), pojoNewGroup.getDescription(),
                    pojoNewGroup.getPicture(), pojoNewGroup.getType());
            groupUserPairsService.addGroupUserPair(pojoNewGroup.getOwnerId(), groupId);
            return groupId;
        }
        return -1;
    }

    @Modifying
    @RequestMapping(value = "/get", method = RequestMethod.GET)
    public PojoGroup getGroup(@RequestParam("id") Long userId, @RequestParam("token") String token,
                              @RequestParam("group_id") Long group_id){
        if(usersService.verifyToken(userId, token)) {
            return groupsService.getPojoGroup(group_id);
        }
        return null;
    }
}
