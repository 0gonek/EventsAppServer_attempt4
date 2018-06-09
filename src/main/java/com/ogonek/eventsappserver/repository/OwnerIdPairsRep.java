package com.ogonek.eventsappserver.repository;

import com.ogonek.eventsappserver.entity.OwnerIdPair;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

/**
 * Таблица пар владелец-мероприятие
 */
@Repository
public interface OwnerIdPairsRep extends JpaRepository<OwnerIdPair, Long> {
    /**
     * Ищет пару по айди
     */
    OwnerIdPair findById(Long id);

    OwnerIdPair findByOwnerIdAndEventId (long ownerId, long eventId);

    /**
     * Ищет все пары по айди владельца
     */
    List<OwnerIdPair> findAllByOwnerId(long ownerId);

    /**
     * Ищет пару по айди мероприятия
     */
    OwnerIdPair findByEventId(long eventId);

    /**
     * Удаляет пару по айди
     */
    @Transactional
    void deleteById(long id);
}
