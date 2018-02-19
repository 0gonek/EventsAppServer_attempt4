package com.ogonek.eventsappserver.Pojo;

public class PojoSmallEvents {
    private PojoSmallEvent[] pojoEvents;

    public PojoSmallEvent[] getPojoEvents() {
        return pojoEvents;
    }

    public void setPojoEvents(PojoSmallEvent[] pojoEvents) {
        this.pojoEvents = pojoEvents;
    }

    public PojoSmallEvents(PojoSmallEvent[] pojoEvents){
        this.pojoEvents = pojoEvents;
    }
}
