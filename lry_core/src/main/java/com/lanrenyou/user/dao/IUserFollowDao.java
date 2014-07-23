package com.lanrenyou.user.dao;

import java.util.List;
import java.util.Map;

import com.lanrenyou.user.model.UserFollow;
import mybatis.framework.core.dao.IValueObjectDao;
import mybatis.framework.core.support.PageIterator;

public interface IUserFollowDao extends IValueObjectDao<UserFollow> {
	
	/**
	 * getFansCountByUid	获取关注我的人数
	 * @param starUid
	 * @return int
	 * @exception 
	*/
	public int getFansCountByUid(int starUid);
	
	/**
	 * getFansByUid	批量获取粉丝
	 * @param starUid
	 * @param offset
	 * @param pageSize
	 * @return List<UserFollow>
	 * @exception 
	*/
	public List<UserFollow> getFansByUid(int starUid, int offset, int pageSize);
	
	/**
	 * getStarCountByUid	获取我关注的人数
	 * @param fansUid
	 * @return int
	 * @exception 
	*/
	public int getStarCountByUid(int fansUid);
	
	/**
	 * getStarByUid	批量获取我关注的人
	 * @param fansUid
	 * @param offset
	 * @param pageSize
	 * @return List<UserFollow>
	 * @exception 
	*/
	public List<UserFollow> getStarByUid(int fansUid, int offset, int pageSize);

	/**
	 * insert	创建一条关注
	 * @param uf
	 * @return int
	 * @exception 
	*/
	public int insert(UserFollow uf);

	/**
	 * deleteByFansUidAndStartUid	删除关注
	 * @param fansUid
	 * @param starUid
	 * @return int
	 * @exception 
	*/
	public int deleteByFansUidAndStartUid(int fansUid, int starUid);

	public Map<Integer, Integer> getFansCountMapByUidList(List<Integer> uidList);

	public Map<Integer, Integer> getStarCountMapByUidList(List<Integer> uidList);

	/**
	 * 判断是否关注了某用户
	 * @param fansUid
	 * @param starUid
	 * @return
	 */
	public boolean getByFansUidStarUid(int fansUid, int starUid);
	
}