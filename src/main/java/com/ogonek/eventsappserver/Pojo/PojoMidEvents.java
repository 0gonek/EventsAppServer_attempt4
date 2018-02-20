package com.ogonek.eventsappserver.Pojo;

public class PojoMidEvents {
    private PojoMidEvent[] pojoEvents;

    public PojoMidEvent[] getPojoEvents() {
        return pojoEvents;
    }

    public void setPojoEvents(PojoMidEvent[] pojoEvents) {
        this.pojoEvents = pojoEvents;
    }

    public PojoMidEvents(PojoMidEvent[] pojoEvents){
        this.pojoEvents = pojoEvents;
    }
}
