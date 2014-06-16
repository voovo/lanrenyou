package com.lanrenyou.letter.enums;

public enum PrivateLetterStatusEnum {
    /**
     * UN_READ:未读
     */
    UN_READ(0),

    /**
     * HAS_READ:已读
     */
    HAS_READ(1),

    /**
     * HAS_REPLY:已回复
     */
    HAS_REPLY(2),

    /**
     * DELETE:删除
     */
    DELETE(-1);

    private int value;

    private PrivateLetterStatusEnum(int _value) {
        this.value = _value;
    }

    public int getValue() {
        return value;
    }
}
