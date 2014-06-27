package com.lanrenyou.admin.model;

import java.util.Date;
import mybatis.framework.core.model.BaseValueObject;

public class AdminLog extends BaseValueObject {
	
	private static final long serialVersionUID = 4298268463418482347L;

	private Integer id;

    /**
     * 管理员ID
     */
    private Integer adminId;

    /**
     * 操作类型
     */
    private int operType;

    private String operContext;

    private Date operTime;

    private String operIp;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getAdminId() {
        return adminId;
    }

    public void setAdminId(Integer adminId) {
        this.adminId = adminId;
    }

    public int getOperType() {
        return operType;
    }

    public void setOperType(int operType) {
        this.operType = operType;
    }

    public String getOperContext() {
        return operContext;
    }

    public void setOperContext(String operContext) {
        this.operContext = operContext == null ? null : operContext.trim();
    }

    public Date getOperTime() {
        return operTime;
    }

    public void setOperTime(Date operTime) {
        this.operTime = operTime;
    }

    public String getOperIp() {
        return operIp;
    }

    public void setOperIp(String operIp) {
        this.operIp = operIp == null ? null : operIp.trim();
    }
}