package com.lanrenyou.letter.enums;

public enum PrivateLetterHasReplyEnum {
    /**
     * UN_REPLY:未回复
     */
    UN_REPLY(0),

    /**
     * HAS_REPLY:已回复
     */
    HAS_REPLY(1);

    private int value;

    private PrivateLetterHasReplyEnum(int _value) {
        this.value = _value;
    }

    public int getValue() {
        return value;
    }
}
