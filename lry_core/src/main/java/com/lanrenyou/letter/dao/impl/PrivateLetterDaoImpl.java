package com.lanrenyou.letter.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.lanrenyou.letter.dao.IPrivateLetterDao;
import com.lanrenyou.letter.enums.PrivateLetterHasReadEnum;
import com.lanrenyou.letter.enums.PrivateLetterHasReplyEnum;
import com.lanrenyou.letter.model.PrivateLetter;
import mybatis.framework.core.dao.BaseDao;
import org.springframework.stereotype.Repository;

@Repository
public class PrivateLetterDaoImpl extends BaseDao<PrivateLetter> implements IPrivateLetterDao {

    public PrivateLetterDaoImpl() {
        super(IPrivateLetterDao.class.getName());
    }

	@Override
	public int getPrivateLetterCountByReceiverUid(int uid) {
		Map<String, Integer> params = new HashMap<String, Integer>();
		params.put("receiverUid", uid);
		return (Integer) this.findOne("getPrivateLetterCountByReceiverUid", params);
	}

	@Override
	public List<PrivateLetter> getPrivateLetterByReceiverUid(int uid,
			int offset, int limit) {
		if(offset < 0){
			offset = 0;
		}
		if(limit <= 0 || limit >= 500){
			limit = 10;
		}
		Map<String, Integer> params = new HashMap<String, Integer>();
		params.put("receiverUid", uid);
		params.put("offset", offset);
		params.put("limit", limit);
		return this.findList("getPrivateLetterByReceiverUid", params);
	}

	@Override
	public int getPrivateLetterCountByReceiverUidAndSenderUid(int senderUid,
			int receiverUid) {
		Map<String, Integer> params = new HashMap<String, Integer>();
		params.put("senderUid", senderUid);
		params.put("receiverUid", receiverUid);
		return (Integer) this.findOne("getPrivateLetterCountByReceiverUidAndSenderUid", params);
	}

	@Override
	public List<PrivateLetter> getPrivateLetterOfTwoManForUidA(
			int uidA, int uidB) {
		Map<String, Integer> params = new HashMap<String, Integer>();
		params.put("uidA", uidA);
		params.put("uidB", uidB);
		return this.findList("getPrivateLetterOfTwoManForUidA", params);
	}

	@Override
	public int addPrivateLetter(PrivateLetter privateLetter) {
		if(null == privateLetter){
			return 0;
		}
		return this.doInsert("insert", privateLetter);
	}

	@Override
	public int updateHasRead(int privateLetterId) {
		Map<String, Integer> params = new HashMap<String, Integer>();
		params.put("id", privateLetterId);
		return this.doUpdate("updateHasReadById", params);
	}

	@Override
	public int updateHasReply(int privateLetterId) {
		Map<String, Integer> params = new HashMap<String, Integer>();
		params.put("id", privateLetterId);
		return this.doUpdate("updateHasReplyById", params);
	}

	@Override
	public int senderDelete(int privateLetterId) {
		Map<String, Integer> params = new HashMap<String, Integer>();
		params.put("id", privateLetterId);
		return this.doUpdate("senderDeletedById", params);
	}

	@Override
	public int receiverDelete(int privateLetterId) {
		Map<String, Integer> params = new HashMap<String, Integer>();
		params.put("id", privateLetterId);
		return this.doUpdate("receiverDeletedById", params);
	}

	@Override
	public int updateHasRead(List<Integer> privateLetterIdList) {
		if(null == privateLetterIdList || privateLetterIdList.size() <= 0){
			return 0;
		}
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("idList", privateLetterIdList);
		return this.doUpdate("updateHasReadByIdList", params);
	}
}