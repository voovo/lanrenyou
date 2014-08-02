package com.lanrenyou.travel.dao;

import java.util.List;

import com.lanrenyou.travel.model.IndexTravel;
import mybatis.framework.core.dao.IValueObjectDao;

public interface IIndexTravelDao extends IValueObjectDao<IndexTravel> {

	/**
	 * insert	插入
	 * 
	 * @param indexTravel
	 * @return int
	 * @exception 
	*/
	public int insert(IndexTravel indexTravel);

	/**
	 * deleteByTid	删除首页中的游记
	 * 
	 * @param tid
	 * @return int
	 * @exception 
	*/
	public int deleteByTid(int tid);
	
	/**
	 * deleteById	删除首页中的游记
	 * 
	 * @param id
	 * @return int
	 * @exception 
	*/
	public int deleteById(int id);
	
	/**
	 * 更新首页游记排序
	 * @param indexTravel
	 * @return
	 */
	public int updateIndexTravel(IndexTravel indexTravel);

	/**
	 * getIndexTravelList	根据用户UID分批查询所收藏的游记
	 * 
	 * @param offset
	 * @param limit
	 * @return List<IndexTravel>
	 * @exception 
	*/
	public List<IndexTravel> getIndexTravelList(int offset, int limit);

}