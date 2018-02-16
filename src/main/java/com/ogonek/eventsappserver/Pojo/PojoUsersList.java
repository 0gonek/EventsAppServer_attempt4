package com.ogonek.eventsappserver.Pojo;

public class PojoUsersList {
    private PojoNameAndAvatar[] pojoNameAndAvatars;

    public PojoNameAndAvatar[] getPojoNameAndAvatars() {
        return pojoNameAndAvatars;
    }

    public void setPojoNameAndAvatars(PojoNameAndAvatar[] pojoNameAndAvatars) {
        this.pojoNameAndAvatars = pojoNameAndAvatars;
    }

    public PojoUsersList(PojoNameAndAvatar[] pojoNameAndAvatars) {
        this.pojoNameAndAvatars = pojoNameAndAvatars;
    }
}
