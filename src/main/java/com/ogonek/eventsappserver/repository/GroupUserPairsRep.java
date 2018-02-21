package com.ogonek.eventsappserver.repository;

import com.ogonek.eventsappserver.entity.GroupUserPair;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface GroupUserPairsRep extends JpaRepository<GroupUserPair, Long> {
    GroupUserPair findById (long id);
    GroupUserPair findByUserIdAndGroupId (long userId, long eventId);
    List<GroupUserPair> findAllByUserId(long userId);
    List<GroupUserPair> findAllByGroupId(long eventId);

    @Transactional
    void deleteById(long id);
    @Transactional
    void deleteAllByGroupId(long groupId);
}
