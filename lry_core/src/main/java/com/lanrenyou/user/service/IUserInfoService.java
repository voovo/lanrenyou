package com.lanrenyou.user.service;

import java.util.List;
import java.util.Map;

import com.lanrenyou.user.model.UserInfo;
import mybatis.framework.core.service.IValueObjectService;

public interface IUserInfoService extends IValueObjectService<UserInfo> {
	
	/**
	 * getUserInfoByUid	根据用户UID查询用户信息
	 * @param uid
	 * @return UserInfo
	 * @exception 
	*/
	public UserInfo getUserInfoByUid(int uid);
	
	/**
	 * getUserInfoListByUidList	根据用户UID批量查询用户信息
	 * @param uidList
	 * @return Map<Integer,UserInfo>
	 * @exception 
	*/
	public Map<Integer, UserInfo> getUserInfoListByUidList(List<Integer> uidList);
	
	/**
	 * getUserInfoByName	根据用户名查询用户信息
	 * @param name
	 * @return UserInfo
	 * @exception 
	*/
	public UserInfo getUserInfoByName(String name);
	
	/**
	 * getUserInfoByEmail	根据用户邮箱查询用户信息
	 * @param email
	 * @return UserInfo
	 * @exception 
	*/
	public UserInfo getUserInfoByEmail(String email);
	
	/**
	 * 创建一个用户 
	 * @param userInfo
	 * @return
	 */
	public int addUserInfo(UserInfo userInfo);
	
	/**
	 * 修改一个用户 
	 * @param userInfo
	 * @return
	 */
	public int updateUserInfo(UserInfo userInfo);
	
	/**
	 * 删除一个用户
	 * @param userInfo
	 * @return
	 */
	public int deleteUserInfo(UserInfo userInfo);
}