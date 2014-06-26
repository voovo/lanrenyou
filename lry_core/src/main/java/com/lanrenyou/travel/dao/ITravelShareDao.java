package com.lanrenyou.travel.dao;

import com.lanrenyou.travel.model.TravelShare;
import mybatis.framework.core.dao.IValueObjectDao;

public interface ITravelShareDao extends IValueObjectDao<TravelShare> {

	public int insert(TravelShare travelShare);

	public int getCountByTid(int tid);
}