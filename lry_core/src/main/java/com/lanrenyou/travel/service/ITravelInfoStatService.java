package com.lanrenyou.travel.service;

import java.util.List;
import java.util.Map;

import com.lanrenyou.travel.model.TravelInfoStat;
import mybatis.framework.core.service.IValueObjectService;

public interface ITravelInfoStatService extends IValueObjectService<TravelInfoStat> {
	
	/**
	 * addTravelViewCnt	增加一条浏览记录
	 * 
	 * @param tid
	 * @return int
	 * @exception 
	*/
	public int addTravelViewCnt(int tid);
	
	/**
	 * addTravelLikeCnt	增加一条喜欢记录
	 * 
	 * @param tid
	 * @return int
	 * @exception 
	*/
	public int addTravelLikeCnt(int tid);
	
	/**
	 * getTravelInfoStatByTid	获取某一游记的统计信息
	 * 
	 * @param tid
	 * @return TravelInfoStat
	 * @exception 
	*/
	public TravelInfoStat getTravelInfoStatByTid(int tid);
	
	/**
	 * getTravelInfoStatMapByTidList	批量获取某些游记的统计信息
	 * 
	 * @param tidList
	 * @return Map<Integer,TravelInfoStat>
	 * @exception 
	*/
	public Map<Integer, TravelInfoStat> getTravelInfoStatMapByTidList(List<Integer> tidList);
}