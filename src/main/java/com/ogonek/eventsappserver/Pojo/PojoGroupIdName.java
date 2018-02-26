package com.ogonek.eventsappserver.Pojo;

import com.ogonek.eventsappserver.entity.Group;

public class PojoGroupIdName {
    private Long id;
    private String name;

    public PojoGroupIdName() {
    }

    public PojoGroupIdName(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public PojoGroupIdName(Group group){
        this.id = group.getId();
        this.name = group.getName();
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
}
