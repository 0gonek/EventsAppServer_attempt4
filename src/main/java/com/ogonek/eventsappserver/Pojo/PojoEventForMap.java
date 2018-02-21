package com.ogonek.eventsappserver.Pojo;

import com.ogonek.eventsappserver.entity.Event;

public class PojoEventForMap {
    private Long id;
    private Double latitude;
    private Double longitude;
    private Integer type;

    public PojoEventForMap() {
    }

    public PojoEventForMap(Long id, Double latitude, Double longitude, Integer type) {
        this.id = id;
        this.latitude = latitude;
        this.longitude = longitude;
        this.type = type;
    }

    public PojoEventForMap(Event event) {
        this.id = event.getId();
        this.latitude = event.getLatitude();
        this.longitude = event.getLongitude();
        this.type = event.getType();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }
}
