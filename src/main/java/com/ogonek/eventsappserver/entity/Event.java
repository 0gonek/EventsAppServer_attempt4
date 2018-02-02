package com.ogonek.eventsappserver.entity;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "events")
public class Event implements Serializable{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Long id;

    @Column(name = "name")
    private String name;

    public Event(){
    }

    public Event(String name){
        this.name = name;
    }

    @Override
    public String toString(){
        return this.name;
    }
}
