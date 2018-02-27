package com.ogonek.eventsappserver.Pojo;

public class PojoNewEvent {
    private String name;
    private Long ownerId;
    private String token;
    private Double latitude;
    private Double longitude;
    private Long date;
    private Long duration;
    private Boolean privacy;
    private String description;
    private Byte[] picture;
    private Integer type;
    private Long groupId;

    public PojoNewEvent() {
    }

    public PojoNewEvent(String name, Long ownerId, String token, Double latitude, Double longitude, Long date, Long duration,
                     String description, Byte[] picture, Integer type, Long groupId) {
        this.name = name;
        this.ownerId = ownerId;
        this.token = token;
        this.latitude = latitude;
        this.longitude = longitude;
        this.date = date;
        this.duration = duration;
        this.privacy = privacy;
        this.description = description;
        this.picture = picture; //ДОДЕЛАТЬ!!
        this.type = type;
        this.groupId = groupId;
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

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
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

    public boolean getPrivacy() {
        return privacy;
    }

    public void setPrivacy(boolean privacy) {
        this.privacy = privacy;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Byte[] getPicture() {
        return picture;
    }

    public void setPicture(Byte[] picture) {
        this.picture = picture;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Long getGroupId() {
        return groupId;
    }

    public void setGroupId(Long groupId) {
        this.groupId = groupId;
    }
}
