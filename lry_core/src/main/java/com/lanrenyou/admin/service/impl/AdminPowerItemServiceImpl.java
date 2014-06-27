package com.lanrenyou.admin.service.impl;

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
}