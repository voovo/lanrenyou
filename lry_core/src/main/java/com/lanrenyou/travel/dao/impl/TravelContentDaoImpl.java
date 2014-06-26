package com.lanrenyou.travel.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.lanrenyou.travel.dao.ITravelContentDao;
import com.lanrenyou.travel.model.TravelContent;
import com.sun.jmx.trace.Trace;

import mybatis.framework.core.dao.BaseDao;
import org.springframework.stereotype.Repository;

@Repository
public class TravelContentDaoImpl extends BaseDao<TravelContent> implements ITravelContentDao {

    public TravelContentDaoImpl() {
        super(ITravelContentDao.class.getName());
    }

	@Override
	public int insert(TravelContent travelContent) {
		if(null == travelContent){
			return 0;
		}
		return this.doInsert("insert", travelContent);
	}

	@Override
	public int update(TravelContent travelContent) {
		if(null == travelContent || null == travelContent.getId() || travelContent.getId() <= 0){
			return 0;
		}
		return this.doUpdate("updateByPrimaryKey", travelContent);
	}

	@Override
	public TravelContent getByTid(int tid) {
		Map<String, Object> params = new HashMap<String, Object>(1);
		params.put("tid", tid);
		return (TravelContent) this.findOne("getByTid", params);
	}

	@Override
	public List<TravelContent> getByTidList(List<Integer> tidList) {
		Map<String, Object> params = new HashMap<String, Object>(1);
		params.put("tidList", tidList);
		return this.findList("getByTidList", params);
	}
}