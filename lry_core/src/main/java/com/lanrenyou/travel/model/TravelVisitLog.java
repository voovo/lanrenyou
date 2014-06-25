package com.lanrenyou.travel.model;

import mybatis.framework.core.model.BaseValueObject;

public class TravelVisitLog extends BaseValueObject {
	
	private static final long serialVersionUID = 7036029698127001898L;

	/**
     * 主键ID
     */
    private Integer id;

    /**
     * 游记ID
     */
    private Integer tid;

    /**
     * 用户ID
     */
    private Integer uid;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getTid() {
        return tid;
    }

    public void setTid(Integer tid) {
        this.tid = tid;
    }

    public Integer getUid() {
        return uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }
}