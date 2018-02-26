package com.ogonek.eventsappserver.service;

import com.ogonek.eventsappserver.Pojo.PojoNameAndAvatar;
import com.ogonek.eventsappserver.Pojo.PojoUsersList;
import com.ogonek.eventsappserver.entity.GroupUserPair;
import com.ogonek.eventsappserver.entity.User;
import com.ogonek.eventsappserver.repository.GroupUserPairsRep;
import com.ogonek.eventsappserver.repository.GroupsRep;
import com.ogonek.eventsappserver.repository.UsersRep;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class GroupUserPairsService {
    @Autowired
    GroupsRep groupsRep;

    @Autowired
    GroupUserPairsRep groupUserPairsRep;

    @Autowired
    UsersRep usersRep;

    @Autowired
    UsersService usersService;

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

    public PojoUsersList getUsersByGroupId(long groupId) {
        List<GroupUserPair> groupUserPairs = groupUserPairsRep.findAllByGroupId(groupId);
        if(groupUserPairs.size() == 0) return null;
        List<Long> userIds = new ArrayList<>();
        for (GroupUserPair groupUserPair : groupUserPairs) {
            userIds.add(groupUserPair.getUserId());
        }
        List<Long> ids = new ArrayList<>();
        List<String> names = new ArrayList<>();
        for (Long userId:userIds) {
            User user = usersRep.findById(userId);
            ids.add(userId);
            names.add(user.getName());
        }

        Long[] userIdsArray = new Long[userIds.size()];
        userIdsArray = userIds.toArray(userIdsArray);
        List<String> avatars = usersService.getSmallAvatarsVK(userIdsArray);

        PojoNameAndAvatar[] pojoNameAndAvatars = new PojoNameAndAvatar[avatars.size()];
        for (int i = 0; i < avatars.size(); i++) {
            pojoNameAndAvatars[i] = new PojoNameAndAvatar(ids.get(i), names.get(i), avatars.get(i));
        }
        return new PojoUsersList(pojoNameAndAvatars);
    }
}
