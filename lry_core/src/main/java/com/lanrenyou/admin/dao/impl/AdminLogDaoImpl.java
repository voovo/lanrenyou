package com.lanrenyou.admin.dao.impl;

import com.lanrenyou.admin.dao.IAdminLogDao;
import com.lanrenyou.admin.model.AdminLog;
import mybatis.framework.core.dao.BaseDao;
import org.springframework.stereotype.Repository;

@Repository
public class AdminLogDaoImpl extends BaseDao<AdminLog> implements IAdminLogDao {

    public AdminLogDaoImpl() {
        super(IAdminLogDao.class.getName());
    }
}