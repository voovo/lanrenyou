package com.lanrenyou.user.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.lanrenyou.user.dao.IUserInfoDao;
import com.lanrenyou.user.model.UserInfo;
import mybatis.framework.core.dao.BaseDao;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

@Repository
public class UserInfoDaoImpl extends BaseDao<UserInfo> implements IUserInfoDao {

    public UserInfoDaoImpl() {
        super(IUserInfoDao.class.getName());
    }

	@Override
	public UserInfo getByUid(int uid) {
		if(uid <= 0){
			return null;
		}
		return findById("selectByPrimaryKey", uid);
	}

	@Override
	public List<UserInfo> getListByUidList(List<Integer> uidList) {
		if(null == uidList || uidList.size() <= 0){
			return null;
		}
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("uidList", uidList);
		return findList("getListByUidList", params);
	}

	@Override
	public UserInfo getByName(String name) {
		if(StringUtils.isBlank(name)){
			return null;
		}
		return (UserInfo) findOne("getByName", name);
	}

	@Override
	public UserInfo getByEmail(String email) {
		if(StringUtils.isBlank(email)){
			return null;
		}
		return (UserInfo) findOne("getByEmail", email);
	}

	@Override
	public int addUserInfo(UserInfo userInfo) {
		return this.doInsert("insert", userInfo);
	}

	@Override
	public int updateUserInfo(UserInfo userInfo) {
		if(null == userInfo || null == userInfo.getId() || userInfo.getId() <= 0){
			return 0;
		}
		return this.doUpdate("updateByPrimaryKey", userInfo);
	}

	@Override
	public int getAllCount() {
		return (Integer) this.findOne("getAllCount", null);
	}
}