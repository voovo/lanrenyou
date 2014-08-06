package com.lanrenyou.admin.service;

import java.util.List;

import com.lanrenyou.admin.model.AdminUser;
import mybatis.framework.core.service.IValueObjectService;
import mybatis.framework.core.support.PageIterator;

public interface IAdminUserService extends IValueObjectService<AdminUser> {
	/**
	 * addAdminUser	增加一个管理员记录
	 * @param adminUser
	 * @return int
	 * @exception 
	*/
	public int addAdminUser(AdminUser adminUser);
	
	/**
	 * updateAdminUser	修改管理员信息
	 * @param adminUser
	 * @return int
	 * @exception 
	*/
	public int updateAdminUser(AdminUser adminUser);
	
	/**
	 * delAdminUser	删除一条管理员记录
	 * @param adminUser
	 * @return int
	 * @exception 
	*/
	public int deleteAdminUser(AdminUser adminUser);
	
	/**
	 * findAllAdminUser	获取所有的管理员信息
	 * @return List<AdminUser>
	 * @exception 
	*/
	public List<AdminUser> findAllAdminUser();
	
	/**
	 * findById	根据ID获取一条管理员信息
	 * @param id
	 * @return AdminUser
	 * @exception 
	*/
	public AdminUser findById(int id);
	
	/**
	 * findByName	根据名称获取管理员信息
	 * @param name
	 * @return AdminUser
	 * @exception 
	*/
	public AdminUser findByName(String name);
	
	/**
	 * pageQuery	分页查询管理员信息
	 * @param name
	 * @param status
	 * @param roleId
	 * @param page
	 * @param pageSize
	 * @return PageIterator<AdminUser>
	 * @exception 
	*/
	public PageIterator<AdminUser> pageQuery(String name, int status, int roleId, int page, int pageSize);
}