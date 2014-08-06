package com.lanrenyou.admin.dao.impl;

import java.util.List;
import java.util.Map;

import com.lanrenyou.admin.dao.IAdminRoleDao;
import com.lanrenyou.admin.model.AdminRole;
import mybatis.framework.core.dao.BaseDao;
import org.springframework.stereotype.Repository;

@Repository
public class AdminRoleDaoImpl extends BaseDao<AdminRole> implements IAdminRoleDao {

    public AdminRoleDaoImpl() {
        super(IAdminRoleDao.class.getName());
    }
    
    @Override
	public int deleteByUpdate(AdminRole role) {
    	if(null == role || null == role.getId()){
    		return 0;
    	}
		return super.doUpdate("updateToDelById", role);
	}

	@Override
	public int queryCountByCondition(Map<String, Object> params) {
		return (Integer)findOne("pageQueryCount", params);
	}

	@Override
	public List<AdminRole> queryByCondition(Map<String, Object> params) {
		return findList("pageQueryList", params);
	}

	@Override
	public AdminRole findByName(String name) {
		return (AdminRole) findOne("getByName", name);
	}
}