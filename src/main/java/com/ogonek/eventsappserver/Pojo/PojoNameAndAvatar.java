package com.ogonek.eventsappserver.Pojo;

import com.ogonek.eventsappserver.entity.User;
import com.ogonek.eventsappserver.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;

public class PojoNameAndAvatar {

    private long id;
    private String name;
    private String avatar;

    public PojoNameAndAvatar() {
    }

    public PojoNameAndAvatar(long id, String name, String avatar) {
        this.id = id;
        this.name = name;
        this.avatar = avatar;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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
