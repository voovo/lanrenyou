package com.lanrenyou.travel.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.lanrenyou.travel.model.TravelContent;

import mybatis.framework.core.service.IValueObjectService;

public interface ITravelContentService extends IValueObjectService<TravelContent> {
	
	/**
	 * addTravelContent	添加游记详细信息
	 * 
	 * @param travelContent
	 * @return int
	 * @exception 
	*/
	public int addTravelContent(TravelContent travelContent);
	
	/**
	 * updateTravelContent	修改游记详细信息
	 * 
	 * @param travelContent
	 * @return int
	 * @exception 
	*/
	public int updateTravelContent(TravelContent travelContent);
	
	/**
	 * getTravelContentByTid	根据游记ID查询游记详细信息
	 * 
	 * @param tid
	 * @return TravelContent
	 * @exception 
	*/
	public TravelContent getTravelContentByTid(int tid);
	
	/**
	 * getTravelContentListByTidList	批量根据游记ID查询游记详细信息
	 * 
	 * @param tidList
	 * @return List<TravelContent>
	 * @exception 
	*/
	public List<TravelContent> getTravelContentListByTidList(List<Integer> tidList);
	
	/**
	 * getTravelContentMapByTidList	批量根据游记ID查询游记详细信息，返回值为Map
	 * 
	 * @param tidList
	 * @return Map<Integer,TravelContent>
	 * @exception 
	*/
	public Map<Integer, TravelContent> getTravelContentMapByTidList(List<Integer> tidList);

	/**
	 * 增加同步索引使用接口
	 * @param startTime
	 * @param endTime
	 * @param offset
	 * @param limit
	 * @return
	 */
	public List<TravelContent> getTravelContentListForSearchIndex(Date startTime, Date endTime, int offset, int limit);

	/**
	 * 全量同步索引使用接口
	 * @param endTime
	 * @param offset
	 * @param limit
	 * @return
	 */
	public List<TravelContent> getTravelContentListForSearchIndex(Date endTime, int offset, int limit);
}