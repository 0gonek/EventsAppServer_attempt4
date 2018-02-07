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
    @Column(name = "eventsId")
    private Long[] eventsId;
    private int eventsIdLast;
    @Column(name = "ownEventsId")
    private Long[] ownEventsId;
    private int ownEventsIdLast;

    public User(){}

    public User(String name, String login, String password){
        this.password = password;
        this.login = login;
        this.name = name;
        eventsId = new Long[EVENTSCOUNT];
        ownEventsId = new Long[EVENTSCOUNT];
        eventsIdLast = -1;
        ownEventsIdLast = -1;
    }

    public boolean verifyPassword(String password){
        return this.password == password;
    }

    public boolean renameUser(String newName){
        this.name = newName;
        return true;
    }

    public boolean changePassword(String oldPassword, String newPassword){
        if(verifyPassword(oldPassword)) {
            password = newPassword;
            return true;
        }
        else
            return false;
    }

    public boolean changeName(String password, String name){
        if(verifyPassword(password)) {
            this.name = name;
            return true;
        }
        else
            return false;
    }

    public boolean addEvent(long eventId){
        if(eventsIdLast < EVENTSCOUNT-1){
            eventsIdLast++;
            eventsId[eventsIdLast] = eventId;
        }
        else{
            int firstEmptyElement = heapifyArray(eventsId);
            if(firstEmptyElement < EVENTSCOUNT){
                eventsIdLast = firstEmptyElement;
                eventsId[eventsIdLast] = eventId;
            }
            else return false;
        }
        return true;
    }

    public boolean deleteEventFromList(long eventId){
        Integer number = getNumberById(eventId, eventsId);
        if(number != null){
            eventsId[number] = null;
            return true;
        }
        else return false;
    }

    public boolean addOwnEvent(long ownEventId){
        if(ownEventsIdLast < EVENTSCOUNT - 1){
            ownEventsIdLast++;
            ownEventsId[ownEventsIdLast] = ownEventId;
        }
        else{
            int firstEmptyElement = heapifyArray(ownEventsId);
            if(firstEmptyElement < EVENTSCOUNT){
                ownEventsIdLast = firstEmptyElement;
                ownEventsId[ownEventsIdLast] = ownEventId;
            }
            else return false;
        }
        return true;
    }

    public boolean deleteOwnEvent(long ownEventId){
        Integer number = getNumberById(ownEventId, ownEventsId);
        if(number != null){
            ownEventsId[number] = null;
            Event.deleteEvent(ownEventId);
            return true;
        }
        else return false;
    }

    private static Integer getNumberById(Long id, Long[] array){
        for (int i = 0; i < array.length; i++) {
            if(array[i] == id)
                return i;
        }
        return null;
    }

    private static int heapifyArray(Long[] array){
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
