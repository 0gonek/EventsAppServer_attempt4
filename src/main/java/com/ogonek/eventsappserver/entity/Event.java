package com.ogonek.eventsappserver.entity;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "events")
public class Event implements Serializable{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "name")
    public String name;

    //@Column(name = "ownerId")
    //private Long ownerId;

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
