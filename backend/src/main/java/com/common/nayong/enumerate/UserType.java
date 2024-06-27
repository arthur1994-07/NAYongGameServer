package com.common.nayong.enumerate;

public enum UserType {
    anonymous(0 , "anonymous_user"),
    player(1, "player"),
    playerCommand(31, "player_with_command"),
    playerRestrict(30, "player_with_restriction"),
    gm(32, "GM");

    public final int code;
    public final String name;

    UserType(int code, String name){
        this.code = code;
        this.name = name;
    }

    public String toString() { return name; }
}
