package com.ogonek.eventsappserver.Pojo;

public class PojoNewGroup {
    private String name;
    private Long ownerId;
    private String token;
    private Boolean privacy;
    private String description;
    private byte[] picture;
    private Integer type;

    public PojoNewGroup() {
    }

    public PojoNewGroup(String name, Long ownerId, String token, Boolean privacy, String description, byte[] picture, Integer type) {
        this.name = name;
        this.ownerId = ownerId;
        this.token = token;
        this.privacy = privacy;
        this.description = description;
        this.picture = picture;
        this.type = type;
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

    public byte[] getPicture() {
        return picture;
    }

    public void setPicture(byte[] picture) {
        this.picture = picture;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }
}
