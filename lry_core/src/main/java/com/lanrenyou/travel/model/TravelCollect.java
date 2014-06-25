package com.lanrenyou.travel.model;

import mybatis.framework.core.model.BaseValueObject;

public class TravelCollect extends BaseValueObject {
    /**
     * 主键ID
     */
    private Integer id;

    /**
     * 用户UID
     */
    private Integer uid;

    /**
     * 游记ID
     */
    private Integer tid;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUid() {
        return uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }

    public Integer getTid() {
        return tid;
    }

    public void setTid(Integer tid) {
        this.tid = tid;
    }
}