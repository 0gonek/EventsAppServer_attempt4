package com.ogonek.eventsappserver.entity;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "users")
public class User implements Serializable{

    private static final int EVENTSCOUNT = 2;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;
    @Column(name = "login", nullable = false)
    private String login;
    @Column(name = "password", nullable = false)
    private String password;
    @Column(name = "events")
    private Long[] eventsId;
    private int eventsIdLast;
    @Column(name = "ownEventsId")
    private Long[] ownEventsId;
    private int ownEventsIdLast;

    //public User(){}

    public User(String login, String password){
        this.password = password;
        this.login = login;
        this.name = login;
        eventsId = new Long[EVENTSCOUNT];
        ownEventsId = new Long[EVENTSCOUNT];
        eventsIdLast = -1;
        ownEventsIdLast = -1;
    }

    public boolean verifyPassword(String password){
        return this.password == password;
    }

    public boolean addEvent(Long eventId){
        if(eventsIdLast < EVENTSCOUNT-1){
            eventsIdLast++;
            eventsId[eventsIdLast] = eventId;
        }
        else{
            int firstEmptyElement = heapyfyArray(eventsId);
            if(firstEmptyElement < EVENTSCOUNT){
                eventsIdLast = firstEmptyElement;
                eventsId[eventsIdLast] = eventId;
            }
            else return false;
        }
        return true;
    }

    public int heapyfyArray(Long[] array){
        int freePos = 0;
        int currentPos = 0;
        while (currentPos < array.length){
            while (freePos < array.length && array[freePos] != null) freePos++;
            currentPos = freePos+1;
            while (currentPos < array.length && array[currentPos] == null) currentPos++;
            if(currentPos < array.length){
                array[freePos] = array[currentPos];
                array[currentPos] = null;
            }
        }
        return freePos;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Long[] getEventsId() {
        return eventsId;
    }

    public void setEventsId(Long[] eventsId) {
        this.eventsId = eventsId;
    }

    public Long[] getOwnEventsId() {
        return ownEventsId;
    }

    public void setOwnEventsId(Long[] ownEventsId) {
        this.ownEventsId = ownEventsId;
    }

}
