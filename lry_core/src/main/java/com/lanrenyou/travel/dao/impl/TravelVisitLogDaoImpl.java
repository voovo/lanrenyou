package com.lanrenyou.travel.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.lanrenyou.travel.dao.ITravelVisitLogDao;
import com.lanrenyou.travel.model.TravelVisitLog;
import mybatis.framework.core.dao.BaseDao;
import org.springframework.stereotype.Repository;

@Repository
public class TravelVisitLogDaoImpl extends BaseDao<TravelVisitLog> implements ITravelVisitLogDao {

    public TravelVisitLogDaoImpl() {
        super(ITravelVisitLogDao.class.getName());
    }

	@Override
	public int insert(TravelVisitLog visitLog) {
		if(null == visitLog){
			return 0;
		}
		return this.doInsert("insert", visitLog);
	}

	@Override
	public int getCountByTid(int tid) {
		return (Integer) this.findOne("getCountByTid", tid);
	}

	@Override
	public Map<Integer, Integer> getVisitCntMapByTidList(List<Integer> tidList) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("tidList", tidList);
		List<Map<String, Object>> list = (List<Map<String, Object>>) this.findList("getVisitCntMapByTidList", params);
		if(null == list){
			return null;
		}
		Map<Integer, Integer> resMap = new HashMap<Integer, Integer>(list.size());
		for(Map<String, Object> map : list){
			Integer tid = (Integer)map.get("tid");
			Long cnt = (Long)map.get("cnt");
			resMap.put(tid, cnt.intValue());
		}
		return resMap;
	}
}