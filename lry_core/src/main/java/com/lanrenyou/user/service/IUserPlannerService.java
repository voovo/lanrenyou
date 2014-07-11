package com.lanrenyou.user.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.lanrenyou.user.model.UserPlanner;
import mybatis.framework.core.service.IValueObjectService;

public interface IUserPlannerService extends IValueObjectService<UserPlanner> {
	
	/**
	 * getUserPlannerByUid	根据UID取规划师信息
	 * 
	 * @param uid
	 * @return UserPlanner
	 * @exception 
	*/
	public UserPlanner getUserPlannerByUid(int uid);
	
	/**
	 * getUserPlannerMapByUidList	批量取规划师信息
	 * 
	 * @param uidList
	 * @return Map<Integer,UserPlanner>
	 * @exception 
	*/
	public Map<Integer, UserPlanner> getUserPlannerMapByUidList(List<Integer> uidList);
	
	/**
	 * addUserPlanner	新增一个规划师信息
	 * 
	 * @param userPlanner
	 * @return int
	 * @exception 
	*/
	public int addUserPlanner(UserPlanner userPlanner);
	
	/**
	 * updateUserPlanner	修改规划师信息
	 * 
	 * @param userPlanner
	 * @return int
	 * @exception 
	*/
	public int updateUserPlanner(UserPlanner userPlanner);

	/**
	 * getUserPlannerListForSearchIndex	增量索引使用
	 * 
	 * @param date
	 * @param endTime
	 * @param offset
	 * @param limit
	 * @return List<UserPlanner>
	 * @exception
	 */
	public List<UserPlanner> getUserPlannerListForSearchIndex(Date date, Date endTime, int offset, int limit);

	/**
	 * getUserPlannerListForSearchIndex	全量索引使用
	 * 
	 * @param endTime
	 * @param offset
	 * @param limit
	 * @return List<UserPlanner>
	 * @exception 
	*/
	public List<UserPlanner> getUserPlannerListForSearchIndex(Date endTime, int offset, int limit);
}