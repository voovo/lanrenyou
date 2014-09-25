package com.lanrenyou.travel.enums;

public enum IndexTravelSrcTypeEnum {
	/**
     * 删除
     */
    DELETE(0),

    /**
     * 发布成功未审核
     */
    NORMAL(1),
    
    /**
     * 审核通过
     */
    PASS(2),

    /**
     * 审核不通过
     */
    UNPASS(3);

    private int value;

    private IndexTravelSrcTypeEnum(int type) {
        this.value = type;
    }

    public int getValue() {
        return value;
    }
}
