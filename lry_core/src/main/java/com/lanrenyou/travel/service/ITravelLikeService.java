package com.lanrenyou.travel.service;

import com.lanrenyou.travel.model.TravelLike;
import mybatis.framework.core.service.IValueObjectService;

public interface ITravelLikeService extends IValueObjectService<TravelLike> {
	
	/**
	 * addTravelLike	加喜欢
	 * 
	 * @param travelLike
	 * @return int
	 * @exception 
	*/
	public int addTravelLike(TravelLike travelLike);
	
	/**
	 * getTravelLikeCntByTid	查询某一游记被喜欢的次数
	 * 
	 * @param tid
	 * @return int
	 * @exception 
	*/
	public int getTravelLikeCntByTid(int tid);
}