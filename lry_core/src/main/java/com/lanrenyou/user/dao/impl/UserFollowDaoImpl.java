package com.lanrenyou.user.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.lanrenyou.user.dao.IUserFollowDao;
import com.lanrenyou.user.model.UserFollow;
import mybatis.framework.core.dao.BaseDao;
import org.springframework.stereotype.Repository;

@Repository
public class UserFollowDaoImpl extends BaseDao<UserFollow> implements IUserFollowDao {

    public UserFollowDaoImpl() {
        super(IUserFollowDao.class.getName());
    }

	@Override
	public int getFansCountByUid(int starUid) {
		Map<String, Integer> params = new HashMap<String, Integer>();
		params.put("starUid", starUid);
		return (Integer) this.findOne("getFansCountByUid", params);
	}

	@Override
	public List<UserFollow> getFansByUid(int starUid, int offset, int pageSize) {
		Map<String, Integer> params = new HashMap<String, Integer>();
		params.put("starUid", starUid);
		params.put("offset", offset);
		params.put("limit", pageSize);
		return this.findList("getFansByUid", params);
	}

	@Override
	public int getStarCountByUid(int fansUid) {
		Map<String, Integer> params = new HashMap<String, Integer>();
		params.put("fansUid", fansUid);
		return (Integer) this.findOne("getStarCountByUid", params);
	}

	@Override
	public List<UserFollow> getStarByUid(int fansUid, int offset, int pageSize) {
		Map<String, Integer> params = new HashMap<String, Integer>();
		params.put("fansUid", fansUid);
		params.put("offset", offset);
		params.put("limit", pageSize);
		return this.findList("getStarByUid", params);
	}

	@Override
	public int insert(UserFollow uf) {
		return this.doInsert("insert", uf);
	}

	@Override
	public int deleteByFansUidAndStartUid(int fansUid, int starUid) {
		Map<String, Integer> params = new HashMap<String, Integer>();
		params.put("fansUid", fansUid);
		params.put("starUid", starUid);
		return this.doDelete("deleteByFansUidAndStartUid", params);
	}

	@Override
	public Map<Integer, Integer> getFansCountMapByUidList(List<Integer> uidList) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("uidList", uidList);
		List<Map<String, Object>> list = (List<Map<String, Object>>) this.findList("getFansCountMapByUidList", params);
		if(null == list){
			return null;
		}
		Map<Integer, Integer> resMap = new HashMap<Integer, Integer>(list.size());
		for(Map<String, Object> map : list){
			Integer tid = (Integer)map.get("star_uid");
			Long cnt = (Long)map.get("cnt");
			resMap.put(tid, cnt.intValue());
		}
		return resMap;
	}

	@Override
	public Map<Integer, Integer> getStarCountMapByUidList(List<Integer> uidList) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("uidList", uidList);
		List<Map<String, Object>> list = (List<Map<String, Object>>) this.findList("getStarCountMapByUidList", params);
		if(null == list){
			return null;
		}
		Map<Integer, Integer> resMap = new HashMap<Integer, Integer>(list.size());
		for(Map<String, Object> map : list){
			Integer tid = (Integer)map.get("fans_uid");
			Long cnt = (Long)map.get("cnt");
			resMap.put(tid, cnt.intValue());
		}
		return resMap;
	}

	@Override
	public boolean getByFansUidStarUid(int fansUid, int starUid) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("fansUid", fansUid);
		params.put("starUid", starUid);
		UserFollow userFollow = (UserFollow) this.findOne("getByFansUidStarUid", params);
		if(null == userFollow){
			return false;
		} else {
			return true;
		}
	}
}