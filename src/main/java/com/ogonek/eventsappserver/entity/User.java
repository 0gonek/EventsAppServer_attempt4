package com.ogonek.eventsappserver.entity;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Сущность пользователя
 */
@Entity
@Table(name = "users")
public class User implements Serializable{

    /**
     * Автогенерируемое поле id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    /**
     * Имя пользователя
     */
    @Column(name = "name", nullable = false)
    private String name;
    /**
     * Пароль (устарел, ныне не используется)
     */
    @Column(name = "code", nullable = true)
    private String code;
    /**
     * Уникальный идентификатор пользователя
     */
    @Column(name = "token", nullable = false)
    private String token;
    /**
     * Тип интеграции пользователя
     */
    @Column(name = "integrationType", nullable = false)
    private String integrationType;
    /**
     * Integration ID пользователя в соответствующей соцсети
     */
    @Column(name = "integrationId", nullable = false)
    private String integrationId;

    /**
     * Конструктор (устаревший)
     * @param name имя пользователя
     * @param code пароль пользователя
     * @param token уникальный идентификатор пользователя
     * @param integrationType тип интеграции пользователя
     * @param integrationId integration ID пользователя в соответствующей соцсети
     */
    public User(String name, String code, String token, String integrationType, String integrationId) {
        this.name = name;
        this.code = code;
        this.token = token;
        this.integrationType = integrationType;
        this.integrationId = integrationId;
    }

    /**
     * Конструктор
     * @param name имя пользователя
     * @param token уникальный идентификатор пользователя
     * @param integrationType тип интеграции пользователя
     * @param integrationId integration ID пользователя в соответствующей соцсети
     */
    public User(String name, String token, String integrationType, String integrationId) {
        this.name = name;
        this.token = token;
        this.integrationType = integrationType;
        this.integrationId = integrationId;
    }

    public boolean verifyToken(String token){
        return token.equals(this.token);
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getCode() {
        return code;
    }

    public String getToken() {
        return token;
    }

    public String getIntegrationType() {
        return integrationType;
    }

    public String getIntegrationId() {
        return integrationId;
    }
}
