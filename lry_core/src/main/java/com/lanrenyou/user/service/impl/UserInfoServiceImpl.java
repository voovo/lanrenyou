package com.lanrenyou.user.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.lanrenyou.user.dao.IUserInfoDao;
import com.lanrenyou.user.enums.UserInfoStatusEnum;
import com.lanrenyou.user.model.UserInfo;
import com.lanrenyou.user.service.IUserInfoService;
import mybatis.framework.core.service.BaseVOService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserInfoServiceImpl extends BaseVOService<UserInfo> implements IUserInfoService {
    @Autowired
    private IUserInfoDao userInfoDao;

	@Override
	public UserInfo getUserInfoByUid(int uid) {
		return userInfoDao.getByUid(uid);
	}

	@Override
	public Map<Integer, UserInfo> getUserInfoListByUidList(List<Integer> uidList) {
		List<UserInfo> userInfoList = userInfoDao.getListByUidList(uidList);
		if(null == userInfoList || userInfoList.size() <= 0){
			return null;
		}
		Map<Integer, UserInfo> userInfoMap = new HashMap<Integer, UserInfo>(userInfoList.size());
		for(UserInfo ui : userInfoList){
			userInfoMap.put(ui.getId(), ui);
		}
		return userInfoMap;
	}

	@Override
	public UserInfo getUserInfoByName(String name) {
		return userInfoDao.getByName(name);
	}

	@Override
	public UserInfo getUserInfoByEmail(String email) {
		return userInfoDao.getByEmail(email);
	}

	@Override
	public int addUserInfo(UserInfo userInfo) {
		return userInfoDao.addUserInfo(userInfo);
	}

	@Override
	public int updateUserInfo(UserInfo userInfo) {
		return userInfoDao.updateUserInfo(userInfo);
	}

	@Override
	public int deleteUserInfo(UserInfo userInfo) {
		userInfo.setStatus(UserInfoStatusEnum.DELETE.getValue());
		return userInfoDao.updateUserInfo(userInfo);
	}
}