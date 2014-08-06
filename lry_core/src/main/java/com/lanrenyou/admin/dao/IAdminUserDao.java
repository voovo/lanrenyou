package com.lanrenyou.admin.dao;

import java.util.List;

import com.lanrenyou.admin.model.AdminUser;
import mybatis.framework.core.dao.IValueObjectDao;

public interface IAdminUserDao extends IValueObjectDao<AdminUser> {
	
	/**
	 * deleteByUpdate	更新管理员为删除状态
	 * @param adminUser
	 * @return int
	 * @exception 
	*/
	public int deleteByUpdate(AdminUser adminUser);

	/**
	 * findByName	根据名称查询管理员信息
	 * @param name
	 * @return Admin
	 * @exception 
	*/
	public AdminUser findByName(String name);
	
	public int getQueryCount(String name, int status, int roleId);
	
	public List<AdminUser> queryByCondition(String name, int status, int roleId, int offset, int limit);
}