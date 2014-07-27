package com.lanrenyou.user.service.impl;

import java.util.List;
import java.util.Map;

import com.lanrenyou.letter.model.PrivateLetter;
import com.lanrenyou.user.dao.IUserFollowDao;
import com.lanrenyou.user.model.UserFollow;
import com.lanrenyou.user.service.IUserFollowService;
import mybatis.framework.core.service.BaseVOService;
import mybatis.framework.core.support.PageIterator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserFollowServiceImpl extends BaseVOService<UserFollow> implements IUserFollowService {
    @Autowired
    private IUserFollowDao userFollowDao;
    
    @Override
	public int addUserFollow(int fansUid, int starUid) {
    	UserFollow uf = new UserFollow();
    	uf.setFansUid(fansUid);
    	uf.setStarUid(starUid);
    	return userFollowDao.insert(uf);
	}

	@Override
	public int removeUserFollow(int fansUid, int starUid) {
		return userFollowDao.deleteByFansUidAndStartUid(fansUid, starUid);
	}

	@Override
	public PageIterator<UserFollow> pageQueryFansByUid(int starUid, int pageNo,
			int pageSize) {
		int totalCount = userFollowDao.getFansCountByUid(starUid);
		List<UserFollow> list = null;
		if(totalCount > 0){
			 list = userFollowDao.getFansByUid(starUid, (pageNo - 1) * pageSize, pageSize);
		}
		PageIterator<UserFollow> pageIterator = PageIterator.createInstance(pageNo, pageSize, totalCount);
		pageIterator.setData(list);
		return pageIterator;
	}

	@Override
	public PageIterator<UserFollow> pageQueryStarByUid(int fansUid, int pageNo,
			int pageSize) {
		int totalCount = userFollowDao.getStarCountByUid(fansUid);
		List<UserFollow> list = null;
		if(totalCount > 0){
			 list = userFollowDao.getStarByUid(fansUid, (pageNo - 1) * pageSize, pageSize);
		}
		PageIterator<UserFollow> pageIterator = PageIterator.createInstance(pageNo, pageSize, totalCount);
		pageIterator.setData(list);
		return pageIterator;
	}

	@Override
	public Map<Integer, Integer> getFansCountMapByUidList(List<Integer> uidList) {
		if(null == uidList || uidList.size() <= 0){
			return null;
		}
		return userFollowDao.getFansCountMapByUidList(uidList);
	}

	@Override
	public Map<Integer, Integer> getStarCountMapByUidList(List<Integer> uidList) {
		if(null == uidList || uidList.size() <= 0){
			return null;
		}
		return userFollowDao.getStarCountMapByUidList(uidList);
	}

	@Override
	public boolean isFollowed(int fansUid, int starUid) {
		return userFollowDao.getByFansUidStarUid(fansUid, starUid);
	}

	@Override
	public int getFansCountByUid(int uid) {
		return userFollowDao.getFansCountByUid(uid);
	}

}