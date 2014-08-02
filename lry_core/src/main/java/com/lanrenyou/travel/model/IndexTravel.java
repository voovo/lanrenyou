package com.lanrenyou.travel.model;

import java.util.Date;

import mybatis.framework.core.model.BaseValueObject;

public class IndexTravel extends BaseValueObject {
	
	private static final long serialVersionUID = 2269671974184705169L;

	/**
     * 主键ID
     */
    private Integer id;

    /**
     * 游记ID
     */
    private Integer tid;
    
    /**
     * 是否置顶
     */
    private Integer isTop;
    
    /**
     * 排序值
     */
    private Integer sort;
    
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

	public Integer getTid() {
		return tid;
	}

	public void setTid(Integer tid) {
		this.tid = tid;
	}

	public Integer getIsTop() {
		return isTop;
	}

	public void setIsTop(Integer isTop) {
		this.isTop = isTop;
	}

	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}

}