package com.lanrenyou.travel.dao;

import com.lanrenyou.travel.model.TravelLike;
import mybatis.framework.core.dao.IValueObjectDao;

public interface ITravelLikeDao extends IValueObjectDao<TravelLike> {

	public int insert(TravelLike travelLike);

	public int getCountByTid(int tid);
}