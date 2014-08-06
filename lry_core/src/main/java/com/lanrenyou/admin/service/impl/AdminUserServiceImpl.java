package com.lanrenyou.admin.service.impl;

import java.util.List;

import com.lanrenyou.admin.dao.IAdminUserDao;
import com.lanrenyou.admin.model.AdminUser;
import com.lanrenyou.admin.service.IAdminUserService;
import mybatis.framework.core.service.BaseVOService;
import mybatis.framework.core.support.PageIterator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdminUserServiceImpl extends BaseVOService<AdminUser> implements IAdminUserService {
    @Autowired
    private IAdminUserDao adminUserDao;
    
    @Override
	public int addAdminUser(AdminUser adminUser) {
		int result = super.doInsert(adminUser);
		return result;
	}

	@Override
	public int updateAdminUser(AdminUser adminUser) {
		int result = super.doUpdateById(adminUser);
		return result;
	}

	@Override
	public int deleteAdminUser(AdminUser adminUser) {
		int result = adminUserDao.deleteByUpdate(adminUser);
		return result;
	}

	@Override
	public List<AdminUser> findAllAdminUser() {
		List<AdminUser> adminList = super.findAll();
		return adminList;
	}

	@Override
	public AdminUser findById(int id) {
		AdminUser admin = super.findById(id);
		return admin;
	}

	@Override
	public AdminUser findByName(String name) {
		return adminUserDao.findByName(name);
	}

	@Override
	public PageIterator<AdminUser> pageQuery(String name, int status, int roleId,
			int pageNo, int pageSize) {
		int totalCount = adminUserDao.getQueryCount(name, status, roleId);
		int offset = (pageNo -1) * pageSize;
		List<AdminUser> adminList = adminUserDao.queryByCondition(name, status, roleId, offset, pageSize);
		PageIterator<AdminUser> page = PageIterator.createInstance(pageNo, pageSize, totalCount);
		page.setData(adminList);
		return page;
	}
}