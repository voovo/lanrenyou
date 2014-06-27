package com.lanrenyou.admin.service.impl;

import com.lanrenyou.admin.dao.IAdminRoleDao;
import com.lanrenyou.admin.model.AdminRole;
import com.lanrenyou.admin.service.IAdminRoleService;
import mybatis.framework.core.service.BaseVOService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdminRoleServiceImpl extends BaseVOService<AdminRole> implements IAdminRoleService {
    @Autowired
    private IAdminRoleDao adminRoleDao;
}