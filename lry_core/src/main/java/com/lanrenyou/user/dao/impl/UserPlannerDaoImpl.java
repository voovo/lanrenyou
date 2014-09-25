package com.lanrenyou.user.dao.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.lanrenyou.user.dao.IUserPlannerDao;
import com.lanrenyou.user.model.UserPlanner;

import mybatis.framework.core.dao.BaseDao;

import org.springframework.stereotype.Repository;

@Repository
public class UserPlannerDaoImpl extends BaseDao<UserPlanner> implements IUserPlannerDao {

    public UserPlannerDaoImpl() {
        super(IUserPlannerDao.class.getName());
    }

	@Override
	public UserPlanner getByUid(int uid) {
		return (UserPlanner) this.findOne("getByUid", uid);
	}

	@Override
	public List<UserPlanner> getListByUidList(List<Integer> uidList) {
		if(null == uidList || uidList.size() <= 0){
			return null;
		}
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("uidList", uidList);
		return this.findList("getListByUidList", params);
	}

	@Override
	public int insert(UserPlanner userPlanner) {
		if(null == userPlanner){
			return 0;
		}
		return this.doInsert("insert", userPlanner);
	}

	@Override
	public int update(UserPlanner userPlanner) {
		if(null == userPlanner || null == userPlanner.getUid() || userPlanner.getUid() <= 0){
			return 0;
		}
		return this.doUpdate("updateByPrimaryKey", userPlanner);
	}

	@Override
	public List<UserPlanner> getListForSearchIndex(Date startTime,
			Date endTime, int offset, int limit) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("startTime", startTime);
		params.put("endTime", endTime);
		params.put("offset", offset);
		params.put("limit", limit);
		return this.findList("getListForAddIndex", params);
	}

	@Override
	public List<UserPlanner> getListForSearchIndex(Date endTime, int offset,
			int limit) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("endTime", endTime);
		params.put("offset", offset);
		params.put("limit", limit);
		return this.findList("getListForFullIndex", params);
	}

	@Override
	public int getCountByUidStatus(int uid, int status) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("uid", uid);
		params.put("status", status);
		return (Integer) this.findOne("getCountByUidStatus", params);
	}

	@Override
	public List<UserPlanner> getListByUidStatus(int uid, int status,
			int offset, int limit) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("uid", uid);
		params.put("status", status);
		params.put("offset", offset);
		params.put("limit", limit);
		return this.findList("getListByUidStatus", params);
	}
}