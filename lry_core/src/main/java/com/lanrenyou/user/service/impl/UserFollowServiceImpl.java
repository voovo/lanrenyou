package com.lanrenyou.user.service.impl;

import com.lanrenyou.user.dao.IUserFollowDao;
import com.lanrenyou.user.model.UserFollow;
import com.lanrenyou.user.service.IUserFollowService;
import mybatis.framework.core.service.BaseVOService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserFollowServiceImpl extends BaseVOService<UserFollow> implements IUserFollowService {
    @Autowired
    private IUserFollowDao userFollowDao;
}