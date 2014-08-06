package com.lanrenyou.admin.dao;

import java.util.List;
import java.util.Map;

import com.lanrenyou.admin.model.AdminRole;
import mybatis.framework.core.dao.IValueObjectDao;

public interface IAdminRoleDao extends IValueObjectDao<AdminRole> {
	
public int deleteByUpdate(AdminRole role);
	
	/**
	 * queryCountByCondition	分页查询时，查询总数量
	 * @param params
	 * @return int
	 * @exception 
	*/
	public int queryCountByCondition(Map<String, Object> params);

	/**
	 * queryByCondition	分页查询时，查询每页的数据
	 * @param params
	 * @return List<AdminRole>
	 * @exception 
	*/
	public List<AdminRole> queryByCondition(Map<String, Object> params);
	
	/**
	 * findByName	根据角色名称获取角色信息
	 * @param name
	 * @return AdminRole
	 * @exception 
	*/
	public AdminRole findByName(String name);
}