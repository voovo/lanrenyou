package com.lanrenyou.user.enums;

public enum UserInfoTypeEnum {

    /**
     * 普通用户
     */
    NOMAL(1),
    
    /**
     * 规划师
     */
    PLANNER(2);

    private int value;

    private UserInfoTypeEnum(int type) {
        this.value = type;
    }

    public int getValue() {
        return value;
    }
}
