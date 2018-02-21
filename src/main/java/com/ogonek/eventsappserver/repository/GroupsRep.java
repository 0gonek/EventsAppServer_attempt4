package com.ogonek.eventsappserver.repository;

import com.ogonek.eventsappserver.entity.Group;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface GroupsRep extends JpaRepository<Group, Long>{
    Group findById (long id);
    List<Group> findAllByOwnerId (long id);
    List<Group> findAllByNameContains(String part);

    @Transactional
    void deleteById(long id);

//    @Transactional
//    @Modifying
//    @Query("update Group g set g.name = ?2 where e.id = ?1")
//    int changeGroupName(Long id, String name);
//    @Transactional
//    @Modifying
//    @Query("update Group g set g.description = ?2 where e.id = ?1")
//    int changeGroupDescription(Long id, String description);
//    @Transactional
//    @Modifying
//    @Query("update Group g set g.privacy = ?2 where e.id = ?1")
//    int changeGroupPrivacy(Long id, boolean privacy);
//    @Transactional
//    @Modifying
//    @Query("update Group g set g.pathToThePicture = ?2 where e.id = ?1")
//    int changeGroupPathToThePicture(Long id, String pathToThePicture);

    @Transactional
    @Modifying
    @Query("update Group g set g.participants = ?2 where g.id = ?1")
    int changeGroupParticipants(Long id, Long participants);

}
