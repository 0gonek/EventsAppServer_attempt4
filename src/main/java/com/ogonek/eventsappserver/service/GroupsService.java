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

/**
 * Сервис для групп и всего, что с ними связано
 */
@Service
public class GroupsService {

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
     * Сервис с мероприятиями
     */
    @Autowired
    EventsService eventsService;

    /**
     * Добавляет новую группу в базу. Возвращает её айди
     * @param name название группы
     * @param ownerId айди создателя
     * @param privacy приватность
     * @param description описание
     * @param picture изображение
     * @param type тип
     */
    public Long addGroup(String name, Long ownerId, Boolean privacy, String description, byte[] picture,
                         Integer type){
        String pathToThePicture = "Error";
        Group group = new Group(name, ownerId, privacy, description, pathToThePicture, type);
        groupsRep.save(group);
        String path = eventsService.savePicture(group.getId(), picture);
        groupsRep.changeGroupPathToThePicture(group.getId(), path);
        return group.getId();
    }

    /**
     * Изменяет группу в базе. ВОзвращает boolean - успешно ли прошло изменение
     * @param pojoChangeGroup группа, которая заменяет группу в базе
     */
    public boolean ChangeGroup(PojoChangeGroup pojoChangeGroup){
        Group oldGroup = groupsRep.findById(pojoChangeGroup.getId());
        if(oldGroup == null || pojoChangeGroup.getOwnerId() == null) return false;
        if(pojoChangeGroup.getOwnerId().equals(oldGroup.getOwnerId())){
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

    /**
     * Удаляет собственную группу
     * @param userId айди владельца
     * @param groupId айди группы
     */
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

    /**
     * Возвращает PojoGroup, сгенерированную из группы
     * @param id айди группы
     * @param userId айди пользователя
     */
    public PojoGroup getPojoGroup(long id, long userId){
        Group group = groupsRep.findById(id);
        PojoGroup pojoGroup = new PojoGroup(group);
        pojoGroup.setAccepted(groupUserPairsRep.findByUserIdAndGroupId(userId,id) != null);
        return pojoGroup;
    }

    // CHECK INDEXES

    /**
     * Возвращает список из нескольких групп, начиная с данного номера, содержащих данную часть названия
     * @param part часть названия
     * @param offset номер, начиная с которого формируется лист
     * @param quantity число групп в листе
     */
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

    /**
     * Возвращает список всех групп, в которых состоит пользователь, в формате PojoGroupIdNames
     * @param userId айди пользователя
     */
    public PojoGroupIdNames getOwn(Long userId){
        List<GroupUserPair> groupIds = groupUserPairsRep.findAllByUserId(userId);
        List<Group> groups = new ArrayList<>();
        for (GroupUserPair pair:groupIds
                ) {
            groups.add(groupsRep.findById(pair.getGroupId()));
        }
        if(groups.size() == 0) return new PojoGroupIdNames(new PojoGroupIdName[0]);
        return toGroupIdNames(groups);
    }

    // CHECK INDEXES

    /**
     * Возвращает список из нескольких групп, в которых состоит пользователь, начиная с данного номера,
     * содержащих данную часть названия
     * @param part часть названия
     * @param offset номер, начиная с которого формируется лист
     * @param quantity число групп в листе
     */
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

    /**
     * Возвращает объект toSmallGroups, сгенерированный из листа групп
     * @param groups лист групп
     */
    private PojoSmallGroups toSmallGroups(List<Group> groups){
        List<PojoSmallGroup> listOsSmallGroups = new ArrayList<>();
        for (Group group : groups) {
            listOsSmallGroups.add(new PojoSmallGroup(group));
        }
        PojoSmallGroup[] pojoSmallGroups = new PojoSmallGroup[groups.size()];
        pojoSmallGroups = listOsSmallGroups.toArray(pojoSmallGroups);
        return new PojoSmallGroups(pojoSmallGroups);
    }

    /**
     * Возвращает объект toGroupIdNames, сгенерированный из листа групп
     * @param groups лист групп
     */
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
