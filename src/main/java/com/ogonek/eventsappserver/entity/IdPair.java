package com.ogonek.eventsappserver.entity;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "idPairs")
public class IdPair implements Serializable{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "userId", nullable = false)
    private Long userId;

    @Column(name = "eventId", nullable = false)
    private Long eventId;

    public IdPair(){}

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
