package com.lanrenyou.letter.service;

import com.lanrenyou.letter.enums.PrivateLetterStatusEnum;
import com.lanrenyou.letter.model.PrivateLetter;
import mybatis.framework.core.service.IValueObjectService;
import mybatis.framework.core.support.PageIterator;

public interface IPrivateLetterService extends IValueObjectService<PrivateLetter> {
	
	/**
	 * pageQueryPrivateLetter	分页查询用户私信
	 * @param uid	用户UID
	 * @param pageNo
	 * @param pageSize
	 * @return PageIterator<PrivateLetter>
	 * @exception 
	*/
	public PageIterator<PrivateLetter> pageQueryPrivateLetter(int uid, int pageNo, int pageSize);
	
	/**
	 * pageQueryPrivateLetter	分页查询某人与某人之间的私信
	 * @param senderUid		发信人
	 * @param receiverUid	收信人
	 * @param pageNo
	 * @param pageSize
	 * @return PageIterator<PrivateLetter>
	 * @exception 
	*/
	public PageIterator<PrivateLetter> pageQueryPrivateLetter(int senderUid, int receiverUid, int pageNo, int pageSize);
	
	/**
	 * deletePrivateLetter	删除私信
	 * @param privateLetterId
	 * @return int
	 * @exception 
	*/
	public int deletePrivateLetter(int privateLetterId);
	
	/**
	 * updatePrivateLetterStatus	更新私信的状态
	 * @param statusEnum
	 * @return int
	 * @exception 
	*/
	public int updatePrivateLetterStatus(int privateLetterId, PrivateLetterStatusEnum statusEnum);
	
	/**
	 * addPrivateLetter	新发一条私信
	 * 
	 * @param privateLetter
	 * @return int
	 * @exception 
	*/
	public int addPrivateLetter(PrivateLetter privateLetter);
}