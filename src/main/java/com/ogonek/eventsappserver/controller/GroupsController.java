package com.ogonek.eventsappserver.controller;

import com.ogonek.eventsappserver.Pojo.*;
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
    @RequestMapping(value = "/change", method = RequestMethod.POST)
    public Boolean changeGroup(@RequestBody PojoChangeGroup pojoChangeGroup){
        if(usersService.verifyToken(pojoChangeGroup.getOwnerId(), pojoChangeGroup.getToken())) {
            return groupsService.ChangeGroup(pojoChangeGroup);
        }
        return false;
    }

    @Modifying
    @RequestMapping(value = "/delete", method = RequestMethod.GET)
    public boolean deleteGroup(@RequestParam("id") Long id, @RequestParam("token") String token,
                                   @RequestParam("group_id") Long groupId){
        if(usersService.verifyToken(id, token)) {
            return groupsService.deleteOwnGroup(id, groupId);
        }
        return false;
    }

    @Modifying
    @RequestMapping(value = "/get", method = RequestMethod.GET)
    public PojoGroup getGroup(@RequestParam("id") Long userId, @RequestParam("token") String token,
                              @RequestParam("group_id") Long group_id){
        if(usersService.verifyToken(userId, token)) {
            return groupsService.getPojoGroup(group_id, userId);
        }
        return null;
    }

    @Modifying
    @RequestMapping(value = "/new_participant", method = RequestMethod.GET)
    public boolean newParticipiant(@RequestParam("id") Long id, @RequestParam("token") String token,
                                   @RequestParam("group_id") Long groupId){
        if(usersService.verifyToken(id, token)) {
            return groupUserPairsService.addGroupUserPair(id, groupId);
        }
        return false;
    }

    @Modifying
    @RequestMapping(value = "/delete_participant", method = RequestMethod.GET)
    public boolean deleteParticipiant(@RequestParam("id") Long id, @RequestParam("token") String token,
                                      @RequestParam("group_id") Long groupId,
                                      @RequestParam("participant_id") Long participantId){
        if(usersService.verifyToken(id, token)) {
            return groupUserPairsService.deleteIdPair(participantId, groupId, id);
        }
        return false;
    }

    @Modifying
    @RequestMapping(value = "/get_participants", method = RequestMethod.GET)
    public PojoUsersList getGroupParticipants(@RequestParam("id") Long userId, @RequestParam("token") String token,
                                              @RequestParam("group_id") Long groupId){
        if(usersService.verifyToken(userId, token)) {
            return groupUserPairsService.getUsersByGroupId(groupId);
        }
        return null;
    }

    @Modifying
    @RequestMapping(value = "/get_own", method = RequestMethod.GET)
    public PojoGroupIdNames getOwn(@RequestParam("id") Long id, @RequestParam("token") String token){
        if(usersService.verifyToken(id, token)) {
            return groupsService.getOwn(id);
        }
        return null;
    }

    @Modifying
    @RequestMapping(value = "/search_own", method = RequestMethod.GET)
    public PojoGroupIdNames searchOwnGroups(@RequestParam("id") Long id, @RequestParam("token") String token,
                                            @RequestParam("key_word") String keyWord, @RequestParam("offset") Integer offset,
                                            @RequestParam("quantity") Integer quantity) {
        if(usersService.verifyToken(id, token)) {
            return groupsService.findSomeOwn(keyWord, id, offset, quantity);
        }
        return null;
    }

    @Modifying
    @RequestMapping(value = "/search", method = RequestMethod.GET)
    public PojoGroupIdNames searchGroups(@RequestParam("id") Long id, @RequestParam("token") String token,
                                       @RequestParam("key_word") String keyWord, @RequestParam("offset") Integer offset,
                                       @RequestParam("quantity") Integer quantity) {
        if(usersService.verifyToken(id, token)) {
            return groupsService.findByName(keyWord, offset, quantity);
        }
        return null;
    }
}
