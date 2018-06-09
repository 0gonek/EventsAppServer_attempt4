package com.ogonek.eventsappserver.entity;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Сущность пары группа-пользователь
 */
@Entity
@Table(name = "groupUserPairs")
public class GroupUserPair implements Serializable{
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
     * Айди группы
     */
    @Column(name = "groupId", nullable = false)
    private Long groupId;

    /**
     * Конструктор
     * @param userId айди пользователя
     * @param groupId айди группы
     */
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
