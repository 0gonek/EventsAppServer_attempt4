package com.ogonek.eventsappserver.entity;


import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "groups")
public class Group implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;
    @Column(name = "ownerId", nullable = false)
    private Long ownerId;
    @Column(name = "privacy", nullable = false)
    private Boolean privacy;
    @Column(name = "description", nullable = true)
    private String description;
    @Column(name = "pathToThePicture", nullable = true)
    private String pathToThePicture;
    @Column(name = "Type", nullable = true)
    private Integer type;
    @Column(name = "Participants", nullable = true)
    private Long participants;

    public Group() {
    }

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
