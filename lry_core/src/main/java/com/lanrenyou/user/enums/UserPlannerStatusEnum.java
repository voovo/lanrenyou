package com.lanrenyou.user.enums;

public enum UserPlannerStatusEnum {
	/**
     * 删除
     */
    DELETE(0),

    /**
     * 待审核
     */
    WAIT_AUDIT(1),
    
    /**
     * 审核通过
     */
    AUDIT_PASS(2),
    
    /**
     * 审核不通过
     */
    AUDIT_REFUSE(3);
    
    private int value;

    private UserPlannerStatusEnum(int type) {
        this.value = type;
    }

    public int getValue() {
        return value;
    }
}
