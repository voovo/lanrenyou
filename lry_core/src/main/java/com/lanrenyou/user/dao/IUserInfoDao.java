package com.lanrenyou.user.dao;

import java.util.List;

import com.lanrenyou.user.model.UserInfo;
import mybatis.framework.core.dao.IValueObjectDao;

public interface IUserInfoDao extends IValueObjectDao<UserInfo> {

	/**
	 * getByUid	根据UID取用户信息
	 * @param uid
	 * @return UserInfo
	 * @exception 
	*/
	public UserInfo getByUid(int uid);

	/**
	 * getListByUidList	根据UID批量取用户信息
	 * @param uidList
	 * @return List<UserInfo>
	 * @exception 
	*/
	public List<UserInfo> getListByUidList(List<Integer> uidList);

	/**
	 * getByName	根据用户名取用户信息
	 * @param name
	 * @return UserInfo
	 * @exception 
	*/
	public UserInfo getByName(String name);

	/**
	 * getByEmail	根据用户邮箱取用户信息
	 * @param email
	 * @return UserInfo
	 * @exception 
	*/
	public UserInfo getByEmail(String email);
	
	/**
	 * addUserInfo	创建用户
	 * @param userInfo
	 * @return int
	 * @exception 
	*/
	public int addUserInfo(UserInfo userInfo);
	
	/**
	 * updateUserInfo	修改用户
	 * @param userInfo
	 * @return int
	 * @exception 
	*/
	public int updateUserInfo(UserInfo userInfo);

	public int getAllCount();
}