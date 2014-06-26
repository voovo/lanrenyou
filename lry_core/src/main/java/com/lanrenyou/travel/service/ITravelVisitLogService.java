package com.lanrenyou.travel.service;

import com.lanrenyou.travel.model.TravelVisitLog;
import mybatis.framework.core.service.IValueObjectService;

public interface ITravelVisitLogService extends IValueObjectService<TravelVisitLog> {
	
	/**
	 * addTravelVisitLog	增加一条浏览记录
	 * 
	 * @param visitLog
	 * @return int
	 * @exception 
	*/
	public int addTravelVisitLog(TravelVisitLog visitLog);
}