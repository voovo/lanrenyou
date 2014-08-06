package com.lanrenyou.admin.dao;

import java.util.List;

import com.lanrenyou.admin.model.AdminRolePower;
import mybatis.framework.core.dao.IValueObjectDao;

public interface IAdminRolePowerDao extends IValueObjectDao<AdminRolePower> {
	
	/**
	 * deleteByAdminRoleId	根据角色ID删除所有的权限
	 * @param adminRoleId void
	 * @exception 
	*/
	public void deleteByAdminRoleId(int adminRoleId);
	
	/**
	 * addAdminRolePowerList	(这里用一句话描述这个方法的作用)
	 * @param adminRolePowerList
	 * @return int
	 * @exception 
	*/
	public int addAdminRolePowerList(List<AdminRolePower> adminRolePowerList);
}