package com.lanrenyou.letter.service.impl;

import java.util.List;

import com.lanrenyou.letter.dao.IPrivateLetterDao;
import com.lanrenyou.letter.enums.PrivateLetterHasReadEnum;
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
			 list = privateLetterDao.getPrivateLetterByReceiverUid(uid, (pageNo - 1) * pageSize, pageSize);
		}
		PageIterator<PrivateLetter> pageIterator = PageIterator.createInstance(pageNo, pageSize, totalCount);
		pageIterator.setData(list);
		return pageIterator;
	}

	@Override
	public List<PrivateLetter> getPrivateLetterOfTwoManForUidA(int uidA, int uidB) {
		List<PrivateLetter> list = privateLetterDao.getPrivateLetterOfTwoManForUidA(uidA, uidB);
		return list;
	}

	@Override
	public int updateHasRead(int privateLetterId) {
		return privateLetterDao.updateHasRead(privateLetterId);
	}
	
	@Override
	public int updateHasReply(int privateLetterId) {
		return privateLetterDao.updateHasReply(privateLetterId);
	}
	
	@Override
	public int addPrivateLetter(PrivateLetter privateLetter) {
		return privateLetterDao.addPrivateLetter(privateLetter);
	}

	@Override
	public int senderDelete(int privateLetterId) {
		return privateLetterDao.senderDelete(privateLetterId);
	}

	@Override
	public int receiverDelete(int privateLetterId) {
		return privateLetterDao.receiverDelete(privateLetterId);
	}

	@Override
	public int updateHasRead(List<Integer> privateLetterIdList) {
		return privateLetterDao.updateHasRead(privateLetterIdList);
	}

	@Override
	public PageIterator<PrivateLetter> pageQueryUnReadLetterCountByReceiverUid(int receiverUid, int pageNo, int pageSize) {
		int totalCount = privateLetterDao.getUnReadLetterCountByReceiverUid(receiverUid);
		List<PrivateLetter> list = null;
		if(totalCount > 0){
			 list = privateLetterDao.getUnReadLetterListByReceiverUid(receiverUid, (pageNo - 1) * pageSize, pageSize);
		}
		PageIterator<PrivateLetter> pageIterator = PageIterator.createInstance(pageNo, pageSize, totalCount);
		pageIterator.setData(list);
		return pageIterator;
	}
}