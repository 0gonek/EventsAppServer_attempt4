package com.ogonek.eventsappserver.repository;

import com.ogonek.eventsappserver.entity.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

/**
 * Таблица мероприятий
 */
@Repository
public interface EventsRep extends JpaRepository<Event, Long> {
    /**
     * Возвращает мероприятие по айди
     */
    Event findById (long id);
    List<Event> findAllByOwnerId (long id);

    /**
     * Возвращает список мероприятий между заданными координатами ли уже завершившихся
     */
    List<Event> findByLatitudeGreaterThanAndLatitudeLessThanAndLongitudeGreaterThanAndLongitudeLessThanOrEndTimeLessThan(double minLatitude,
                                                                                                                         double maxLatitude,
                                                                                                                         double minLongitude,
                                                                                                                         double maxLongitude,
                                                                                                                         long currentTime);

    /**
     * Возвращает все мероприятия по части названия
     * @param part часть названия
     */
    List<Event> findAllByNameContains(String part);

    /**
     * Удаляет мероприятие по айди
     */
    @Transactional
    void deleteById(long id);

    @Transactional
    void deleteAllByEndTimeGreaterThan(long endTime);

    /**
     * Изменияет название мероприятия
     * @param name новое название
     */
    @Transactional
    @Modifying
    @Query("update Event e set e.name = ?2 where e.id = ?1")
    int changeEventName(Long id, String name);

    /**
     * Изменияет широту мероприятия
     */
    @Transactional
    @Modifying
    @Query("update Event e set e.latitude = ?2 where e.id = ?1")
    int changeEventLatitude(Long id, Double latitude);

    /**
     * Изменияет долготу мероприятия
     */
    @Transactional
    @Modifying
    @Query("update Event e set e.longitude = ?2 where e.id = ?1")
    int changeEventLongitude(Long id, Double longitude);

    /**
     * Изменяет дату мероприятия
     */
    @Transactional
    @Modifying
    @Query("update Event e set e.date = ?2 where e.id = ?1")
    int changeEventDate(Long id, long date);

    /**
     * Изменяет время окончания мероприятия
     */
    @Transactional
    @Modifying
    @Query("update Event e set e.endTime = ?2 where e.id = ?1")
    int changeEventEndTime(Long id, long endTime);

    /**
     * Изменяет приватность мероприятия
     */
    @Transactional
    @Modifying
    @Query("update Event e set e.privacy = ?2 where e.id = ?1")
    int changeEventPrivacy(Long id, boolean privacy);

    /**
     * Изменяет описание мероприятия
     */
    @Transactional
    @Modifying
    @Query("update Event e set e.description = ?2 where e.id = ?1")
    int changeEventDescription(Long id, String description);

    /**
     * Изменяет путь к картинке мероприятия
     */
    @Transactional
    @Modifying
    @Query("update Event e set e.pathToThePicture = ?2 where e.id = ?1")
    int changeEventPathToThePicture(Long id, String pathToThePicture);

    /**
     * Изменяет тип мероприятия
     */
    @Transactional
    @Modifying
    @Query("update Event e set e.type = ?2 where e.id = ?1")
    int changeEventType(Long id, Integer type);

    /**
     * Изменяет число участников мероприятия
     */
    @Transactional
    @Modifying
    @Query("update Event e set e.participants = ?2 where e.id = ?1")
    int changeEventParticipants(Long id, Long participants);
}
