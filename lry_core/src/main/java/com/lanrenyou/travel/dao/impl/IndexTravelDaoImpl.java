package com.lanrenyou.travel.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.lanrenyou.travel.dao.IIndexTravelDao;
import com.lanrenyou.travel.model.IndexTravel;
import mybatis.framework.core.dao.BaseDao;
import org.springframework.stereotype.Repository;

@Repository
public class IndexTravelDaoImpl extends BaseDao<IndexTravel> implements IIndexTravelDao {

    public IndexTravelDaoImpl() {
        super(IIndexTravelDao.class.getName());
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
	public List<IndexTravel> getIndexTravelListByTidSrcType(int tid, Character srcType, int offset, int limit) {
		Map<String, Object> params = new HashMap<String, Object>();
		if(tid > 0){
			params.put("tid", tid);
		}
		if(null != srcType){
			params.put("srcType", srcType);
		}
		params.put("offset", offset);
		params.put("limit", limit);
		return this.findList("getIndexTravelListByTidSrcType", params);
	}

	@Override
	public IndexTravel getByTid(int tid) {
		if(tid <= 0){
			return null;
		}
		return (IndexTravel) this.findOne("getByTid", tid);
	}

	@Override
	public int getCountByTidSrcType(int tid, Character srcType) {
		Map<String, Object> params = new HashMap<String, Object>();
		if(tid > 0){
			params.put("tid", tid);
		}
		if(null != srcType){
			params.put("srcType", srcType);
		}
		return (Integer) this.findOne("getCountByTidSrcType", params);
	}
}