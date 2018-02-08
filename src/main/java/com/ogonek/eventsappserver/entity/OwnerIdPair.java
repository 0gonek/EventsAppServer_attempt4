package com.ogonek.eventsappserver.entity;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "ownerIdPairs")
public class OwnerIdPair implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "ownerId", nullable = false)
    private Long ownerId;

    @Column(name = "eventId", nullable = false)
    private Long eventId;

    public OwnerIdPair(){}

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
