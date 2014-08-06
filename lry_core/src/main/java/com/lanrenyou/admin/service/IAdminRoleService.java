package com.lanrenyou.admin.service;

import java.util.List;
import java.util.Map;

import com.lanrenyou.admin.model.AdminRole;
import mybatis.framework.core.service.IValueObjectService;
import mybatis.framework.core.support.PageIterator;

public interface IAdminRoleService extends IValueObjectService<AdminRole> {
	/**
	 * addAdminRole	增加一个角色信息
	 * @param adminRole
	 * @return int
	 * @exception 
	*/
	public int addAdminRole(AdminRole adminRole);
	
	/**
	 * updateAdminRole	修改角色信息
	 * @param adminRole
	 * @return int
	 * @exception 
	*/
	public int updateAdminRole(AdminRole adminRole);
	
	/**
	 * deleteAdminRole	删除角色信息
	 * @param adminRole
	 * @return int
	 * @exception 
	*/
	public int deleteAdminRole(AdminRole adminRole);
	
	/**
	 * findAllAdminRole	获取所有的角色信息
	 * @return List<AdminRole>
	 * @exception 
	*/
	public List<AdminRole> findAllAdminRole();
	
	/**
	 * findWithPowerIdListById	根据ID获取角色信息
	 * @param id
	 * @return AdminRole
	 * @exception 
	*/
	public AdminRole findWithPowerIdListById(int id);
	
	/**
	 * findByName	根据名称查询角色
	 * @return AdminRole
	 * @exception 
	*/
	public AdminRole findByName(String name);
	
	/**
	 * pageQueryByCondition	分页查询角色信息
	 * @param params
	 * @param pageNo
	 * @param pageSize
	 * @return PageIterator<AdminRole>
	 * @exception 
	*/
	public PageIterator<AdminRole> pageQueryByCondition(	Map<String, Object> params, int pageNo, int pageSize);
}