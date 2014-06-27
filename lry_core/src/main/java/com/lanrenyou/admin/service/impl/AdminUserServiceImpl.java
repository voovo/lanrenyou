package com.lanrenyou.admin.service.impl;

import com.lanrenyou.admin.dao.IAdminUserDao;
import com.lanrenyou.admin.model.AdminUser;
import com.lanrenyou.admin.service.IAdminUserService;
import mybatis.framework.core.service.BaseVOService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdminUserServiceImpl extends BaseVOService<AdminUser> implements IAdminUserService {
    @Autowired
    private IAdminUserDao adminUserDao;
}