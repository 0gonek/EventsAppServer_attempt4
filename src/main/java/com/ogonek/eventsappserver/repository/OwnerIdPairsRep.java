package com.ogonek.eventsappserver.repository;

import com.ogonek.eventsappserver.entity.OwnerIdPair;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
public interface OwnerIdPairsRep extends JpaRepository<OwnerIdPair, Long> {
    OwnerIdPair findById(Long id);
    @Transactional
    void deleteById(long id);
}
