package com.ogonek.eventsappserver.Pojo;

public class PojoEventsForMap {
    private PojoEventForMap[] pojoEvents;

    public PojoEventForMap[] getPojoEvents() {
        return pojoEvents;
    }

    public void setPojoEvents(PojoEventForMap[] pojoEvents) {
        this.pojoEvents = pojoEvents;
    }

    public PojoEventsForMap(PojoEventForMap[] pojoEvents) {
        this.pojoEvents = pojoEvents;
    }
}
