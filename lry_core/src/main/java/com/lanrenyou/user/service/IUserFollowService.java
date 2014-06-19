package com.lanrenyou.user.service;

import com.lanrenyou.user.model.UserFollow;
import mybatis.framework.core.service.IValueObjectService;
import mybatis.framework.core.support.PageIterator;

public interface IUserFollowService extends IValueObjectService<UserFollow> {
	
	/**
	 * addUserFollow	用户添加关注
	 * @param fansUid
	 * @param starUid
	 * @return int
	 * @exception 
	*/
	public int addUserFollow(int fansUid, int starUid);
	
	/**
	 * removeUserFollow	用户取消关注
	 * @param fansUid
	 * @param starUid
	 * @return int
	 * @exception 
	*/
	public int removeUserFollow(int fansUid, int starUid);
	
	
	/**
	 * pageQueryFansByUid	查询关注我的用户
	 * @param starUid
	 * @param pageNo
	 * @param pageSize
	 * @return PageIterator<UserFollow>
	 * @exception 
	*/
	public PageIterator<UserFollow> pageQueryFansByUid(int starUid, int pageNo, int pageSize);
	
	
	/**
	 * pageQueryStarByUid	查找我关注的用户
	 * @param fansUid
	 * @param pageNo
	 * @param pageSize
	 * @return PageIterator<UserFollow>
	 * @exception 
	*/
	public PageIterator<UserFollow> pageQueryStarByUid(int fansUid, int pageNo, int pageSize);
}