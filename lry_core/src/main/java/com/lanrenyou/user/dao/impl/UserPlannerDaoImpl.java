package com.lanrenyou.user.dao.impl;

import com.lanrenyou.user.dao.IUserPlannerDao;
import com.lanrenyou.user.model.UserPlanner;
import mybatis.framework.core.dao.BaseDao;
import org.springframework.stereotype.Repository;

@Repository
public class UserPlannerDaoImpl extends BaseDao<UserPlanner> implements IUserPlannerDao {

    public UserPlannerDaoImpl() {
        super(IUserPlannerDao.class.getName());
    }
}