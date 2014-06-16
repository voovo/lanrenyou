package com.lanrenyou.user.model;

import mybatis.framework.core.model.BaseValueObject;

public class UserFollow extends BaseValueObject {
    /**
     * 主键ID
     */
    private Integer id;

    /**
     * 关注者ID
     */
    private Integer fansUid;

    /**
     * 被关注者ID
     */
    private Integer starUid;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getFansUid() {
        return fansUid;
    }

    public void setFansUid(Integer fansUid) {
        this.fansUid = fansUid;
    }

    public Integer getStarUid() {
        return starUid;
    }

    public void setStarUid(Integer starUid) {
        this.starUid = starUid;
    }
}