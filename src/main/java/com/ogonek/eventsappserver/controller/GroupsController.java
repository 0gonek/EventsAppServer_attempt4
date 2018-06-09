package com.ogonek.eventsappserver.controller;

import com.ogonek.eventsappserver.Pojo.*;
import com.ogonek.eventsappserver.service.GroupUserPairsService;
import com.ogonek.eventsappserver.service.GroupsService;
import com.ogonek.eventsappserver.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.web.bind.annotation.*;

/**
 * Контроллер для запросов, касающихся групп. Все запросы по пути /groups
 */
@RestController
@RequestMapping("/groups")
public class GroupsController {

    /**
     * Серивис с группами
     */
    @Autowired
    GroupsService groupsService;

    /**
     * Сервис с групп и их участников
     */
    @Autowired
    GroupUserPairsService groupUserPairsService;

    /**
     * Сервис с пользователями
     */
    @Autowired
    UsersService usersService;

    /**
     * Позволяет создать новую группу. Возвращает айди новой группы, или -1, если произошла ошибка
     * @param pojoNewGroup информация о группе
     */
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

    /**
     * Позволяет сменить информации о группе. Возвращает boolean - успешно ли прошло изменение
     * @param pojoChangeGroup новая информация о группе
     */
    @Modifying
    @RequestMapping(value = "/change", method = RequestMethod.POST)
    public Boolean changeGroup(@RequestBody PojoChangeGroup pojoChangeGroup){
        if(usersService.verifyToken(pojoChangeGroup.getOwnerId(), pojoChangeGroup.getToken())) {
            return groupsService.ChangeGroup(pojoChangeGroup);
        }
        return false;
    }

    /**
     * Позволяет удалить группу. Возвращает boolean - успешно ли прошло удаление
     * @param id айди владельца группы
     * @param token уникальный ключ владельца группы
     * @param groupId айди группы
     */
    @Modifying
    @RequestMapping(value = "/delete", method = RequestMethod.GET)
    public boolean deleteGroup(@RequestParam("id") Long id, @RequestParam("token") String token,
                               @RequestParam("group_id") Long groupId){
        if(usersService.verifyToken(id, token)) {
            return groupsService.deleteOwnGroup(id, groupId);
        }
        return false;
    }

    /**
     * Возвращает полную информацию о группе
     * @param userId айди пользователя
     * @param token уникальный ключ пользователя
     * @param group_id айди группы
     */
    @Modifying
    @RequestMapping(value = "/get", method = RequestMethod.GET)
    public PojoGroup getGroup(@RequestParam("id") Long userId, @RequestParam("token") String token,
                              @RequestParam("group_id") Long group_id){
        if(usersService.verifyToken(userId, token)) {
            return groupsService.getPojoGroup(group_id, userId);
        }
        return null;
    }

    /**
     * Позволяет добавить участника в группу. Возвращает boolean - успешно ли прошло добавление
     * @param id айди пользователя
     * @param token уникальный ключ пользователя
     * @param groupId айди группы
     */
    @Modifying
    @RequestMapping(value = "/new_participant", method = RequestMethod.GET)
    public boolean newParticipiant(@RequestParam("id") Long id, @RequestParam("token") String token,
                                   @RequestParam("group_id") Long groupId){
        if(usersService.verifyToken(id, token)) {
            return groupUserPairsService.addGroupUserPair(id, groupId);
        }
        return false;
    }

    /**
     * Позволяет удалить участника из группы. Возвращает boolean - успешно ли прошло удаление
     * @param id айди пользователя
     * @param token уникальный ключ пользователя
     * @param groupId айди группы
     * @param participantId айди участника
     */
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

    /**
     * Возвращает список всех участников группы
     * @param userId айди пользователя
     * @param token уникальный ключ пользователя
     * @param groupId айди группы
     */
    @Modifying
    @RequestMapping(value = "/get_participants", method = RequestMethod.GET)
    public PojoUsersList getGroupParticipants(@RequestParam("id") Long userId, @RequestParam("token") String token,
                                              @RequestParam("group_id") Long groupId){
        if(usersService.verifyToken(userId, token)) {
            return groupUserPairsService.getUsersByGroupId(groupId);
        }
        return null;
    }

    /**
     * Возвращает уменьшненные версии групп, в которых состоит пользователь
     * @param id айди пользователя
     * @param token уникальный ключ пользователя
     */
    @Modifying
    @RequestMapping(value = "/get_own", method = RequestMethod.GET)
    public PojoGroupIdNames getOwn(@RequestParam("id") Long id, @RequestParam("token") String token){
        if(usersService.verifyToken(id, token)) {
            return groupsService.getOwn(id);
        }
        return null;
    }

    /**
     * Возвращает лист групп, в которых состоит пользователь, начинающихся с данной подстроки
     * @param id айди пользователя
     * @param token уникальный ключ пользователя
     * @param keyWord подстрока названия
     * @param offset номер, с которого начинается лист
     * @param quantity количество результатов выдачи
     */
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

    /**
     * Возвращает лист групп, начинающихся с данной подстроки
     * @param id айди пользователя
     * @param token уникальный ключ пользователя
     * @param keyWord подстрока названия
     * @param offset номер, с которого начинается лист
     * @param quantity количество результатов выдачи
     */
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
