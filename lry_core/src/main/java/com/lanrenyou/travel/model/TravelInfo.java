package com.lanrenyou.travel.model;

import java.util.Date;
import mybatis.framework.core.model.BaseValueObject;

public class TravelInfo extends BaseValueObject {
	private static final long serialVersionUID = -1360016552922311452L;

	/**
     * 主键ID
     */
    private Integer id;

    /**
     * 城市
     */
    private String city;

    /**
     * 发布者ID
     */
    private Integer uid;

    /**
     * 标题
     */
    private String title;

    /**
     * 是否精华[0:不是; 1:是]
     */
    private int isElite = 0;

    /**
     * 是否置顶[0:否; 1:是]
     */
    private int isTop = 0;

    /**
     * 状态[1:待审核; 2:审核通过; 3:审核不通过; -1:删除]
     */
    private int status;

    private Integer createUid;

    private Date createTime;

    private String createIp;

    private Integer updateUid;
    
    private Date updateTime;

    private String updateIp;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public Integer getUid() {
        return uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title == null ? null : title.trim();
    }

    public int getIsElite() {
        return isElite;
    }

    public void setIsElite(int isElite) {
        this.isElite = isElite;
    }

    public int getIsTop() {
        return isTop;
    }

    public void setIsTop(int isTop) {
        this.isTop = isTop;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Integer getCreateUid() {
        return createUid;
    }

    public void setCreateUid(Integer createUid) {
        this.createUid = createUid;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getCreateIp() {
        return createIp;
    }

    public void setCreateIp(String createIp) {
        this.createIp = createIp == null ? null : createIp.trim();
    }

    public Integer getUpdateUid() {
        return updateUid;
    }

    public void setUpdateUid(Integer updateUid) {
        this.updateUid = updateUid;
    }
    
    public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public String getUpdateIp() {
        return updateIp;
    }

    public void setUpdateIp(String updateIp) {
        this.updateIp = updateIp == null ? null : updateIp.trim();
    }
}