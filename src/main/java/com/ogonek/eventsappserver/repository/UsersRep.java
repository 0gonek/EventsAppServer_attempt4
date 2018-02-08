package com.ogonek.eventsappserver.repository;

import com.ogonek.eventsappserver.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
public interface UsersRep extends JpaRepository<User, Long> {
    User findById(long id);
    User findByIntegrationIdAndIntegrationType(String integrationId, String integrationType);
    @Transactional
    void deleteById(long id);
    @Transactional
    @Modifying
    @Query("update User u set u.name = ?2 where u.id = ?1")
    int changeUserName(Long id, String name);
    @Transactional
    @Modifying
    @Query("update User u set u.code = ?2 where u.id = ?1")
    int changeUserCode(Long id, String code);
    @Transactional
    @Modifying
    @Query("update User u set u.token = ?2 where u.id = ?1")
    int changeUserToken(Long id, String token);
    @Transactional
    @Modifying
    @Query("update User u set u.integrationType = ?2 where u.id = ?1")
    int changeUserIntegrationType(Long id, String integrationType);
    @Transactional
    @Modifying
    @Query("update User u set u.integrationId = ?2 where u.id = ?1")
    int changeUserIntegrationId(Long id, String integrationId);
}
