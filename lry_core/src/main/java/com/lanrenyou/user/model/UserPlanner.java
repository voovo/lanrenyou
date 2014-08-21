package com.lanrenyou.user.model;

import java.util.Date;
import mybatis.framework.core.model.BaseValueObject;

public class UserPlanner extends BaseValueObject {
	
	private static final long serialVersionUID = 3189562919684403320L;

	/**
     * 主键ID
     */
    private Integer id;

    /**
     * 用户ID
     */
    private Integer uid;

    /**
     * 可规划地区
     */
    private String targetCity;
    
    /**
     * 收费标准
     */
    private String fees;

    /**
     * 规划价格
     */
    private Double price;

    /**
     * 收费模式[1:按天; 2:按周; 3:按次;]
     */
    private int chargeMode;

    /**
     * 状态
     */
    private int status;
    
    /**
     * 审核拒绝原因
     */
    private String refuseReason;

    private Integer createUid;

    private Date createTime;

    private String createIp;

    private Integer updateUid;

    private String updateIp;
    
    private Date updateTime;

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

    public String getTargetCity() {
        return targetCity;
    }

    public void setTargetCity(String targetCity) {
        this.targetCity = targetCity == null ? null : targetCity.trim();
    }

    public String getFees() {
		return fees;
	}

	public void setFees(String fees) {
		this.fees = fees;
	}

	public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public int getChargeMode() {
        return chargeMode;
    }

    public void setChargeMode(int chargeMode) {
        this.chargeMode = chargeMode;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getRefuseReason() {
		return refuseReason;
	}

	public void setRefuseReason(String refuseReason) {
		this.refuseReason = refuseReason;
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