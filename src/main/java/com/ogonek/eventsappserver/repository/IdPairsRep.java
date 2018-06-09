package com.ogonek.eventsappserver.repository;

import com.ogonek.eventsappserver.entity.IdPair;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

/**
 * Таблица пар пользователь-группа
 */
@Repository
public interface IdPairsRep extends JpaRepository<IdPair, Long> {
    /**
     * Ищет пару по айди
     */
    IdPair findById (long id);

    /**
     * Ищет пару по айди пользователя и мероприятия
     */
    IdPair findByUserIdAndEventId (long userId, long eventId);

    /**
     * Ищет все пары по айди пользователя
     */
    List<IdPair> findAllByUserId(long userId);

    /**
     * Ищет все пары по айди мероприятия
     */
    List<IdPair> findAllByEventId(long eventId);

    /**
     * Удаляет пару по айди
     */
    @Transactional
    void deleteById(long id);

    /**
     * Удаляет все пары по айди мероприятия
     */
    @Transactional
    void deleteAllByEventId(long eventId);
}
