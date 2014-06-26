package com.lanrenyou.travel.service;

import com.lanrenyou.travel.model.TravelShare;
import mybatis.framework.core.service.IValueObjectService;

public interface ITravelShareService extends IValueObjectService<TravelShare> {
	
	/**
	 * addTravelShare	分享
	 * 
	 * @param travelShare
	 * @return int
	 * @exception 
	*/
	public int addTravelShare(TravelShare travelShare);
	
	/**
	 * getTravelShareCntByTid	查询某一游记被分享的次数
	 * 
	 * @param tid
	 * @return int
	 * @exception 
	*/
	public int getTravelShareCntByTid(int tid);
}