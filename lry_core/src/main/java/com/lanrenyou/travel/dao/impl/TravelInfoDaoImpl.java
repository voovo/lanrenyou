package com.lanrenyou.travel.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.lanrenyou.travel.dao.ITravelInfoDao;
import com.lanrenyou.travel.enums.TravelInfoStatusEnum;
import com.lanrenyou.travel.model.TravelInfo;
import mybatis.framework.core.dao.BaseDao;
import org.springframework.stereotype.Repository;

@Repository
public class TravelInfoDaoImpl extends BaseDao<TravelInfo> implements ITravelInfoDao {

    public TravelInfoDaoImpl() {
        super(ITravelInfoDao.class.getName());
    }

	@Override
	public int insert(TravelInfo travelInfo) {
		if(null == travelInfo){
			return 0;
		}
		return this.doInsert("insert", travelInfo);
	}

	@Override
	public int update(TravelInfo travelInfo) {
		if(null == travelInfo || null == travelInfo.getId() || travelInfo.getId() <= 0){
			return 0;
		}
		return this.doUpdate("updateByPrimaryKey", travelInfo);
	}

	@Override
	public int update2Delete(TravelInfo travelInfo) {
		if(null == travelInfo || null == travelInfo.getId() || travelInfo.getId() <= 0){
			return 0;
		}
		travelInfo.setStatus(TravelInfoStatusEnum.DELETE.getValue());
		return this.doUpdate("updateByPrimaryKey", travelInfo);
	}

	@Override
	public TravelInfo getById(int tid) {
		return this.findById("selectByPrimaryKey", tid);
	}

	@Override
	public List<TravelInfo> getByIdList(List<Integer> tidList) {
		if(null == tidList || tidList.size() <= 0){
			return null;
		}
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("tidList", tidList);
		return this.findList("getByIdList", params);
	}

	@Override
	public int getTravelInfoCountByUid(int uid) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("uid", uid);
		return (Integer)this.findOne("getTravelInfoCountByUid", params);
	}

	@Override
	public List<TravelInfo> getTravelInfoListByUid(int uid, int offset,
			int limit) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("uid", uid);
		params.put("offset", offset);
		params.put("limit", limit);
		return this.findList("getTravelInfoListByUid", params);
	}
}