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
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public List<TravelCollect> getTravelCollectListByUid(int uid, int offset,
			int limit) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getTravelCollectCountByTid(int tid) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public List<TravelCollect> getTravelCollectListByTid(int tid, int offset,
			int limit) {
		// TODO Auto-generated method stub
		return null;
	}
}