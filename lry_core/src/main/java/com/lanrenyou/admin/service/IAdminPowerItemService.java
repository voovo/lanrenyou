package com.lanrenyou.admin.service;

import java.util.List;

import com.lanrenyou.admin.model.AdminPowerItem;
import mybatis.framework.core.service.IValueObjectService;

public interface IAdminPowerItemService extends IValueObjectService<AdminPowerItem> {
	/**
	 * addAdminPowerItem	增加一条权限信息
	 * @param powerItem
	 * @return int
	 * @exception 
	*/
	public int addAdminPowerItem(AdminPowerItem powerItem);
	
	/**
	 * updateAdminPowerItem	修改一条权限信息
	 * @param powerItem
	 * @return int
	 * @exception 
	*/
	public int updateAdminPowerItem(AdminPowerItem powerItem);
	
	/**
	 * deleteAdminPowerItem	删除一条权限信息
	 * @param powerItem
	 * @return int
	 * @exception 
	*/
	public int deleteAdminPowerItem(AdminPowerItem powerItem);
	
	/**
	 * findAllAdminPowerItem	获取所有的权限信息
	 * @return List<AdminPowerItem>
	 * @exception 
	*/
	public List<AdminPowerItem> findAllAdminPowerItem();
	
	/**
	 * findById	根据ID查询权限信息
	 * @param id
	 * @return AdminPowerItem
	 * @exception 
	*/
	public AdminPowerItem findById(int id);
}