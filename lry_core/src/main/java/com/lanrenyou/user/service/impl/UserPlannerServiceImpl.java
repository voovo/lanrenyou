package com.lanrenyou.user.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

	@Override
	public UserPlanner getUserPlannerByUid(int uid) {
		return userPlannerDao.getByUid(uid);
	}

	@Override
	public Map<Integer, UserPlanner> getUserPlannerMapByUidList(
			List<Integer> uidList) {
		if(null == uidList || uidList.size() <= 0){
			return null;
		}
		List<UserPlanner> userPlannerList = userPlannerDao.getListByUidList(uidList);
		Map<Integer, UserPlanner> userPlannerMap = new HashMap<Integer, UserPlanner>(userPlannerList.size());
		for(UserPlanner up : userPlannerList){
			userPlannerMap.put(up.getUid(), up);
		}
		return userPlannerMap;
	}

	@Override
	public int addUserPlanner(UserPlanner userPlanner) {
		return userPlannerDao.insert(userPlanner);
	}

	@Override
	public int updateUserPlanner(UserPlanner userPlanner) {
		return userPlannerDao.update(userPlanner);
	}
}