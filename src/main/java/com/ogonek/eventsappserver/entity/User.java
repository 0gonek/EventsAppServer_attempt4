package com.ogonek.eventsappserver.entity;

import org.springframework.data.jpa.repository.Modifying;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "users")
public class User implements Serializable{

    private static final int EVENTSCOUNT = 2;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;
    @Column(name = "code", nullable = true)
    private String code;
    @Column(name = "token", nullable = false)
    private String token;
    @Column(name = "integrationType", nullable = false)
    private String integrationType;
    @Column(name = "integrationId", nullable = false)
    private String integrationId;

    public User(){}

    public User(String name, String code, String token, String integrationType, String integrationId) {
        this.name = name;
        this.code = code;
        this.token = token;
        this.integrationType = integrationType;
        this.integrationId = integrationId;
    }

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
