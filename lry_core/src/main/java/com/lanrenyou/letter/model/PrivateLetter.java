package com.lanrenyou.letter.model;

import mybatis.framework.core.model.BaseValueObject;

public class PrivateLetter extends BaseValueObject {
    /**
     * 主键ID
     */
    private Integer id;

    /**
     * 发信人
     */
    private Integer senderUid;

    /**
     * 收信人
     */
    private Integer receiverUid;

    /**
     * 内容
     */
    private String context;

    /**
     * 是否已读[0:未读; 1:已读]
     */
    private int hasRead = 0;

    /**
     * 是否回复[0:未回复; 1:已回复]
     */
    private int hasReply = 0;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getSenderUid() {
        return senderUid;
    }

    public void setSenderUid(Integer senderUid) {
        this.senderUid = senderUid;
    }

    public Integer getReceiverUid() {
        return receiverUid;
    }

    public void setReceiverUid(Integer receiverUid) {
        this.receiverUid = receiverUid;
    }

    public String getContext() {
        return context;
    }

    public void setContext(String context) {
        this.context = context == null ? null : context.trim();
    }

    public int getHasRead() {
        return hasRead;
    }

    public void setHasRead(int hasRead) {
        this.hasRead = hasRead;
    }

    public int getHasReply() {
        return hasReply;
    }

    public void setHasReply(int hasReply) {
        this.hasReply = hasReply;
    }
}