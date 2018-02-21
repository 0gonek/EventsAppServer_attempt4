package com.ogonek.eventsappserver.Pojo;

public class PojoNewGroup {
    private Long id;
    private String name;
    private Long ownerId;
    private String token;
    private Boolean privacy;
    private String description;
    private String picture;
    private Integer type;
    private Long participants;

    public PojoNewGroup() {
    }

    public PojoNewGroup(Long id, String name, Long ownerId, String token, Boolean privacy, String description, String picture, Integer type, Long participants) {
        this.id = id;
        this.name = name;
        this.ownerId = ownerId;
        this.token = token;
        this.privacy = privacy;
        this.description = description;
        this.picture = picture;
        this.type = type;
        this.participants = participants;
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

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
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
