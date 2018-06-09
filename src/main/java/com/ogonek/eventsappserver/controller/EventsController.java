package com.ogonek.eventsappserver.controller;

import com.ogonek.eventsappserver.Pojo.*;
import com.ogonek.eventsappserver.service.EventsService;
import com.ogonek.eventsappserver.service.IdPairsService;
import com.ogonek.eventsappserver.service.OwnerIdPairsService;
import com.ogonek.eventsappserver.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.web.bind.annotation.*;

/**
 * Контроллер для запросов, касающихся организации мероприятий. Все запросы по пути /events
 */
@RestController
@RequestMapping("/events")
public class EventsController {

    /**
     * Сервис с мероприятиями
     */
    @Autowired
    EventsService eventsService;

    /**
     * Серивис с парами мероприятий и их посетителей
     */
    @Autowired
    IdPairsService idPairsService;

    /**
     * Сервис с пользователями
     */
    @Autowired
    UsersService usersService;

    /**
     * Сервис с парами мероприятий и их организаторов
     */
    @Autowired
    OwnerIdPairsService ownerIdPairsService;

    /**
     * Возвращает полную информацию о мероприятии
     * @param userId айди пользователя
     * @param token уникальный ключ пользователя
     * @param eventId айди мероприятия
     */
    @Modifying
    @RequestMapping(value = "/get", method = RequestMethod.GET)
    public PojoEvent getEvent(@RequestParam("id") Long userId, @RequestParam("token") String token,
                              @RequestParam("event_id") Long eventId){
        if(usersService.verifyToken(userId, token)) {
            return eventsService.getPojoEvent(eventId, userId);
        }
        return null;
    }

    /**
     * Возвращает мероприятние, если оно публично, и null иначе
     * @param eventId айди мероприятия
     */
    @Modifying
    @RequestMapping(value = "/get_public", method = RequestMethod.GET)
    public PojoEvent getEventPublic(@RequestParam("event_id") Long eventId){
        return eventsService.getPojoEventPublic(eventId);

    }

    /**
     * Возвращает изображение в виде массива байт
     * @param directory путь к изображению
     */
    @Modifying
    @RequestMapping(value = "/get_picture", produces = {"image/jpeg"}, method = RequestMethod.GET)
    public @ResponseBody byte[] getPicture(@RequestParam("path") String directory){
        return eventsService.getPicture(directory);
    }

    /**
     * Возвращает уменьшненные версии событий, которые пользователь собирается посетить, или мероприятия, которыми управляет пользователь
     * @param type 0 - вернуть собственные мероприятия, 1 - вернуть мероприятия, которые пользователь собирается посетить
     * @param userId айди пользователя
     * @param token уникальный ключ пользователя
     */
    @Modifying
    @RequestMapping(value = "/get_profile_events", method = RequestMethod.GET)
    public PojoSmallEvents getOwnEvents(@RequestParam("type") int type, @RequestParam("id") Long userId, @RequestParam("token") String token){
        if(usersService.verifyToken(userId, token)) {
            switch (type){
                case 0:
                    return eventsService.getOwnEvents(userId);
                case 1:
                    return eventsService.getPresentEvents(userId);
            }
        }
        return null;
    }

    /**
     * Возвращает мероприятия, которые пользователь собирается посетить, или мероприятия, которыми управляет пользователь
     * @param type 0 - вернуть собственные мероприятия, 1 - вернуть мероприятия, которые пользователь собирается посетить
     * @param userId айди пользователя
     * @param token уникальный ключ пользователя
     */
    @Modifying
    @RequestMapping(value = "/get_big_profile_events", method = RequestMethod.GET)
    public PojoEvents getBigOwnEvents(@RequestParam("type") int type, @RequestParam("id") Long userId, @RequestParam("token") String token){
        if(usersService.verifyToken(userId, token)) {
            switch (type){
                case 0:
                    return eventsService.getBigOwnEvents(userId);
                case 1:
                    return eventsService.getBigPresentEvents(userId);
            }
        }
        return null;
    }

    /**
     * Возвращает мероприятия, находящийеся в данном диапозоне координат
     * @param userId айди пользователя
     * @param token уникальный ключ пользователя
     * @param minLatitude минимальная широта
     * @param maxLatitude максимальная широта
     * @param minLongitude минимальная долгота
     * @param maxLongitude максимальная долгота
     */
    @Modifying
    @RequestMapping(value = "/get_between", method = RequestMethod.GET)
    public PojoEventsForMap getEventsBetween(@RequestParam("id") Long userId, @RequestParam("token") String token,
                                             @RequestParam("min_lat") double minLatitude, @RequestParam("max_lat") double maxLatitude,
                                             @RequestParam("min_lon") double minLongitude, @RequestParam("max_lon") double maxLongitude){
        if(usersService.verifyToken(userId, token)) {
            return eventsService.getEventsBetween(userId, minLatitude, maxLatitude, minLongitude, maxLongitude);
        }
        return null;
    }

    /**
     * Возвращает публичные мероприятия, находящийеся в данном диапозоне координат
     * @param minLatitude минимальная широта
     * @param maxLatitude максимальная широта
     * @param minLongitude минимальная долгота
     * @param maxLongitude максимальная долгота
     */
    @Modifying
    @RequestMapping(value = "/get_between_public", method = RequestMethod.GET)
    public PojoEventsForMap getEventsBetweenPublic(@RequestParam("min_lat") double minLatitude, @RequestParam("max_lat") double maxLatitude,
                                                   @RequestParam("min_lon") double minLongitude, @RequestParam("max_lon") double maxLongitude){
        return eventsService.getEventsBetween(null, minLatitude, maxLatitude, minLongitude, maxLongitude);
    }

    /**
     * Позволяет создать новое мероприятние. Возвращает айди нового мероприятия, или -1, если произошла ошибка
     * @param pojoNewEvent параметры нового мероприятия
     */
    @Modifying
    @RequestMapping(value = "/new", method = RequestMethod.POST)
    public long newEvent(@RequestBody PojoNewEvent pojoNewEvent){
        if(usersService.verifyToken(pojoNewEvent.getOwnerId(), pojoNewEvent.getToken())) {
            long eventId = eventsService.addEvent(pojoNewEvent.getName(), pojoNewEvent.getOwnerId(), pojoNewEvent.getLatitude(),
                    pojoNewEvent.getLongitude(), pojoNewEvent.getDate(), pojoNewEvent.getType(),
                    pojoNewEvent.getDate() + pojoNewEvent.getDuration(),
                    pojoNewEvent.getPrivacy(), pojoNewEvent.getDescription(), pojoNewEvent.getPicture(), pojoNewEvent.getGroupId());
            idPairsService.addPair(pojoNewEvent.getOwnerId(), eventId);
            ownerIdPairsService.addOwnerIdPair(pojoNewEvent.getOwnerId(), eventId);
            return eventId;
        }
        return -1;
    }

    /**
     * Позволяет добавить участника мероприятия. Возвращает boolean - успешно ли прошло добавление
     * @param id айди пользователя
     * @param token уникальный ключ пользователя
     * @param eventId айди мероприятия
     */
    @Modifying
    @RequestMapping(value = "/new_participant", method = RequestMethod.GET)
    public boolean newParticipiant(@RequestParam("id") Long id, @RequestParam("token") String token,
                                   @RequestParam("event_id") Long eventId){
        if(usersService.verifyToken(id, token)) {
            return idPairsService.addPair(id, eventId);
        }
        return false;
    }

    /**
     * Позволяет удалить участника из мероприятия. Возвращает boolean - успешно ли прошло добавление
     * @param id айди пользователя
     * @param token уникальный ключ пользователя
     * @param eventId айди мероприятия
     * @param participantId айди участника
     */
    @Modifying
    @RequestMapping(value = "/delete_participant", method = RequestMethod.GET)
    public boolean deleteParticipiant(@RequestParam("id") Long id, @RequestParam("token") String token,
                                      @RequestParam("event_id") Long eventId,
                                      @RequestParam("participant_id") Long participantId){
        if(usersService.verifyToken(id, token)) {
            return idPairsService.deleteIdPair(participantId, eventId, id);
        }
        return false;
    }

    /**
     * Позволяет удалить мероприятние. Возвращает boolean - успешно ли прошло удаление
     * @param id айди владельца эвента
     * @param token уникальный ключ владельца эвента
     * @param eventId айди мероприятия
     */
    @Modifying
    @RequestMapping(value = "/delete", method = RequestMethod.GET)
    public boolean deleteEvent(@RequestParam("id") Long id, @RequestParam("token") String token,
                               @RequestParam("event_id") Long eventId){
        if(usersService.verifyToken(id, token)) {
            return eventsService.deleteOwnEvent(id, eventId);
        }
        return false;
    }

    /**
     * Возвращает список всех участников мероприятия
     * @param userId айди пользователя
     * @param token уникальный ключ пользователя
     * @param eventId айди мероприятия
     */
    @Modifying
    @RequestMapping(value = "/get_participants", method = RequestMethod.GET)
    public PojoUsersList getEventParticipants(@RequestParam("id") Long userId, @RequestParam("token") String token,
                                              @RequestParam("event_id") Long eventId){
        if(usersService.verifyToken(userId, token)) {
            return idPairsService.getUsersByEventId(eventId);
        }
        return null;
    }

    /**
     * Возвращает лист мероприятий, начинающихся с данной подстроки
     * @param userId айди пользователя
     * @param token уникальный ключ пользователя
     * @param part подстрока названия
     */
    @Modifying
    @RequestMapping(value = "/search", method = RequestMethod.GET)
    public PojoSmallEvents searchByName(@RequestParam("id") Long userId, @RequestParam("token") String token,
                                        @RequestParam("part") String part){
        if(usersService.verifyToken(userId, token)) {
            return eventsService.findByName(part, userId, 0, 100);
        }
        return null;
    }

    /**
     * Позволяет сменить информации о мероприятии. Возвращает boolean - успешно ли прошло изменение
     * @param pojoChangeEvent новая информация о мероприятии
     */
    @Modifying
    @RequestMapping(value = "/change", method = RequestMethod.POST)
    public Boolean changeEvent(@RequestBody PojoChangeEvent pojoChangeEvent){
        if(usersService.verifyToken(pojoChangeEvent.getOwnerId(), pojoChangeEvent.getToken())) {
            return eventsService.changeEvent(pojoChangeEvent);
        }
        return false;
    }
}
