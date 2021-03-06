package com.lanrenyou.user.model;

import java.util.Date;

import org.apache.commons.lang.StringUtils;

import mybatis.framework.core.model.BaseValueObject;

public class UserInfo extends BaseValueObject {
	
	private static final long serialVersionUID = 112905237851756898L;

	/**
     * 主键ID
     */
    private Integer id;

    /**
     * 登录用户名
     */
    private String name;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 密码
     */
    private String userPass;

    /**
     * 历史密码
     */
    private String historyPasswd;

    /**
     * 头像
     */
    private String avatar;

    /**
     * 昵称
     */
    private String nickname;

    /**
     * 用户类型[0:普通用户; 1:规划师]
     */
    private int userType = 0;

    /**
     * 微博账号
     */
    private String weiboName;

    /**
     * 微博链接
     */
    private String weiboUrl;

    /**
     * 微信账号
     */
    private String wechatName;
    
    /**
     * 现住址
     */
    private String presentAddress;
    
    /**
     * 以前住址
     */
    private String previousAddress;

    /**
     * 状态
     */
    private int status;
    
    /**
     * 个人简介
     */
    private String userIntro;

    private Integer createUid;

    private Date createTime;

    private String createIp;

    private Integer updateUid;

    private String updateIp;
    
    private int viewCntSum;

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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email == null ? null : email.trim();
    }

    public String getUserPass() {
        return userPass;
    }

    public void setUserPass(String password) {
        this.userPass = password == null ? null : password.trim();
    }

    public String getHistoryPasswd() {
        return historyPasswd;
    }

    public void setHistoryPasswd(String historyPasswd) {
        this.historyPasswd = historyPasswd == null ? null : historyPasswd.trim();
    }

    public String getAvatar() {
    	if(StringUtils.isNotBlank(avatar)){
    		return avatar;
    	} else {
    		return "/resources/imgs/user/u_face_80-80.jpg";
    	}
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar == null ? null : avatar.trim();
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname == null ? null : nickname.trim();
    }

    public int getUserType() {
        return userType;
    }

    public void setUserType(int userType) {
        this.userType = userType;
    }

    public String getWeiboName() {
        return weiboName;
    }

    public void setWeiboName(String weiboName) {
        this.weiboName = weiboName == null ? null : weiboName.trim();
    }

    public String getWeiboUrl() {
        return weiboUrl;
    }

    public void setWeiboUrl(String weiboUrl) {
        this.weiboUrl = weiboUrl == null ? null : weiboUrl.trim();
    }

    public String getWechatName() {
        return wechatName;
    }

    public void setWechatName(String wechatName) {
        this.wechatName = wechatName == null ? null : wechatName.trim();
    }
    
    public String getUserIntro() {
        return userIntro;
    }

    public void setUserIntro(String userIntro) {
        this.userIntro = userIntro == null ? null : userIntro.trim();
    }

    public String getPresentAddress() {
		return presentAddress;
	}

	public void setPresentAddress(String presentAddress) {
		this.presentAddress = presentAddress;
	}

	public String getPreviousAddress() {
		return previousAddress;
	}

	public void setPreviousAddress(String previousAddress) {
		this.previousAddress = previousAddress;
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

    public String getUpdateIp() {
        return updateIp;
    }

    public void setUpdateIp(String updateIp) {
        this.updateIp = updateIp == null ? null : updateIp.trim();
    }

	public int getViewCntSum() {
		return viewCntSum;
	}

	public void setViewCntSum(int viewCntSum) {
		this.viewCntSum = viewCntSum;
	}
    
}