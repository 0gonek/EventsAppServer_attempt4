package com.ogonek.eventsappserver.Pojo;

import com.ogonek.eventsappserver.entity.User;
import com.ogonek.eventsappserver.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;

public class PojoNameAndBigAvatar {

    private long serverID;
    private String name;
    private String avatar;

    public PojoNameAndBigAvatar() {
    }

    public PojoNameAndBigAvatar(long serverID, String name, String avatar) {
        this.serverID = serverID;
        this.name = name;
        this.avatar = avatar;
    }

    public long getServerID() {
        return serverID;
    }

    public void setServerID(long id) {
        this.serverID = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }
}
