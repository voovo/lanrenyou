package com.lanrenyou.travel.enums;

public enum TravelInfoStatusEnum {
	/**
     * 删除
     */
    DELETE(0),

    /**
     * 正常
     */
    NORMAL(1),

    /**
     * 审核不通过
     */
    UNPASS(2);

    private int value;

    private TravelInfoStatusEnum(int type) {
        this.value = type;
    }

    public int getValue() {
        return value;
    }
}
