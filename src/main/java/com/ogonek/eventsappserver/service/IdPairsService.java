package com.ogonek.eventsappserver.service;

import com.ogonek.eventsappserver.Pojo.PojoNameAndAvatar;
import com.ogonek.eventsappserver.Pojo.PojoUsersList;
import com.ogonek.eventsappserver.entity.IdPair;
import com.ogonek.eventsappserver.entity.User;
import com.ogonek.eventsappserver.repository.EventsRep;
import com.ogonek.eventsappserver.repository.IdPairsRep;
import com.ogonek.eventsappserver.repository.UsersRep;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Сервис для пар пользователей и мероприятий, на которые они собираются
 */
@Service
public class IdPairsService {

    /**
     * База пар мероприятие-пользователь
     */
    @Autowired
    IdPairsRep idPairsRep;
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
     * База с мероприятиями
     */
    @Autowired
    EventsRep eventsRep;

    /**
     * Добавляет пару в базу. Возвращает boolean - успешно ли прошло добавление
     * @param userId айди пользователя
     * @param eventId айди мероприятия
     */
    public boolean addPair(long userId, long eventId){
        if(idPairsRep.findByUserIdAndEventId(userId,eventId) == null){
            IdPair idPair = new IdPair(userId, eventId);
            if(eventsRep.findById(eventId) == null) return false;
            idPairsRep.save(idPair);
            Long participants = eventsRep.findById(eventId).getParticipants();
            eventsRep.changeEventParticipants(eventId, participants+1);
            return true;
        }
        return false;
    }

    private class UserIdNameIntegrationId{
        public long id;
        public String name;

        public UserIdNameIntegrationId(long id, String name) {
            this.id = id;
            this.name = name;
        }
    }

    /**
     * Возвращает листо пользователей, собирающихся на данной мероприятие
     * @param eventId айди мероприятия
     */
    public PojoUsersList getUsersByEventId(long eventId){
        List<IdPair> idPairs = idPairsRep.findAllByEventId(eventId);
        if(idPairs.size() == 0) return null;
        List<Long> userIds = new ArrayList<>();
        for (IdPair idPair: idPairs) {
            userIds.add(idPair.getUserId());
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

    public Iterable<IdPair> getAll(){
        Iterable<IdPair> allPairs = idPairsRep.findAll();
        return allPairs;
    }

    public IdPair getById(long iD){
        IdPair pair = idPairsRep.findById(iD);
        return pair;
    }

    public List<IdPair> getAllByOwnerId(long userId){
        List<IdPair> pairs = idPairsRep.findAllByUserId(userId);
        return pairs;
    }

    public List<IdPair> getByEventId(long eventId){
        List<IdPair> pairs = idPairsRep.findAllByEventId(eventId);
        return pairs;
    }

    /**
     * Удаляет пару из базы. Возвращает boolean - успешно ли прошло удаление
     * @param userId айди пользователя
     * @param eventId айди мероприятия
     * @param ownerId айди самого пользователя или создателя мероприятия
     */
    public boolean deleteIdPair(long userId, long eventId, long ownerId){
        if(idPairsRep.findByUserIdAndEventId(userId,eventId) != null){
            if(eventsRep.findById(eventId) == null) return false;
            if(ownerId == userId || ownerId == eventsRep.findById(eventId).getOwnerId()){
                idPairsRep.deleteById(idPairsRep.findByUserIdAndEventId(userId,eventId).getId());
                Long participants = eventsRep.findById(eventId).getParticipants();
                eventsRep.changeEventParticipants(eventId, participants - 1);
                return true;
            }
        }
        return false;
    }

    /**
     * Возвращает лист имёт и аватаров учатсников мероприятия
     * @param eventId айди мероприятия
     */
    public List<PojoNameAndAvatar> getNamesAndAvatars(long eventId){
        List<IdPair> idPairs = idPairsRep.findAllByEventId(eventId);
        List<PojoNameAndAvatar> pojoNameAndAvatars = new LinkedList<PojoNameAndAvatar>();
        for (IdPair idPair : idPairs) {
            long userId = idPair.getUserId();
            User user = usersRep.findById(userId);
            pojoNameAndAvatars.add(new PojoNameAndAvatar(userId, user.getName(), usersService.getBigAvatarVK(userId)));
        }
        return pojoNameAndAvatars;
    }

}
