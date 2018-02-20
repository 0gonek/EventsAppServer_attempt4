package com.ogonek.eventsappserver.entity;


import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "groups")
public class Group implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;


}
