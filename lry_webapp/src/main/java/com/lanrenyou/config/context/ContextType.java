package com.lanrenyou.config.context;

public enum ContextType {
    NORMAL(0), SINA_APP(1);
    private int code;

    private ContextType(int code) {
        this.code = code;
    }
}