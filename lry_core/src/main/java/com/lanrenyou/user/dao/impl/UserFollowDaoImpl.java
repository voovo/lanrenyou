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
}