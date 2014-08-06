package com.lanrenyou.admin.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.lanrenyou.admin.dao.IAdminRolePowerDao;
import com.lanrenyou.admin.model.AdminRolePower;
import mybatis.framework.core.dao.BaseDao;
import org.springframework.stereotype.Repository;

@Repository
public class AdminRolePowerDaoImpl extends BaseDao<AdminRolePower> implements IAdminRolePowerDao {

    public AdminRolePowerDaoImpl() {
        super(IAdminRolePowerDao.class.getName());
    }
    
    @Override
	public void deleteByAdminRoleId(int roleId) {
		super.delete("deleteByRoleId", roleId);
	}

	@Override
	public int addAdminRolePowerList(List<AdminRolePower> rolePowerList) {
		if(null == rolePowerList || rolePowerList.size() <= 0){
			return 0;
		}
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("list", rolePowerList);
		return super.doInsertList("insertList", params);
	}
}