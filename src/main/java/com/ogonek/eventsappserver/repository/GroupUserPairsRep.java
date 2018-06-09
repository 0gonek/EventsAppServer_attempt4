package com.ogonek.eventsappserver.repository;

import com.ogonek.eventsappserver.entity.GroupUserPair;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;
/**
 * Таблица пар пользователь-группа
 */
@Repository
public interface GroupUserPairsRep extends JpaRepository<GroupUserPair, Long> {
    /**
     * Ищет пару по айди
     */
    GroupUserPair findById (long id);

    /**
     * Ищет пару по айди пользователя и айди группы
     */
    GroupUserPair findByUserIdAndGroupId (long userId, long eventId);

    /**
     * Ищет все пары по айди пользователя
     */
    List<GroupUserPair> findAllByUserId(long userId);

    /**
     * Ищет все пары по айди группы
     */
    List<GroupUserPair> findAllByGroupId(long eventId);

    /**
     * Удаляет пару по айди
     */
    @Transactional
    void deleteById(long id);

    /**
     * Удаляет все пары по айди группы
     */
    @Transactional
    void deleteAllByGroupId(long groupId);
}
