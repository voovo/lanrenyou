package com.lanrenyou.user.enums;

public enum UserInfoStatusEnum {
	/**
     * 删除
     */
    DELETE(0),

    /**
     * 待验证邮箱
     */
    WAIT_VERIFY_EMAIL(1),
    
    /**
     * 已经验证邮箱待完善用户资料
     */
    VERIFIED_EMAIL_WAIT_COMPLATE_INFO(2),

    /**
     * 已完善用户资料
     */
    HAS_COMPLAE_INFO(3);
    
    private int value;

    private UserInfoStatusEnum(int type) {
        this.value = type;
    }

    public int getValue() {
        return value;
    }
}
