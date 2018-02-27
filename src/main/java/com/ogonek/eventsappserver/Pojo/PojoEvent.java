package com.ogonek.eventsappserver.Pojo;

import com.ogonek.eventsappserver.entity.Event;

public class PojoEvent {
    private Long id;
    private String name;
    private Long ownerId;
    private Double latitude;
    private Double longitude;
    private Long date;
    private Long duration;
    private boolean privacy;
    private String description;
    private String pathToThePicture;
    private Integer type;
    private Long participants;
    private Long groupId;
    private Boolean isAccepted;
    private String groupName;

    public PojoEvent() {
    }

    public PojoEvent(Long id, String name, Long ownerId, Double latitude, Double longitude, Long date, Long duration,
                     Boolean privacy, String description, String pathToThePicture, Integer type, Long participants,
                     Long groupId, Boolean isAccepted, String groupName) {
        this.id = id;
        this.name = name;
        this.ownerId = ownerId;
        this.latitude = latitude;
        this.longitude = longitude;
        this.date = date;
        this.duration = duration;
        this.privacy = privacy;
        this.description = description;
        this.pathToThePicture = pathToThePicture; //ДОДЕЛАТЬ!!
        this.type = type;
        this.participants = participants;
        this.groupId = groupId;
        this.isAccepted = isAccepted;
        this.groupName = groupName;
    }

    public PojoEvent(Event event){
        this.id = event.getId();
        this.name = event.getName();
        this.ownerId = event.getOwnerId();
        this.latitude = event.getLatitude();
        this.longitude = event.getLongitude();
        this.date = event.getDate();
        this.duration = event.getEndTime() - event.getDate();
        this.privacy = event.getPrivacy();
        this.description = event.getDescription();
        this.pathToThePicture = event.getPathToThePicture() + "Error"; //ДОДЕЛАТЬ!!
        this.type = event.getType();
        this.participants = event.getParticipants();
        this.groupId = event.getGroupID();
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

    public Boolean getPrivacy() {
        return privacy;
    }

    public void setPrivacy(Boolean privacy) {
        this.privacy = privacy;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPathToThePicture() {
        return pathToThePicture;
    }

    public void setPathToThePicture(String pathToThePicture) {
        this.pathToThePicture = pathToThePicture;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Long getParticipants() {
        return participants;
    }

    public void setParticipants(Long participants) {
        this.participants = participants;
    }

    public Long getGroupId() {
        return groupId;
    }

    public void setGroupId(Long groupId) {
        this.groupId = groupId;
    }

    public Boolean getAccepted() {
        return isAccepted;
    }

    public void setAccepted(Boolean accepted) {
        isAccepted = accepted;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }
}
