package com.ogonek.eventsappserver.entity;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Сущность пары мероприятие-пользователь
 */
@Entity
@Table(name = "idPairs")
public class IdPair implements Serializable{

    /**
     * Автогенерируемое поле id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    /**
     * Айди пользователя
     */
    @Column(name = "userId", nullable = false)
    private Long userId;
    /**
     * Айди мероприятия
     */
    @Column(name = "eventId", nullable = false)
    private Long eventId;

    /**
     * Конструктор
     * @param userId айди пользователя
     * @param eventId айди пероприятия
     */
    public IdPair(long userId, long eventId){
        this.userId = userId;
        this.eventId = eventId;
    }

    public Long getId() {
        return id;
    }

    public Long getUserId() {
        return userId;
    }

    public Long getEventId() {
        return eventId;
    }
}
