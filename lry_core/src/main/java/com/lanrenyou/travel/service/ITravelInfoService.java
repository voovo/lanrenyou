package com.lanrenyou.travel.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.lanrenyou.travel.model.TravelInfo;
import mybatis.framework.core.service.IValueObjectService;
import mybatis.framework.core.support.PageIterator;

public interface ITravelInfoService extends IValueObjectService<TravelInfo> {
	
	/**
	 * addTravelInfo	创建一条游记信息
	 * 
	 * @param travelInfo
	 * @return int
	 * @exception 
	*/
	public int addTravelInfo(TravelInfo travelInfo);
	
	/**
	 * updateTravelInfo	修改游记信息
	 * 
	 * @param travelInfo
	 * @return int
	 * @exception 
	*/
	public int updateTravelInfo(TravelInfo travelInfo);
	
	/**
	 * deleteTravelInfo	删除游记信息
	 * 
	 * @param travelInfo	其中id, update_uid, update_ip 字段是必须赋值
	 * @return int
	 * @exception 
	*/
	public int deleteTravelInfo(TravelInfo travelInfo);
	
	/**
	 * getTravelInfoById	根据ID获得游记信息
	 * 
	 * @param tid
	 * @return TravelInfo
	 * @exception 
	*/
	public TravelInfo getTravelInfoById(int tid);
	
	/**
	 * getTravelInfoByIdList	批量根据ID获取游记信息
	 * 
	 * @param tidList
	 * @return List<TravelInfo>
	 * @exception 
	*/
	public List<TravelInfo> getTravelInfoByIdList(List<Integer> tidList);
	
	/**
	 * getTravelInfoMapByTidList	批量根据ID获取游记信息， 返回值为Map
	 * 
	 * @param tidList
	 * @return Map<Integer,TravelInfo>
	 * @exception 
	*/
	public Map<Integer, TravelInfo> getTravelInfoMapByTidList(List<Integer> tidList);
	
	/**
	 * pageQueryTravelInfoByUid	分页查询规划师的游记
	 * 
	 * @param uid
	 * @param pageNo
	 * @param pageSize
	 * @return PageIterator<TravelInfo>
	 * @exception 
	*/
	public PageIterator<TravelInfo> pageQueryTravelInfoByUid(int uid, int pageNo, int pageSize);

	/**
	 * 增加同步索引使用接口
	 * @param startTime
	 * @param endTime
	 * @param offset
	 * @param limit
	 * @return
	 */
	public List<TravelInfo> getTravelInfoListForSearchIndex(Date startTime, Date endTime, int offset, int limit);

	/**
	 * 全量同步索引使用接口
	 * @param endTime
	 * @param offset
	 * @param limit
	 * @return
	 */
	public List<TravelInfo> getTravelInfoListForSearchIndex(Date endTime, int offset, int limit);
	
	/**
	 * 批量获取某些人发布过的游记数量
	 * @param uidList
	 * @return
	 */
	public Map<Integer, Integer> getPublishedTravelCntMapByUidList(List<Integer> uidList);
	
}