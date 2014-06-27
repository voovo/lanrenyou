package com.lanrenyou.admin.dao.impl;

import com.lanrenyou.admin.dao.IAdminRolePowerDao;
import com.lanrenyou.admin.model.AdminRolePower;
import mybatis.framework.core.dao.BaseDao;
import org.springframework.stereotype.Repository;

@Repository
public class AdminRolePowerDaoImpl extends BaseDao<AdminRolePower> implements IAdminRolePowerDao {

    public AdminRolePowerDaoImpl() {
        super(IAdminRolePowerDao.class.getName());
    }
}