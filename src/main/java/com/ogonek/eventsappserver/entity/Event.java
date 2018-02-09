package com.ogonek.eventsappserver.entity;

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
    @Column(name = "description", nullable = true)
    private String description;
    @Column(name = "pathToThePicture", nullable = true)
    private String pathToThePicture;


    @Override
    public String toString(){
        return this.name;
    }

    public Event(){
    }

    public Event(String name){
        this.name = name;
    }

    public static boolean deleteEvent(Long id){
        return false;
    }
}
