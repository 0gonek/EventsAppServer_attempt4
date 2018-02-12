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
    private String ownerId;
    @Column(name = "latitude", nullable = false)
    private Double latitude;
    @Column(name = "longitude", nullable = false)
    private Double longitude;
    @Column(name = "date", nullable = false)
    private Long date;
    @Column(name = "description", nullable = true)
    private String description;
    @Column(name = "pathToThePicture", nullable = true)
    private String pathToThePicture;

    public Event(){
    }

    public Event(String name, String ownerId, Double latitude, Double longitude, Long date, String description, String pathToThePicture) {
        this.name = name;
        this.ownerId = ownerId;
        this.latitude = latitude;
        this.longitude = longitude;
        this.date = date;
        this.description = description;
        this.pathToThePicture = pathToThePicture;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getOwnerId() {
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

    public String getDescription() {
        return description;
    }

    public String getPathToThePicture() {
        return pathToThePicture;
    }
}
