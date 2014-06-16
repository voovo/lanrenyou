package com.lanrenyou.user.dao.impl;

import com.lanrenyou.user.dao.IUserInfoDao;
import com.lanrenyou.user.model.UserInfo;
import mybatis.framework.core.dao.BaseDao;
import org.springframework.stereotype.Repository;

@Repository
public class UserInfoDaoImpl extends BaseDao<UserInfo> implements IUserInfoDao {

    public UserInfoDaoImpl() {
        super(IUserInfoDao.class.getName());
    }
}