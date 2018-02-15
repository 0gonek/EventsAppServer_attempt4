package com.ogonek.eventsappserver.repository;

import com.ogonek.eventsappserver.entity.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface EventsRep extends JpaRepository<Event, Long> {
    Event findById (long id);
    List<Event> findAllByOwnerId (long id);
    List<Event> findByLatitudeGreaterThanAndLatitudeLessThanAndLongitudeGreaterThanAndLongitudeLessThan(double minLatitude,
                                                                                                        double maxLatitude,
                                                                                                        double minLongitude,
                                                                                                        double maxLongitude);
    @Transactional
    void deleteById(long id);
    @Transactional
    @Modifying
    @Query("update Event e set e.name = ?2 where e.id = ?1")
    int changeEventName(Long id, String name);
    @Transactional
    @Modifying
    @Query("update Event e set e.latitude = ?2 where e.id = ?1")
    int changeEventLatitude(Long id, Double latitude);
    @Transactional
    @Modifying
    @Query("update Event e set e.longitude = ?2 where e.id = ?1")
    int changeEventLongitude(Long id, Double longitude);
    @Transactional
    @Modifying
    @Query("update Event e set e.date = ?2 where e.id = ?1")
    int changeEventDate(Long id, long date);
    @Transactional
    @Modifying
    @Query("update Event e set e.duration = ?2 where e.id = ?1")
    int changeEventDuration(Long id, long duration);
    @Transactional
    @Modifying
    @Query("update Event e set e.description = ?2 where e.id = ?1")
    int changeEventDescription(Long id, String description);
    @Transactional
    @Modifying
    @Query("update Event e set e.pathToThePicture = ?2 where e.id = ?1")
    int changeEventPathToThePicture(Long id, String pathToThePicture);
    @Transactional
    @Modifying
    @Query("update Event e set e.type = ?2 where e.id = ?1")
    int changeEventType(Long id, String type);
    @Transactional
    @Modifying
    @Query("update Event e set e.participants = ?2 where e.id = ?1")
    int changeEventParticipants(Long id, long participants);
}
