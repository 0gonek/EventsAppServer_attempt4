package com.ogonek.eventsappserver.repository;

import com.ogonek.eventsappserver.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

/**
 * Таблица пользователей
 */
@Repository
public interface UsersRep extends JpaRepository<User, Long> {
    /**
     * Поиск по айди
     */
    User findById(long id);

    /**
     * Поиск по Integrayion id и Integration type
     */
    User findByIntegrationIdAndIntegrationType(String integrationId, String integrationType);

    /**
     * Удаление по айди
     */
    @Transactional
    void deleteById(long id);

    /**
     * Изменение имени по айди
     */
    @Transactional
    @Modifying
    @Query("update User u set u.name = ?2 where u.id = ?1")
    int changeUserName(Long id, String name);

    /**
     * Изменение пароля (устаревшее) по айди
     */
    @Transactional
    @Modifying
    @Query("update User u set u.code = ?2 where u.id = ?1")
    int changeUserCode(Long id, String code);

    /**
     * Изменение уникального идентификатора пользователя по айди
     */
    @Transactional
    @Modifying
    @Query("update User u set u.token = ?2 where u.id = ?1")
    int changeUserToken(Long id, String token);

    /**
     * Изменение типа интеграции пользователя по айди
     */
    @Transactional
    @Modifying
    @Query("update User u set u.integrationType = ?2 where u.id = ?1")
    int changeUserIntegrationType(Long id, String integrationType);

    /**
     * Изменение Integration id пользователя по айди
     */
    @Transactional
    @Modifying
    @Query("update User u set u.integrationId = ?2 where u.id = ?1")
    int changeUserIntegrationId(Long id, String integrationId);
}
