package com.lanrenyou.user.dao;

import java.util.Date;
import java.util.List;

import com.lanrenyou.user.model.UserPlanner;
import mybatis.framework.core.dao.IValueObjectDao;

public interface IUserPlannerDao extends IValueObjectDao<UserPlanner> {

	/**
	 * getByUid	根据UID取规划师信息
	 * 
	 * @param uid
	 * @return UserPlanner
	 * @exception 
	*/
	public UserPlanner getByUid(int uid);

	/**
	 * getListByUidList	根据UID批量取规划师信息
	 * 
	 * @param uidList
	 * @return List<UserPlanner>
	 * @exception 
	*/
	public List<UserPlanner> getListByUidList(List<Integer> uidList);

	/**
	 * insert	添加一个规划师
	 * 
	 * @param userPlanner
	 * @return int
	 * @exception 
	*/
	public int insert(UserPlanner userPlanner);

	/**
	 * update	修改规划师信息
	 * 
	 * @param userPlanner
	 * @return int
	 * @exception 
	*/
	public int update(UserPlanner userPlanner);

	/**
	 * getListForSearchIndex	增量索引查数据用接口
	 * 
	 * @param staratTime
	 * @param endTime
	 * @param offset
	 * @param limit
	 * @return List<UserPlanner>
	 * @exception 
	*/
	public List<UserPlanner> getListForSearchIndex(Date startTime,	Date endTime, int offset, int limit);

	/**
	 * getListForSearchIndex	全量索引查数据用接口
	 * 
	 * @param endTime
	 * @param offset
	 * @param limit
	 * @return List<UserPlanner>
	 * @exception 
	*/
	public List<UserPlanner> getListForSearchIndex(Date endTime, int offset, int limit);
}