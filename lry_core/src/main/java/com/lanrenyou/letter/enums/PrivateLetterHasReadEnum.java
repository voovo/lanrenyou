package com.lanrenyou.letter.enums;

public enum PrivateLetterHasReadEnum {
    /**
     * UN_READ:未读
     */
    UN_READ(0),

    /**
     * HAS_READ:已读
     */
    HAS_READ(1);

    private int value;

    private PrivateLetterHasReadEnum(int _value) {
        this.value = _value;
    }

    public int getValue() {
        return value;
    }
}
