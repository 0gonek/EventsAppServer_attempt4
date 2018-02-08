package com.ogonek.eventsappserver.repository;

import com.ogonek.eventsappserver.entity.IdPair;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
public interface IdPairsRep extends JpaRepository<IdPair, Long> {
    IdPair findById (long id);
    @Transactional
    void deleteById(long id);
}
