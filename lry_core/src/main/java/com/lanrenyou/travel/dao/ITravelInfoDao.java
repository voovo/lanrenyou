package com.lanrenyou.travel.dao;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.lanrenyou.travel.model.TravelInfo;
import mybatis.framework.core.dao.IValueObjectDao;

public interface ITravelInfoDao extends IValueObjectDao<TravelInfo> {

	/**
	 * insert	插入一条游记信息
	 * 
	 * @param travelInfo
	 * @return int
	 * @exception 
	*/
	public int insert(TravelInfo travelInfo);

	/**
	 * update	更新一条游记信息
	 * 
	 * @param travelInfo
	 * @return int
	 * @exception 
	*/
	public int update(TravelInfo travelInfo);

	/**
	 * update2Delete	将游记标志为删除
	 * 
	 * @param travelInfo
	 * @return int
	 * @exception 
	*/
	public int update2Delete(TravelInfo travelInfo);

	/**
	 * getById	根据游记ID查询游记信息
	 * 
	 * @param tid
	 * @return TravelInfo
	 * @exception 
	*/
	public TravelInfo getById(int tid);

	/**
	 * getByIdList	批量根据游记ID查询游记信息
	 * 
	 * @param tidList
	 * @return List<TravelInfo>
	 * @exception 
	*/
	public List<TravelInfo> getByIdList(List<Integer> tidList);

	/**
	 * getTravelInfoCountByUid	查询某规划师发布的所有有效游记个数
	 * 
	 * @param uid
	 * @return int
	 * @exception 
	*/
	public int getTravelInfoCountByUid(int uid);

	/**
	 * getTravelInfoListByUid	查询某一范围内某规划师发布的游记
	 * 
	 * @param uid
	 * @param offset
	 * @param limit
	 * @return List<TravelInfo>
	 * @exception 
	*/
	public List<TravelInfo> getTravelInfoListByUid(int uid, int offset, int limit);
	
	public int getTravelInfoCountForPlanner(int uid);
	
	public List<TravelInfo> getTravelInfoListForPlanner(int uid, int offset, int limit);

	public Map<Integer, Integer> getPublishedTravelCntMapByUidList(List<Integer> uidList);

	public int getViewCntSumByUid(Integer uid);

	public List<Integer> getViewTravelAuthorId(Date startTime, Date endTime,
			int offset, int limit);

	public int getCountByTidStatus(int tid, int status);

	public List<TravelInfo> getListByTidStatus(int tid, int status, int offset,	int limit);
}