package com.lanrenyou.user.service.impl;

import com.lanrenyou.user.dao.IUserPlannerDao;
import com.lanrenyou.user.model.UserPlanner;
import com.lanrenyou.user.service.IUserPlannerService;
import mybatis.framework.core.service.BaseVOService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserPlannerServiceImpl extends BaseVOService<UserPlanner> implements IUserPlannerService {
    @Autowired
    private IUserPlannerDao userPlannerDao;
}