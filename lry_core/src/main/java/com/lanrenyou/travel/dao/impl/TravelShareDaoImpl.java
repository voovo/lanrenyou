package com.lanrenyou.travel.dao.impl;

import com.lanrenyou.travel.dao.ITravelShareDao;
import com.lanrenyou.travel.model.TravelShare;
import mybatis.framework.core.dao.BaseDao;
import org.springframework.stereotype.Repository;

@Repository
public class TravelShareDaoImpl extends BaseDao<TravelShare> implements ITravelShareDao {

    public TravelShareDaoImpl() {
        super(ITravelShareDao.class.getName());
    }

	@Override
	public int insert(TravelShare travelShare) {
		if(null == travelShare){
			return 0;
		}
		return this.doInsert("insert", travelShare);
	}

	@Override
	public int getCountByTid(int tid) {
		return (Integer) this.findOne("getCountByTid", tid);
	}
}