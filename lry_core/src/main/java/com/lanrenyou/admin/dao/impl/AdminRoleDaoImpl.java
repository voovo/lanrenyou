package com.lanrenyou.admin.dao.impl;

import com.lanrenyou.admin.dao.IAdminRoleDao;
import com.lanrenyou.admin.model.AdminRole;
import mybatis.framework.core.dao.BaseDao;
import org.springframework.stereotype.Repository;

@Repository
public class AdminRoleDaoImpl extends BaseDao<AdminRole> implements IAdminRoleDao {

    public AdminRoleDaoImpl() {
        super(IAdminRoleDao.class.getName());
    }
}