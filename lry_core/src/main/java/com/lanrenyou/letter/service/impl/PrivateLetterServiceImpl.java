package com.lanrenyou.letter.service.impl;

import java.util.List;

import com.lanrenyou.letter.dao.IPrivateLetterDao;
import com.lanrenyou.letter.enums.PrivateLetterStatusEnum;
import com.lanrenyou.letter.model.PrivateLetter;
import com.lanrenyou.letter.service.IPrivateLetterService;
import mybatis.framework.core.service.BaseVOService;
import mybatis.framework.core.support.PageIterator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PrivateLetterServiceImpl extends BaseVOService<PrivateLetter> implements IPrivateLetterService {
    @Autowired
    private IPrivateLetterDao privateLetterDao;

	@Override
	public PageIterator<PrivateLetter> pageQueryPrivateLetter(int uid,
			int pageNo, int pageSize) {
		int totalCount = privateLetterDao.getPrivateLetterCountByReceiverUid(uid);
		List<PrivateLetter> list = null;
		if(totalCount > 0){
			 list = privateLetterDao.getPrivateLetterByReceiverUid(uid, pageNo, pageSize);
		}
		PageIterator<PrivateLetter> pageIterator = PageIterator.createInstance(pageNo, pageSize, totalCount);
		pageIterator.setData(list);
		return pageIterator;
	}

	@Override
	public PageIterator<PrivateLetter> pageQueryPrivateLetter(int senderUid,
			int receiverUid, int pageNo, int pageSize) {
		int totalCount = privateLetterDao.getPrivateLetterCountByReceiverUidAndSenderUid(senderUid, receiverUid);
		List<PrivateLetter> list = null;
		if(totalCount > 0){
			 list = privateLetterDao.getPrivateLetterByReceiverUidAndSenderUid(senderUid, receiverUid, pageNo, pageSize);
		}
		PageIterator<PrivateLetter> pageIterator = PageIterator.createInstance(pageNo, pageSize, totalCount);
		pageIterator.setData(list);
		return pageIterator;
	}

	@Override
	public int deletePrivateLetter(int privateLetterId) {
		return privateLetterDao.updatePrivateLetterStatus(privateLetterId, PrivateLetterStatusEnum.DELETE.getValue());
	}

	@Override
	public int updatePrivateLetterStatus(int privateLetterId, PrivateLetterStatusEnum statusEnum) {
		return privateLetterDao.updatePrivateLetterStatus(privateLetterId, statusEnum.getValue());
	}
}