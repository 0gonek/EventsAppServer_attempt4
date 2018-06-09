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

/**
 * Сервис для пар пользователей и групп, в которых они состоят
 */
@Service
public class GroupUserPairsService {

    /**
     * База групп
     */
    @Autowired
    GroupsRep groupsRep;

    /**
     * База пар группа-пользователь
     */
    @Autowired
    GroupUserPairsRep groupUserPairsRep;

    /**
     * База пользователей
     */
    @Autowired
    UsersRep usersRep;

    /**
     * Сервис с пользователями
     */
    @Autowired
    UsersService usersService;

    /**
     * Добалвяет в базу пару пользователь-группа
     * @param userId айди пользователя
     * @param groupId айди группы
     */
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

    /**
     * Удалет пару из базы. Возврашает boolean - успешно ли прошло удаление
     * @param userId айди пользователя
     * @param groupId айди группы
     * @param ownerId айди того, кто хочет произвести удаление (сам пользователь или создатель группы)
     */
    public boolean deleteIdPair(long userId, long groupId, long ownerId){
        if(groupUserPairsRep.findByUserIdAndGroupId(userId,groupId) != null){
            if(groupsRep.findById(groupId) == null) return false;
            if(ownerId == userId || ownerId == groupsRep.findById(groupId).getOwnerId()){
                groupUserPairsRep.deleteById(groupUserPairsRep.findByUserIdAndGroupId(userId,groupId).getId());
                Long participants = groupsRep.findById(groupId).getParticipants();
                groupsRep.changeGroupParticipants(groupId, participants - 1);
                return true;
            }
        }
        return false;
    }

    /**
     * Возвращает лист участников группы
     * @param groupId айди группы
     */
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
