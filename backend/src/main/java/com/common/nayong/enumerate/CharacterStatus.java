package com.common.nayong.enumerate;

public enum CharacterStatus {
    online(1, "online"),
    connecting(2, "connecting"),
    offline(0, "offline");


    public final int key;
    public final String name;

    CharacterStatus(int key, String name) {
        this.key = key;
        this.name = name;
    }

    public String toString() { return name; }
}
