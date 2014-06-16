package com.lanrenyou.user.dao.impl;

import com.lanrenyou.user.dao.IUserFollowDao;
import com.lanrenyou.user.model.UserFollow;
import mybatis.framework.core.dao.BaseDao;
import org.springframework.stereotype.Repository;

@Repository
public class UserFollowDaoImpl extends BaseDao<UserFollow> implements IUserFollowDao {

    public UserFollowDaoImpl() {
        super(IUserFollowDao.class.getName());
    }
}