package com.lanrenyou.letter.service;

import java.util.List;

import com.lanrenyou.letter.enums.PrivateLetterHasReadEnum;
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
	 * getPrivateLetterOfTwoManForUidA	为A用户查询A与B之间的私信，过滤掉了UidA删除掉的私信
	 * @param uidA		A
	 * @param uidB	B
	 * @return List<PrivateLetter>
	 * @exception 
	*/
	public List<PrivateLetter> getPrivateLetterOfTwoManForUidA(int uidA, int uidB);
	
	/**
	 * senderDelete	发送者删除私信
	 * @param privateLetterId
	 * @return int
	 * @exception 
	*/
	public int senderDelete(int privateLetterId);
	
	/**
	 * 接收者删除私信
	 * @param privateLetterId
	 * @return
	 */
	public int receiverDelete(int privateLetterId);
	
	/**
	 * addPrivateLetter	新发一条私信
	 * 
	 * @param privateLetter
	 * @return int
	 * @exception 
	*/
	public int addPrivateLetter(PrivateLetter privateLetter);
	
	public int updateHasRead(int privateLetterId) ;
	
	public int updateHasReply(int privateLetterId) ;
	
}