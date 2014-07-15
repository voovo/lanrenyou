package com.lanrenyou.travel.service;

import java.util.List;
import java.util.Map;

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
	
	/**
	 * 获取某篇游记的浏览数
	 * @param tid
	 * @return
	 */
	public int getVisitCountByTid(int tid);
	
	/**
	 * 批量获取某些游记的浏览数
	 * @param tidList
	 * @return	Map<游记ID， 浏览数>
	 */
	public Map<Integer, Integer> getVisitCountMapByTidList(List<Integer> tidList);
}