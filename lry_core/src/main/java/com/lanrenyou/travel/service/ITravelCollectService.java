package com.lanrenyou.travel.service;

import com.lanrenyou.travel.model.TravelCollect;
import mybatis.framework.core.service.IValueObjectService;
import mybatis.framework.core.support.PageIterator;

public interface ITravelCollectService extends IValueObjectService<TravelCollect> {
	
	/**
	 * addTravelCollect	加收藏
	 * 
	 * @param travelCollect
	 * @return int
	 * @exception 
	*/
	public int addTravelCollect(TravelCollect travelCollect);
	
	/**
	 * deleteByUidTid	取消收藏
	 * 
	 * @param uid
	 * @param tid
	 * @return int
	 * @exception 
	*/
	public int deleteByUidTid(int uid, int tid);
	
	/**
	 * pageQueryTravelCollectByUid	根据用户分页查询其收藏的游记
	 * 
	 * @param uid
	 * @param pageNo
	 * @param pageSize
	 * @return PageIterator<TravelCollect>
	 * @exception 
	*/
	public PageIterator<TravelCollect> pageQueryTravelCollectByUid(int uid, int pageNo, int pageSize);
	
	/**
	 * pageQueryTravelCollectByTid	根据游记分页查询收藏其的用户
	 * 
	 * @param tid
	 * @param pageNo
	 * @param pageSize
	 * @return PageIterator<TravelCollect>
	 * @exception 
	*/
	public PageIterator<TravelCollect> pageQueryTravelCollectByTid(int tid, int pageNo, int pageSize);
}