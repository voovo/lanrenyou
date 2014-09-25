package com.lanrenyou.admin.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.lanrenyou.admin.dao.IAdminRoleDao;
import com.lanrenyou.admin.dao.IAdminRolePowerDao;
import com.lanrenyou.admin.model.AdminRole;
import com.lanrenyou.admin.model.AdminRolePower;
import com.lanrenyou.admin.service.IAdminRoleService;
import mybatis.framework.core.service.BaseVOService;
import mybatis.framework.core.support.PageIterator;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdminRoleServiceImpl extends BaseVOService<AdminRole> implements IAdminRoleService {
    @Autowired
    private IAdminRoleDao adminRoleDao;
    
    @Autowired
    private IAdminRolePowerDao adminRolePowerDao;

	@Override
	public int addAdminRole(AdminRole adminRole) {
		if(null == adminRole){
			return -1;
		}
		if(StringUtils.isBlank(adminRole.getName())){
			return -2;
		}
		if(null == adminRole.getCreateUid() || adminRole.getCreateUid() <= 0){
			return -3;
		}
		if(StringUtils.isBlank(adminRole.getCreateIp())){
			return -4;
		}
		int result = super.doInsert(adminRole);
		if(result > 0){
			List<Integer> powerItemIdList = adminRole.getAdminPowerItemList();
			if(null != powerItemIdList && powerItemIdList.size() > 0){
				List<AdminRolePower> rolePowerList = new ArrayList<AdminRolePower>(powerItemIdList.size());
				for(Integer powerItemId : powerItemIdList){
					AdminRolePower rolePower = new AdminRolePower();
					rolePower.setRoleId(adminRole.getId());
					rolePower.setPowerId(powerItemId);
					rolePower.setCreateUid(adminRole.getCreateUid());
					rolePower.setCreateTime(new Date());
					rolePower.setCreateIp(adminRole.getCreateIp());
					rolePowerList.add(rolePower);
				}
				adminRolePowerDao.addAdminRolePowerList(rolePowerList);
			}
		}
		return result;
	}

	@Override
	public int updateAdminRole(AdminRole adminRole) {
		if(null == adminRole){
			return -1;
		}
		if(StringUtils.isBlank(adminRole.getName())){
			return -2;
		}
		if(null == adminRole.getUpdateUid() || adminRole.getUpdateUid() <= 0){
			return -3;
		}
		if(StringUtils.isBlank(adminRole.getUpdateIp())){
			return -4;
		}
		int result = super.doUpdateById(adminRole);
		adminRolePowerDao.deleteByAdminRoleId(adminRole.getId());
		if(result > 0){
			List<Integer> powerItemIdList = adminRole.getAdminPowerItemList();
			if(null != powerItemIdList && powerItemIdList.size() > 0){
				List<AdminRolePower> rolePowerList = new ArrayList<AdminRolePower>(powerItemIdList.size());
				for(Integer powerItemId : powerItemIdList){
					AdminRolePower rolePower = new AdminRolePower();
					rolePower.setRoleId(adminRole.getId());
					rolePower.setPowerId(powerItemId);
					rolePower.setCreateUid(adminRole.getUpdateUid());
					rolePower.setCreateTime(new Date());
					rolePower.setCreateIp(adminRole.getUpdateIp());
					rolePowerList.add(rolePower);
				}
				adminRolePowerDao.addAdminRolePowerList(rolePowerList);
			}
		}
		return result;
	}

	@Override
	public int deleteAdminRole(AdminRole role) {
		return adminRoleDao.deleteByUpdate(role);
	}

	@Override
	public List<AdminRole> findAllAdminRole() {
		return super.findAll();
	}

	@Override
	public AdminRole findWithPowerIdListById(int id) {
		AdminRole role = super.findById(id);
		List<Integer> powerIdList = adminRolePowerDao.findList("selectByRoleId", role.getId());
		role.setAdminPowerItemList(powerIdList);
		return role;
	}
	
	@Override
	public AdminRole findByName(String name){
		return adminRoleDao.findByName(name);
	}
	
	@Override
	public PageIterator<AdminRole> pageQueryByCondition(
			Map<String, Object> params, int pageNo, int pageSize) {
		int totalCount = adminRoleDao.queryCountByCondition(params);
		int offset = (pageNo -1) * pageSize;
		params.put("offset", offset);
		params.put("limit", pageSize);
		List<AdminRole> list = adminRoleDao.queryByCondition(params);
		PageIterator<AdminRole> page = PageIterator.createInstance(pageNo, pageSize, totalCount);
		page.setData(list);
		return page;
	}
}