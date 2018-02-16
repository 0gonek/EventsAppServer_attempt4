package com.ogonek.eventsappserver.entity;

import sun.util.calendar.BaseCalendar;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "events")
public class Event implements Serializable{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;
    @Column(name = "ownerId", nullable = false)
    private Long ownerId;
    @Column(name = "latitude", nullable = false)
    private Double latitude;
    @Column(name = "longitude", nullable = false)
    private Double longitude;
    @Column(name = "date", nullable = false)
    private Long date;
    @Column(name = "duration", nullable = false)
    private Long duration;
    @Column(name = "description", nullable = true)
    private String description;
    @Column(name = "pathToThePicture", nullable = true)
    private String pathToThePicture;
    @Column(name = "Type", nullable = true)
    private String type;
    @Column(name = "Participants", nullable = true)
    private Long participants;

    public Event(){
    }

    public Event(String name, long ownerId, Double latitude, Double longitude, Long date, String type, Long duration, String description, String pathToThePicture) {
        this.name = name;
        this.ownerId = ownerId;
        this.latitude = latitude;
        this.longitude = longitude;
        this.date = date;
        this.duration = duration;
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

    public Double getLatitude() {
        return latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public Long getDate() {
        return date;
    }

    public Long getDuration() {
        return duration;
    }

    public String getDescription() {
        return description;
    }

    public String getPathToThePicture() {
        return pathToThePicture;
    }

    public String getType() {
        return type;
    }

    public long getParticipants() {
        return participants;
    }
}
