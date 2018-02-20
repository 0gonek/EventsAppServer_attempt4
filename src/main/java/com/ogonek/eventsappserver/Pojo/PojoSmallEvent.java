package com.ogonek.eventsappserver.Pojo;

import com.ogonek.eventsappserver.entity.Event;

public class PojoSmallEvent {
    private Long id;
    private String name;
    private Long date;
    private String description;

    public PojoSmallEvent() {
    }

    public PojoSmallEvent(Long id, String name, Long date, String description) {
        this.id = id;
        this.name = name;
        this.date = date;
        this.description = description;
    }

    public PojoSmallEvent(Event event){
        this.id = event.getId();
        this.name = event.getName();
        this.date = event.getDate();
        this.description = event.getDescription();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getDate() {
        return date;
    }

    public void setDate(Long date) {
        this.date = date;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
