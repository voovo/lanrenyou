package com.lanrenyou.letter.dao;

import java.util.List;

import com.lanrenyou.letter.model.PrivateLetter;
import mybatis.framework.core.dao.IValueObjectDao;

public interface IPrivateLetterDao extends IValueObjectDao<PrivateLetter> {

	/**
	 * getPrivateLetterCountByReceiverUid	查询收到的所有私信数量
	 * @param uid	收信人UID
	 * @return int
	 * @exception 
	*/
	public int getPrivateLetterCountByReceiverUid(int uid);

	/**
	 * getPrivateLetterByReceiverUid	查询收到的所有私信
	 * @param uid	收信人UID
	 * @param offset
	 * @param limit
	 * @return List<PrivateLetter>
	 * @exception 
	*/
	public List<PrivateLetter> getPrivateLetterByReceiverUid(int uid, int offset, int limit);

	/**
	 * getPrivateLetterCountByReceiverUidAndSenderUid	查询receiverUid收到的和senderUid的私信数量
	 * @param senderUid		发信人UID
	 * @param receiverUid	收信人UID
	 * @return int
	 * @exception 
	*/
	public int getPrivateLetterCountByReceiverUidAndSenderUid(int senderUid, int receiverUid);

	/**
	 * getPrivateLetterByReceiverUidAndSenderUid	查询receiverUid收到的和senderUid的私信
	 * @param senderUid		发信人UID
	 * @param receiverUid	收信人UID
	 * @param offset
	 * @param limit
	 * @return List<PrivateLetter>
	 * @exception 
	*/
	public List<PrivateLetter> getPrivateLetterByReceiverUidAndSenderUid(int senderUid, int receiverUid, int offset, int limit);

	/**
	 * addPrivateLetter	创建一条私信
	 * 
	 * @param privateLetter
	 * @return int
	 * @exception 
	*/
	public int addPrivateLetter(PrivateLetter privateLetter);

	public int updateHasRead(int privateLetterId);

	public int updateHasReply(int privateLetterId);

	public int senderDelete(int privateLetterId);

	public int receiverDelete(int privateLetterId);
}