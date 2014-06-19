package com.lanrenyou.letter.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.lanrenyou.letter.dao.IPrivateLetterDao;
import com.lanrenyou.letter.enums.PrivateLetterStatusEnum;
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
	public List<PrivateLetter> getPrivateLetterByReceiverUidAndSenderUid(
			int senderUid, int receiverUid, int offset, int limit) {
		if(offset < 0){
			offset = 0;
		}
		if(limit <= 0 || limit >= 500){
			limit = 10;
		}
		Map<String, Integer> params = new HashMap<String, Integer>();
		params.put("senderUid", senderUid);
		params.put("receiverUid", receiverUid);
		params.put("offset", offset);
		params.put("limit", limit);
		return this.findList("getPrivateLetterByReceiverUidAndSenderUid", params);
	}

	@Override
	public int updatePrivateLetterStatus(int privateLetterId, int value) {
		Map<String, Integer> params = new HashMap<String, Integer>();
		params.put("id", privateLetterId);
		if(value == PrivateLetterStatusEnum.HAS_READ.getValue()){
			return this.doUpdate("updateHasReadById", params);
		} else if (value == PrivateLetterStatusEnum.HAS_REPLY.getValue()) {
			return this.doUpdate("updateHasReplyById", params);
		} else if (value == PrivateLetterStatusEnum.DELETE.getValue()) {
			return this.doUpdate("updateIsDeletedById", params);
		}
		return 0;
	}

	@Override
	public int addPrivateLetter(PrivateLetter privateLetter) {
		if(null == privateLetter){
			return 0;
		}
		return this.doInsert("insert", privateLetter);
	}
}