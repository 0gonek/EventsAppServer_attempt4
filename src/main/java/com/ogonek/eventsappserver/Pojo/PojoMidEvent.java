package com.ogonek.eventsappserver.Pojo;

import com.ogonek.eventsappserver.entity.Event;

public class PojoMidEvent {
    private Long id;
    private String name;
    private Long ownerId;
    private Double latitude;
    private Double longitude;
    private Long date;
    private Long duration;
    private String type;
    private Long participants;

    public PojoMidEvent(){}

    public PojoMidEvent(Long id, String name, Long ownerId, Double latitude, Double longitude, Long date, Long duration,
                        String type, Long participants) {
        this.id = id;
        this.name = name;
        this.ownerId = ownerId;
        this.latitude = latitude;
        this.longitude = longitude;
        this.date = date;
        this.duration = duration;
        this.type = type;
        this.participants = participants;
    }

    public PojoMidEvent(Event event){
        this.id = event.getId();
        this.name = event.getName();
        this.ownerId = event.getOwnerId();
        this.latitude = event.getLatitude();
        this.longitude = event.getLongitude();
        this.date = event.getDate();
        this.duration = event.getEndTime() - event.getDate();
        this.type = event.getType();
        this.participants = event.getParticipants();
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

    public Long getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(Long ownerId) {
        this.ownerId = ownerId;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public Long getDate() {
        return date;
    }

    public void setDate(Long date) {
        this.date = date;
    }

    public Long getDuration() {
        return duration;
    }

    public void setDuration(Long duration) {
        this.duration = duration;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Long getParticipants() {
        return participants;
    }

    public void setParticipants(Long participants) {
        this.participants = participants;
    }
}
