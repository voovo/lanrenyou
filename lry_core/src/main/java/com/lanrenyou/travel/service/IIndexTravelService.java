package com.lanrenyou.travel.service;

import java.util.List;

import com.lanrenyou.travel.model.IndexTravel;
import mybatis.framework.core.service.IValueObjectService;
import mybatis.framework.core.support.PageIterator;

public interface IIndexTravelService extends IValueObjectService<IndexTravel> {
	
	/**
	 * addIndexTravel	将游记加入首页
	 * 
	 * @param indexTravel
	 * @return int
	 * @exception 
	*/
	public int addIndexTravel(IndexTravel indexTravel);
	
	/**
	 * deleteByTid	根据游记ID取消首页
	 * 
	 * @param uid
	 * @param tid
	 * @return int
	 * @exception 
	*/
	public int deleteByTid(int tid);
	
	/**
	 * deleteById	根据游记ID取消首页
	 * 
	 * @param id
	 * @return int
	 * @exception 
	*/
	public int deleteById(int id);
	
	/**
	 * @param indexTravel
	 * @return
	 */
	public int updateIndexTravel(IndexTravel indexTravel);
	
	public IndexTravel getByTid(int tid);

	public PageIterator<IndexTravel> pageQueryByTidSrcType(int tid, Character srcType, int pageNo, int pageSize);
	
}