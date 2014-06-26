package com.lanrenyou.travel.dao;

import java.util.List;

import com.lanrenyou.travel.model.TravelCollect;
import mybatis.framework.core.dao.IValueObjectDao;

public interface ITravelCollectDao extends IValueObjectDao<TravelCollect> {

	/**
	 * insert	插入
	 * 
	 * @param travelCollect
	 * @return int
	 * @exception 
	*/
	public int insert(TravelCollect travelCollect);

	/**
	 * deleteByUidTid	删除收藏
	 * 
	 * @param uid
	 * @param tid
	 * @return int
	 * @exception 
	*/
	public int deleteByUidTid(int uid, int tid);

	/**
	 * getTravelCollectCountByUid	根据用户UID查询其所收藏的游记总数
	 * 
	 * @param uid
	 * @return int
	 * @exception 
	*/
	public int getTravelCollectCountByUid(int uid);

	/**
	 * getTravelCollectListByUid	根据用户UID分批查询所收藏的游记
	 * 
	 * @param uid
	 * @param offset
	 * @param limit
	 * @return List<TravelCollect>
	 * @exception 
	*/
	public List<TravelCollect> getTravelCollectListByUid(int uid, int offset,
			int limit);

	/**
	 * getTravelCollectCountByTid	根据游记ID查询收藏其的用户总数
	 * 
	 * @param tid
	 * @return int
	 * @exception 
	*/
	public int getTravelCollectCountByTid(int tid);

	/**
	 * getTravelCollectListByTid	根据游记ID分批查询游记
	 * 
	 * @param tid
	 * @param offset
	 * @param limit
	 * @return List<TravelCollect>
	 * @exception 
	*/
	public List<TravelCollect> getTravelCollectListByTid(int tid, int offset,
			int limit);
}