package com.ogonek.eventsappserver.repository;

import com.ogonek.eventsappserver.entity.IdPair;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface IdPairsRep extends JpaRepository<IdPair, Long> {
    IdPair findById (long id);
    IdPair findByUserIdAndEventId (long userId, long eventId);
    List<IdPair> findAllByUserId(long userId);
    List<IdPair> findAllByEventId(long eventId);
    @Transactional
    void deleteById(long id);
    @Transactional
    void deleteAllByEventId(long eventId);
}
