package com.lanrenyou.admin.dao.impl;

import com.lanrenyou.admin.dao.IAdminPowerItemDao;
import com.lanrenyou.admin.model.AdminPowerItem;
import mybatis.framework.core.dao.BaseDao;
import org.springframework.stereotype.Repository;

@Repository
public class AdminPowerItemDaoImpl extends BaseDao<AdminPowerItem> implements IAdminPowerItemDao {

    public AdminPowerItemDaoImpl() {
        super(IAdminPowerItemDao.class.getName());
    }
}