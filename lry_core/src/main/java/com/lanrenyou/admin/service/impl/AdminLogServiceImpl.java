package com.lanrenyou.admin.service.impl;

import com.lanrenyou.admin.dao.IAdminLogDao;
import com.lanrenyou.admin.model.AdminLog;
import com.lanrenyou.admin.service.IAdminLogService;
import mybatis.framework.core.service.BaseVOService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdminLogServiceImpl extends BaseVOService<AdminLog> implements IAdminLogService {
    @Autowired
    private IAdminLogDao adminLogDao;
}