package com.ogonek.eventsappserver.Pojo;

public class PojoEvents {
    private PojoEvent[] pojoEvents;

    public PojoEvents(PojoEvent[] pojoEvents) {
        this.pojoEvents = pojoEvents;
    }

    public PojoEvents() {
    }

    public PojoEvent[] getPojoEvents() {
        return pojoEvents;
    }

    public void setPojoEvents(PojoEvent[] pojoEvents) {
        this.pojoEvents = pojoEvents;
    }
}
