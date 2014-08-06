package com.lanrenyou.admin.model;

import java.util.Date;
import java.util.List;

import mybatis.framework.core.model.BaseValueObject;

public class AdminRole extends BaseValueObject {

	private static final long serialVersionUID = 1620437182628172901L;

	private Integer id;

    /**
     * 角色名称
     */
    private String name;

    /**
     * 状态[-1:删除, 0:停用, 1:正常]
     */
    private int status;
    
    private List<Integer> adminPowerItemList;

    private Integer createUid;

    private Date createTime;

    private String createIp;

    private Integer updateUid;

    private String updateIp;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public List<Integer> getAdminPowerItemList() {
		return adminPowerItemList;
	}

	public void setAdminPowerItemList(List<Integer> adminPowerItemList) {
		this.adminPowerItemList = adminPowerItemList;
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

    public String getUpdateIp() {
        return updateIp;
    }

    public void setUpdateIp(String updateIp) {
        this.updateIp = updateIp == null ? null : updateIp.trim();
    }
}