package com.ogonek.eventsappserver.repository;

import com.ogonek.eventsappserver.entity.Group;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.CriteriaBuilder;
import javax.transaction.Transactional;
import java.util.List;

/**
 * Таблица групп
 */
@Repository
public interface GroupsRep extends JpaRepository<Group, Long>{
    /**
     * Возвращает группу по айди
     */
    Group findById (long id);

    List<Group> findAllByOwnerId (long id);

    /**
     * Ищет группы по части названия
     */
    List<Group> findAllByNameContains(String part);

    List<Group> findAllByNameContainsAndOwnerId(String part, long id);

    /**
     * Удаляет группу по айди
     */
    @Transactional
    void deleteById(long id);

    /**
     * Изменяет название группы
     */
    @Transactional
    @Modifying
    @Query("update Group g set g.name = ?2 where g.id = ?1")
    int changeGroupName(Long id, String name);

    /**
     * Изменяет описание группы
     */
    @Transactional
    @Modifying
    @Query("update Group g set g.description = ?2 where g.id = ?1")
    int changeGroupDescription(Long id, String description);

    /**
     * Изменяет приватность группы
     */
    @Transactional
    @Modifying
    @Query("update Group g set g.privacy = ?2 where g.id = ?1")
    int changeGroupPrivacy(Long id, boolean privacy);

    /**
     * Изменяет путь к картинке группы
     */
    @Transactional
    @Modifying
    @Query("update Group g set g.pathToThePicture = ?2 where g.id = ?1")
    int changeGroupPathToThePicture(Long id, String pathToThePicture);

    /**
     * Изменяет тип группы
     */
    @Transactional
    @Modifying
    @Query("update Group g set g.type = ?2 where g.id = ?1")
    int changeGroupType(Long id, Integer type);

    /**
     *Изменяет число учатсников группы
     */
    @Transactional
    @Modifying
    @Query("update Group g set g.participants = ?2 where g.id = ?1")
    int changeGroupParticipants(Long id, Long participants);

}
