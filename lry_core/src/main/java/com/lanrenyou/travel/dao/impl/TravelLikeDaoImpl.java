package com.lanrenyou.travel.dao.impl;

import com.lanrenyou.travel.dao.ITravelLikeDao;
import com.lanrenyou.travel.model.TravelLike;
import mybatis.framework.core.dao.BaseDao;
import org.springframework.stereotype.Repository;

@Repository
public class TravelLikeDaoImpl extends BaseDao<TravelLike> implements ITravelLikeDao {

    public TravelLikeDaoImpl() {
        super(ITravelLikeDao.class.getName());
    }

	@Override
	public int insert(TravelLike travelLike) {
		if(null == travelLike){
			return 0;
		}
		return this.doInsert("insert", travelLike);
	}

	@Override
	public int getCountByTid(int tid) {
		return (Integer) this.findOne("getCountByTid", tid);
	}
}