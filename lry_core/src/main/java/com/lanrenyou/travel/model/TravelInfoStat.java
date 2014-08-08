package com.lanrenyou.travel.model;

import java.util.Date;

import mybatis.framework.core.model.BaseValueObject;

public class TravelInfoStat extends BaseValueObject {
	
	private static final long serialVersionUID = -3541395439397329272L;

	/**
     * 主键ID
     */
    private Integer id;

    /**
     * 游记ID
     */
    private Integer tid;

    /**
     * 浏览量
     */
    private Integer viewCnt;

    /**
     * 喜欢数
     */
    private Integer likeCnt = 0;
    
    private Date updateTime;

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

    public Integer getViewCnt() {
        return viewCnt;
    }

    public void setViewCnt(Integer viewCnt) {
        this.viewCnt = viewCnt;
    }

    public Integer getLikeCnt() {
        return likeCnt;
    }

    public void setLikeCnt(Integer likeCnt) {
        this.likeCnt = likeCnt;
    }

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
    
}