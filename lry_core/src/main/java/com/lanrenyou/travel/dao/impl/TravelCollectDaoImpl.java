package com.lanrenyou.travel.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.lanrenyou.travel.dao.ITravelCollectDao;
import com.lanrenyou.travel.model.TravelCollect;
import mybatis.framework.core.dao.BaseDao;
import org.springframework.stereotype.Repository;

@Repository
public class TravelCollectDaoImpl extends BaseDao<TravelCollect> implements ITravelCollectDao {

    public TravelCollectDaoImpl() {
        super(ITravelCollectDao.class.getName());
    }

	@Override
	public int insert(TravelCollect travelCollect) {
		if(null == travelCollect){
			return 0;
		}
		return this.doInsert("insert", travelCollect);
	}

	@Override
	public int deleteByUidTid(int uid, int tid) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("uid", uid);
		params.put("tid", tid);
		return this.delete("deleteByUidTid", params);
	}

	@Override
	public int getTravelCollectCountByUid(int uid) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("uid", uid);
		return (Integer) findOne("getTravelCollectCountByUid", params);
	}

	@Override
	public List<TravelCollect> getTravelCollectListByUid(int uid, int offset,
			int limit) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("uid", uid);
		params.put("offset", offset);
		params.put("limit", limit);
		return findList("getTravelCollectListByUid", params);
	}

	@Override
	public int getTravelCollectCountByTid(int tid) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("tid", tid);
		return (Integer) findOne("getTravelCollectCountByTid", params);
	}

	@Override
	public List<TravelCollect> getTravelCollectListByTid(int tid, int offset,
			int limit) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("tid", tid);
		params.put("offset", offset);
		params.put("limit", limit);
		return findList("getTravelCollectListByTid", params);
	}

	@Override
	public TravelCollect getCollectByUidTid(int uid, int tid) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("uid", uid);
		params.put("tid", tid);
		return (TravelCollect) findOne("getCollectByUidTid", params);
	}
}