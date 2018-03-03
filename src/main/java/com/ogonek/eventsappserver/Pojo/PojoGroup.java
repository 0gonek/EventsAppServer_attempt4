package com.ogonek.eventsappserver.Pojo;

import com.ogonek.eventsappserver.entity.Group;

public class PojoGroup {
    private Long id;
    private String name;
    private Long ownerId;
    private Boolean privacy;
    private String description;
    private String picture;
    private Integer type;
    private Long participants;
    private Boolean isAccepted;

    public PojoGroup() {
    }

    public PojoGroup(Long id, String name, Long ownerId, Boolean privacy, String description, String picture, Integer type, Long participants, Boolean isAccepted) {
        this.id = id;
        this.name = name;
        this.ownerId = ownerId;
        this.privacy = privacy;
        this.description = description;
        this.picture = picture;
        this.type = type;
        this.participants = participants;
        this.isAccepted = isAccepted;
    }

    public Boolean getAccepted() {
        return isAccepted;
    }

    public void setAccepted(Boolean accepted) {
        isAccepted = accepted;
    }

    public PojoGroup(Group group) {
        this.id = group.getId();
        this.name = group.getName();
        this.ownerId = group.getOwnerId();
        this.privacy = group.getPrivacy();
        this.description = group.getDescription();
        this.picture = group.getPathToThePicture() + "Error";
        this.type = group.getType();
        this.participants = group.getParticipants();

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

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
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
}
