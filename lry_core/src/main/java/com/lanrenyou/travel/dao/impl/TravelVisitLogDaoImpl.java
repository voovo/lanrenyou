package com.lanrenyou.travel.dao.impl;

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
}