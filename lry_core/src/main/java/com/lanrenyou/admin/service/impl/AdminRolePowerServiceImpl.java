package com.lanrenyou.admin.service.impl;

import com.lanrenyou.admin.dao.IAdminRolePowerDao;
import com.lanrenyou.admin.model.AdminRolePower;
import com.lanrenyou.admin.service.IAdminRolePowerService;
import mybatis.framework.core.service.BaseVOService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdminRolePowerServiceImpl extends BaseVOService<AdminRolePower> implements IAdminRolePowerService {
    @Autowired
    private IAdminRolePowerDao adminRolePowerDao;
}