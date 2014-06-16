package com.lanrenyou.user.service.impl;

import com.lanrenyou.user.dao.IUserInfoDao;
import com.lanrenyou.user.model.UserInfo;
import com.lanrenyou.user.service.IUserInfoService;
import mybatis.framework.core.service.BaseVOService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserInfoServiceImpl extends BaseVOService<UserInfo> implements IUserInfoService {
    @Autowired
    private IUserInfoDao userInfoDao;
}