package com.ogonek.eventsappserver.entity;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "groupUserPairs")
public class GroupUserPair implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "userId", nullable = false)
    private Long userId;
    @Column(name = "groupId", nullable = false)
    private Long groupId;

    public GroupUserPair() {
    }

    public GroupUserPair(Long userId, Long groupId) {
        this.userId = userId;
        this.groupId = groupId;
    }

    public Long getId() {
        return id;
    }

    public Long getUserId() {
        return userId;
    }

    public Long getGroupId() {
        return groupId;
    }
}
