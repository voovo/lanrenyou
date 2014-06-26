package com.lanrenyou.travel.dao;

import java.util.List;

import com.lanrenyou.travel.model.TravelContent;
import mybatis.framework.core.dao.IValueObjectDao;

public interface ITravelContentDao extends IValueObjectDao<TravelContent> {

	/**
	 * insert	插入
	 * 
	 * @param travelContent
	 * @return int
	 * @exception 
	*/
	public int insert(TravelContent travelContent);

	/**
	 * update	修改
	 * 
	 * @param travelContent
	 * @return int
	 * @exception 
	*/
	public int update(TravelContent travelContent);

	/**
	 * getByTid	根据游记ID查询游记内容
	 * 
	 * @param tid
	 * @return TravelContent
	 * @exception 
	*/
	public TravelContent getByTid(int tid);

	/**
	 * getByTidList	批量根据游记ID查询游记内容
	 * 
	 * @param tidList
	 * @return List<TravelContent>
	 * @exception 
	*/
	public List<TravelContent> getByTidList(List<Integer> tidList);
}