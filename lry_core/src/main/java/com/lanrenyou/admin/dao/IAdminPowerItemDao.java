package com.lanrenyou.admin.dao;

import com.lanrenyou.admin.model.AdminPowerItem;
import mybatis.framework.core.dao.IValueObjectDao;

public interface IAdminPowerItemDao extends IValueObjectDao<AdminPowerItem> {
	
	/**
	 * deleteByUpdate	将权限状态修改为删除状态
	 * @param adminPowerItem
	 * @return int
	 * @exception 
	*/
	public int deleteByUpdate(AdminPowerItem adminPowerItem);
}