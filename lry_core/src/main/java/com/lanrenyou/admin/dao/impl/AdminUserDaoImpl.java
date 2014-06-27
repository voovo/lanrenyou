package com.lanrenyou.admin.dao.impl;

import com.lanrenyou.admin.dao.IAdminUserDao;
import com.lanrenyou.admin.model.AdminUser;
import mybatis.framework.core.dao.BaseDao;
import org.springframework.stereotype.Repository;

@Repository
public class AdminUserDaoImpl extends BaseDao<AdminUser> implements IAdminUserDao {

    public AdminUserDaoImpl() {
        super(IAdminUserDao.class.getName());
    }
}