package com.ogonek.eventsappserver.Pojo;

import com.ogonek.eventsappserver.entity.Group;

public class PojoSmallGroup {
    private Long id;
    private String name;
    private String description;

    public PojoSmallGroup() {
    }

    public PojoSmallGroup(Long id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }

    public PojoSmallGroup(Group group){
        this.id = group.getId();
        this.name = group.getName();
        this.description = group.getDescription();
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
