package com.lanrenyou.admin.service.impl;

import java.util.List;

import com.lanrenyou.admin.dao.IAdminPowerItemDao;
import com.lanrenyou.admin.model.AdminPowerItem;
import com.lanrenyou.admin.service.IAdminPowerItemService;
import mybatis.framework.core.service.BaseVOService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdminPowerItemServiceImpl extends BaseVOService<AdminPowerItem> implements IAdminPowerItemService {
    @Autowired
    private IAdminPowerItemDao adminPowerItemDao;
    
    @Override
	public int addAdminPowerItem(AdminPowerItem adminPowerItem) {
		int result = super.doInsert(adminPowerItem);
		return result;
	}

	@Override
	public int updateAdminPowerItem(AdminPowerItem adminPowerItem) {
		int result = super.doUpdateById(adminPowerItem);
		return result;
	}

	@Override
	public int deleteAdminPowerItem(AdminPowerItem adminPowerItem) {
		int result = adminPowerItemDao.deleteByUpdate(adminPowerItem);
		return result;
	}

	@Override
	public List<AdminPowerItem> findAllAdminPowerItem() {
		return super.findAll();
	}

	@Override
	public AdminPowerItem findById(int id) {
		return super.findById(id);
	}
}