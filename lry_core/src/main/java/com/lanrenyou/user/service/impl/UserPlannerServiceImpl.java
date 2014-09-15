package com.lanrenyou.user.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.lanrenyou.user.dao.IUserPlannerDao;
import com.lanrenyou.user.model.UserPlanner;
import com.lanrenyou.user.service.IUserPlannerService;

import mybatis.framework.core.service.BaseVOService;
import mybatis.framework.core.support.PageIterator;

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

	@Override
	public List<UserPlanner> getUserPlannerListForSearchIndex(Date staratTime,
			Date endTime, int offset, int limit) {
		return userPlannerDao.getListForSearchIndex(staratTime, endTime, offset, limit);
	}

	@Override
	public List<UserPlanner> getUserPlannerListForSearchIndex(Date endTime,
			int offset, int limit) {
		return userPlannerDao.getListForSearchIndex(endTime, offset, limit);
	}

	@Override
	public PageIterator<UserPlanner> pageQueryByUidStatus(int uid, int status,
			int pageNo, int pageSize) {
		if(pageNo <= 0){
			pageNo = 1;
		}
		if(pageSize <= 0 || pageSize > 100){
			pageSize = 10;
		}
		int totalCount = userPlannerDao.getCountByUidStatus(uid, status);
		List<UserPlanner> list = null;
		if(totalCount > 0){
			list = userPlannerDao.getListByUidStatus(uid, status, (pageNo - 1) * pageSize, pageSize);
		}
		PageIterator<UserPlanner> pageIterator = PageIterator.createInstance(pageNo, pageSize, totalCount);
		pageIterator.setData(list);
		return pageIterator;
	}
}