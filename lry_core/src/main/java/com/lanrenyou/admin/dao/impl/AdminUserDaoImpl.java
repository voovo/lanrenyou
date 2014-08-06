package com.lanrenyou.admin.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.lanrenyou.admin.dao.IAdminUserDao;
import com.lanrenyou.admin.model.AdminUser;
import com.lanrenyou.admin.enums.AdminUserStatusEnum;
import mybatis.framework.core.dao.BaseDao;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

@Repository
public class AdminUserDaoImpl extends BaseDao<AdminUser> implements IAdminUserDao {

    public AdminUserDaoImpl() {
        super(IAdminUserDao.class.getName());
    }
    
    @Override
	public int deleteByUpdate(AdminUser admin) {
		return super.doUpdate("deleteByUpdate", admin);
	}

	@Override
	public AdminUser findByName(String name) {
		return (AdminUser) super.findOne("selectByName", name);
	}

	@Override
	public int getQueryCount(String name, int status, int roleId) {
		Map<String, Object> params = new HashMap<String, Object>();
		if(StringUtils.isNotBlank(name)){
			params.put("name", name);
		}
		if(null != AdminUserStatusEnum.valueOf(status)){
			params.put("status", status);
		}
		if(roleId > 0){
			params.put("roleId", roleId);
		}
		return (Integer) findOne("getQueryCount", params);
	}

	@Override
	public List<AdminUser> queryByCondition(String name, int status, int roleId,
			int offset, int limit) {
		Map<String, Object> params = new HashMap<String, Object>();
		if(StringUtils.isNotBlank(name)){
			params.put("name", name);
		}
		if(null != AdminUserStatusEnum.valueOf(status)){
			params.put("status", status);
		}
		if(roleId > 0){
			params.put("roleId", roleId);
		}
		if(offset < 0){
			offset = 0;
		}
		params.put("offset", offset);
		if(limit <= 0){
			limit = 20;
		}
		params.put("limit", limit);
		return findList("queryByCondition", params);
	}
}