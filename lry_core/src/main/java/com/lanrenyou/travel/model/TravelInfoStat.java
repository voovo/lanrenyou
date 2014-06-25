package com.lanrenyou.travel.model;

import mybatis.framework.core.model.BaseValueObject;

public class TravelInfoStat extends BaseValueObject {
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
}