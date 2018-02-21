package com.ogonek.eventsappserver.Pojo;

public class PojoChangeGroup {
    private Long id;
    private Long ownerId;
    private String token;
    private String description;
    private String picture;
    private Integer type;

    public PojoChangeGroup() {
    }

    public PojoChangeGroup(Long id, Long ownerId, String token, String description, String picture, Integer type) {
        this.id = id;
        this.ownerId = ownerId;
        this.token = token;
        this.description = description;
        this.picture = picture;
        this.type = type;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
}
