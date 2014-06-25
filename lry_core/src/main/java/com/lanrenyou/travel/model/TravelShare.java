package com.lanrenyou.travel.model;

import mybatis.framework.core.model.BaseValueObject;

public class TravelShare extends BaseValueObject {
	
	private static final long serialVersionUID = 7078347098642707526L;

	private Integer id;

    /**
     * 分享者ID
     */
    private Integer uid;

    /**
     * 游记ID
     */
    private Integer tid;

    /**
     * 分享渠道
     */
    private int shareType;

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

    public int getShareType() {
        return shareType;
    }

    public void setShareType(int shareType) {
        this.shareType = shareType;
    }
}