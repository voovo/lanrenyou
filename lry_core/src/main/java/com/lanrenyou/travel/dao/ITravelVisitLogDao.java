package com.lanrenyou.travel.dao;

import com.lanrenyou.travel.model.TravelVisitLog;
import mybatis.framework.core.dao.IValueObjectDao;

public interface ITravelVisitLogDao extends IValueObjectDao<TravelVisitLog> {

	public int insert(TravelVisitLog visitLog);
}