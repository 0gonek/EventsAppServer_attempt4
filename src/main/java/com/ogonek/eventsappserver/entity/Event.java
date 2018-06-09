package com.ogonek.eventsappserver.entity;

import sun.util.calendar.BaseCalendar;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Сущность мероприятия
 */
@Entity
@Table(name = "events")
public class Event implements Serializable{

    /**
     * Автогенерируемое поле id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    /**
     * Название мероприятия
     */
    @Column(name = "name", nullable = false)
    private String name;
    /**
     * Айди организатора мероприятия
     */
    @Column(name = "ownerId", nullable = false)
    private Long ownerId;
    /**
     * Широта мероприятия
     */
    @Column(name = "latitude", nullable = false)
    private Double latitude;
    /**
     * Долгота мероприятия
     */
    @Column(name = "longitude", nullable = false)
    private Double longitude;
    /**
     * Дата проведения мероприятия
     */
    @Column(name = "date", nullable = false)
    private Long date;
    /**
     * Дата окончания мероприятия
     */
    @Column(name = "endtime", nullable = false)
    private Long endTime;
    /**
     * Приватность мероприятия
     */
    @Column(name = "privacy", nullable = false)
    private Boolean privacy;
    /**
     * Описание мероприятия
     */
    @Column(name = "description", nullable = true)
    private String description;
    /**
     * Путь к картинке мероприятия
     */
    @Column(name = "pathToThePicture", nullable = true)
    private String pathToThePicture;
    /**
     * Тип мероприятия
     */
    @Column(name = "Type", nullable = true)
    private Integer type;
    /**
     * Число участников мероприятия
     */
    @Column(name = "Participants", nullable = true)
    private Long participants;
    /**
     * Айди группы, в которой создано мероприятие
     */
    @Column(name = "GroupID", nullable = true)
    private Long groupID;


    public Event(){
    }

    /**
     * Конструктор
     * @param name название мероприятия
     * @param ownerId айди владельца мероприятия
     * @param latitude широта мероприятия
     * @param longitude долгота мероприятия
     * @param date дата проведения мероприятия
     * @param type тип мероприятия
     * @param endTime дата окончания мероприятия
     * @param privacy приватность мероприятия
     * @param description описание мероприятия
     * @param pathToThePicture путь к картинке мероприятия
     * @param groupID айди группы, в которой создано мероприятие
     */
    public Event(String name, long ownerId, Double latitude, Double longitude, Long date, Integer type, Long endTime,
                 boolean privacy, String description, String pathToThePicture, Long groupID) {
        this.name = name;
        this.ownerId = ownerId;
        this.latitude = latitude;
        this.longitude = longitude;
        this.date = date;
        this.endTime = endTime;
        this.privacy = privacy;
        this.description = description;
        this.pathToThePicture = pathToThePicture;
        this.type = type;
        this.participants = 0L;
        this.groupID = groupID;
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

    public Double getLatitude() {
        return latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public Long getDate() {
        return date;
    }

    public Long getEndTime() {
        return endTime;
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

    public long getParticipants() {
        return participants;
    }

    public Long getGroupID() {
        return groupID;
    }
}
