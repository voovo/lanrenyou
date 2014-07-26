package com.lanrenyou.travel.enums;

public enum TravelInfoIsTopEnum {
	/**
     * 非置顶
     */
    NO(0),

    /**
     * 置顶
     */
    YES(1);

    private int value;

    private TravelInfoIsTopEnum(int type) {
        this.value = type;
    }

    public int getValue() {
        return value;
    }
}
