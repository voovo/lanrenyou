package com.lanrenyou.travel.enums;

public enum TravelInfoIsEliteEnum {
	/**
     * 非精华，普通
     */
    NO(0),

    /**
     * 精华
     */
    YES(1);

    private int value;

    private TravelInfoIsEliteEnum(int type) {
        this.value = type;
    }

    public int getValue() {
        return value;
    }
}
