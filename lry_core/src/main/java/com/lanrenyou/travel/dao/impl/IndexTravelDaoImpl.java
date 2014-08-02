package com.lanrenyou.travel.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.lanrenyou.travel.dao.IIndexTravelDao;
import com.lanrenyou.travel.dao.ITravelCollectDao;
import com.lanrenyou.travel.model.IndexTravel;
import mybatis.framework.core.dao.BaseDao;
import org.springframework.stereotype.Repository;

@Repository
public class IndexTravelDaoImpl extends BaseDao<IndexTravel> implements IIndexTravelDao {

    public IndexTravelDaoImpl() {
        super(ITravelCollectDao.class.getName());
    }

	@Override
	public int insert(IndexTravel indexTravel) {
		if(null == indexTravel){
			return 0;
		}
		return this.doInsert("insert", indexTravel);
	}

	@Override
	public int deleteByTid(int tid) {
		return this.delete("deleteByTid", tid);
	}

	@Override
	public int deleteById(int id) {
		return this.delete("deleteByPrimaryKey", id);
	}

	@Override
	public int updateIndexTravel(IndexTravel indexTravel) {
		if(null == indexTravel || indexTravel.getId() == null){
			return 0;
		}
		return this.doUpdate("updateByPrimaryKey", indexTravel);
	}

	@Override
	public List<IndexTravel> getIndexTravelList(int offset, int limit) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("offset", offset);
		params.put("limit", limit);
		return findList("getIndexTravelList", params);
	}
}