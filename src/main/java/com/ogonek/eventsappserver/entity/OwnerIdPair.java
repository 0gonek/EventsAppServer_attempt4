package com.ogonek.eventsappserver.entity;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Сущность пары мероприятие-владелец
 */
@Entity
@Table(name = "ownerIdPairs")
public class OwnerIdPair implements Serializable{
    /**
     * Автогенерируемое поле id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    /**
     * Айди владельца
     */
    @Column(name = "ownerId", nullable = false)
    private Long ownerId;
    /**
     * Айди мероприятия
     */
    @Column(name = "eventId", nullable = false)
    private Long eventId;

    /**
     * Конструктор
     * @param ownerId айди владельца
     * @param eventId айди мероприятия
     */
    public OwnerIdPair(long ownerId, long eventId){
        this.ownerId = ownerId;
        this.eventId = eventId;
    }

    public Long getId() {
        return id;
    }

    public Long getOwnerId() {
        return ownerId;
    }

    public Long getEventId() {
        return eventId;
    }
}
