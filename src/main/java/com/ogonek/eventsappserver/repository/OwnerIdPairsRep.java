package com.ogonek.eventsappserver.repository;

import com.ogonek.eventsappserver.entity.OwnerIdPair;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface OwnerIdPairsRep extends JpaRepository<OwnerIdPair, Long> {
    OwnerIdPair findById(Long id);
    OwnerIdPair findByOwnerIdAndEventId (long ownerId, long eventId);
    List<OwnerIdPair> findAllByOwnerId(long ownerId);
    OwnerIdPair findByEventId(long eventId);
    @Transactional
    void deleteById(long id);
}
