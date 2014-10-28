package com.lanrenyou.travel.model;

import java.util.Date;

import mybatis.framework.core.model.BaseValueObject;

public class IndexBanner extends BaseValueObject {
	
	/**
	 * serialVersionUID:（用一句话描述这个变量表示什么）
	 */
	private static final long serialVersionUID = -570243457342981346L;

	/**
     * 主键ID
     */
    private Integer id;

    /**
     * 图片alt
     */
    private String alt;
    
    /**
     * 图片的URL
     */
    private String picUrl;
    
    /**
     * Banner链接地址
     */
    private String linkUrl;
    
    /**
     * 排序
     */
    private Integer sort;
    
    /**
     * 是否删除
     */
    private Integer isDel;
    
    /**
     * 更新时间
     */
    private Date updateTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

	public String getAlt() {
		return alt;
	}

	public void setAlt(String alt) {
		this.alt = alt;
	}

	public String getPicUrl() {
		return picUrl;
	}

	public void setPicUrl(String picUrl) {
		this.picUrl = picUrl;
	}

	public String getLinkUrl() {
		return linkUrl;
	}

	public void setLinkUrl(String linkUrl) {
		this.linkUrl = linkUrl;
	}

	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}

	public Integer getIsDel() {
		return isDel;
	}

	public void setIsDel(Integer isDel) {
		this.isDel = isDel;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

}