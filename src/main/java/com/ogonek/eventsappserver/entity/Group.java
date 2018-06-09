package com.ogonek.eventsappserver.entity;


import javax.persistence.*;
import java.io.Serializable;

/**
 * Сущность группы
 */
@Entity
@Table(name = "groups")
public class Group implements Serializable{
    /**
     * Автогенерируемое поле id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    /**
     * Название группы
     */
    @Column(name = "name", nullable = false)
    private String name;
    /**
     * Айди владельца группы
     */
    @Column(name = "ownerId", nullable = false)
    private Long ownerId;
    /**
     * Приватность группы
     */
    @Column(name = "privacy", nullable = false)
    private Boolean privacy;
    /**
     * Описание группы
     */
    @Column(name = "description", nullable = true)
    private String description;
    /**
     * Путь у картинке группы
     */
    @Column(name = "pathToThePicture", nullable = true)
    private String pathToThePicture;
    /**
     * Тип группы
     */
    @Column(name = "Type", nullable = true)
    private Integer type;
    /**
     * Число участников группы
     */
    @Column(name = "Participants", nullable = true)
    private Long participants;

    public Group() {
    }

    /**
     * Конструктор
     * @param name название группы
     * @param ownerId айди владельца группы
     * @param privacy приватность группы
     * @param description описание группы
     * @param pathToThePicture путь к картинке группы
     * @param type тип группы
     */
    public Group(String name, Long ownerId, Boolean privacy, String description, String pathToThePicture, Integer type) {
        this.name = name;
        this.ownerId = ownerId;
        this.privacy = privacy;
        this.description = description;
        this.pathToThePicture = pathToThePicture;
        this.type = type;
        this.participants = 0L;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Long getOwnerId() {
        return ownerId;
    }

    public Boolean getPrivacy() {
        return privacy;
    }

    public String getDescription() {
        return description;
    }

    public String getPathToThePicture() {
        return pathToThePicture;
    }

    public Integer getType() {
        return type;
    }

    public Long getParticipants() {
        return participants;
    }
}
