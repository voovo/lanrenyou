package com.lanrenyou.admin.controller.role;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import mybatis.framework.core.support.PageIterator;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.util.HtmlUtils;

import com.lanrenyou.admin.controller.base.BaseController;
import com.lanrenyou.admin.enums.AdminRoleStatusEnum;
import com.lanrenyou.admin.model.AdminRole;
import com.lanrenyou.admin.service.IAdminPowerItemService;
import com.lanrenyou.admin.service.IAdminRoleService;
import com.lanrenyou.util.PasswordUtil;


@Controller
@RequestMapping("/admin/role")
public class AdminRoleController extends BaseController {
	
	@Autowired
	private IAdminRoleService adminRoleService;
	
	@Autowired
	private IAdminPowerItemService adminPowerItemService;
	
	@RequestMapping(value="/role_list")
	public ModelAndView roleList(
			@RequestParam(value="queryname",required=false) String name,
			@RequestParam(value="querystatus",required=false, defaultValue="-1") int status,
			@RequestParam(value="pageNo",required=false, defaultValue="1") int pageNo,
			@RequestParam(value="pageSize",required=false, defaultValue="20") int pageSize
		){
		ModelAndView mav = new ModelAndView();
		
		Map<String, Object> queryConditionMap = new HashMap<String, Object>();
		queryConditionMap.put("queryname", name);
		queryConditionMap.put("querystatus", status);
		mav.addObject("queryConditionMap", queryConditionMap);
		
		Map<String, Object> params = new HashMap<String, Object>();
		if(StringUtils.isNotBlank(name)){
			params.put("name", name.trim());
		}
		if(status > 0){
			params.put("status", status);
		}
		PageIterator<AdminRole> pageIterator = adminRoleService.pageQueryByCondition(params, pageNo, pageSize);
		mav.addObject("pageIterator", pageIterator);
		mav.setViewName("/admin/role/role_list");
		return mav;
	}
	
	@RequestMapping(value="/role_add")
	public ModelAndView roleAdd(){
		ModelAndView mav = new ModelAndView();
		mav.setViewName("/admin/role/role_add");
		return mav;
	}
	
	@RequestMapping(value="/role_addsubmit", method=RequestMethod.POST)
	public ModelAndView roleAddsubmit(
			@RequestParam(value="name",required=true) String name,
			@RequestParam(value="powerItemId",required=true) String powerItemId
			) {
		ModelAndView mav = new ModelAndView();
		AdminRole role = new AdminRole();
		name = HtmlUtils.htmlEscape(name.trim());
		role.setName(name);
		String[] powerItemArray = powerItemId.split(",");
		Set<Integer> powerItemSet = new HashSet<Integer>();
		for(String pi : powerItemArray){
			powerItemSet.add(Integer.parseInt(pi));
		}
		role.setAdminPowerItemList(new ArrayList<Integer>(powerItemSet));
		role.setStatus(AdminRoleStatusEnum.NORMAL.getValue());
		role.setCreateUid(getCurrentAdmin().getId());
		role.setCreateTime(new Date());
		role.setCreateIp(getUserIP());
		role.setUpdateUid(getCurrentAdmin().getId());
		role.setUpdateIp(getUserIP());
		AdminRole hasRole = adminRoleService.findByName(name);
		if(null != hasRole){
			mav.setViewName("/common/error");
			mav.addObject("errorMsg", "该角色名已经存在，请重新选择新的角色名称");
			return mav;
		}
		adminRoleService.addAdminRole(role);
		mav.setViewName("forward:/admin/role/role_list");
		return mav;
	}
	
	@RequestMapping(value="/role_update")
	public ModelAndView roleUpdate(
			@RequestParam(value="id",required=true) int id
		){
		ModelAndView mav = new ModelAndView();
		AdminRole role = adminRoleService.findWithPowerIdListById(id);
		if(null != role){
			List<Integer> powerItemList = role.getAdminPowerItemList();
			Map<String, Integer> powerItemIdMap = new HashMap<String, Integer>();
			for(Integer powerItemId : powerItemList){
				powerItemIdMap.put(String.valueOf(powerItemId), 1);
			}
			mav.addObject("role", role);
			mav.addObject("powerMap", powerItemIdMap);
		}
		mav.setViewName("/admin/role/role_modify");
		return mav;
	}
	
	@RequestMapping(value="/role_updatesubmit", method=RequestMethod.POST)
	public ModelAndView roleUpdateSubmit(
			@RequestParam(value="id",required=true) int id,
			@RequestParam(value="name",required=true) String name,
			@RequestParam(value="powerItemId",required=true) String powerItemId
			) {
		ModelAndView mav = new ModelAndView();
		AdminRole role = adminRoleService.findWithPowerIdListById(id);
		if(null != role){
			name = HtmlUtils.htmlEscape(name.trim());
			role.setName(name);
			String[] powerItemArray = powerItemId.split(",");
			Set<Integer> powerItemSet = new HashSet<Integer>();
			for(String pi : powerItemArray){
				powerItemSet.add(Integer.parseInt(pi));
			}
			role.setAdminPowerItemList(new ArrayList<Integer>(powerItemSet));
			role.setUpdateUid(getCurrentAdmin().getId());
			role.setUpdateIp(getUserIP());
			AdminRole hasRole = adminRoleService.findByName(name);
			if(null != hasRole && hasRole.getId().intValue() != role.getId().intValue()){
				mav.setViewName("/common/error");
				mav.addObject("errorMsg", "该角色名已经存在，请重新选择新的角色名称");
				return mav;
			}
			adminRoleService.updateAdminRole(role);
		}
		mav.setViewName("forward:/admin/role/role_list");
		return mav;
	}
	
	@RequestMapping(value="/role_enable")
	public ModelAndView roleEnable(
			@RequestParam(value="id",required=true) int id
		){
		ModelAndView mav = new ModelAndView();
		
		AdminRole role = adminRoleService.findById(id);
		if(null != role){
			role.setStatus(AdminRoleStatusEnum.NORMAL.getValue());
			role.setUpdateUid(getCurrentAdmin().getId());
			role.setUpdateIp(getUserIP());
			adminRoleService.doUpdateById(role);
		}
		
		mav.setViewName("forward:/admin/role/role_list");
		return mav;
	}
	
	@RequestMapping(value="/role_pause")
	public ModelAndView rolePause(
			@RequestParam(value="id",required=true) int id
		){
		ModelAndView mav = new ModelAndView();
		
		AdminRole role = adminRoleService.findById(id);
		if(null != role){
			role.setStatus(AdminRoleStatusEnum.STOP.getValue());
			role.setUpdateUid(getCurrentAdmin().getId());
			role.setUpdateIp(getUserIP());
			adminRoleService.doUpdateById(role);
		}
		
		mav.setViewName("forward:/admin/role/role_list");
		return mav;
	}
	
	@RequestMapping(value="/role_del")
	public ModelAndView roleDelete(
			@RequestParam(value="id",required=true) int id
		){
		ModelAndView mav = new ModelAndView();
		
		AdminRole role = adminRoleService.findById(id);
		if(null != role){
			role.setStatus(-1);
			role.setUpdateUid(getCurrentAdmin().getId());
			role.setUpdateIp(getUserIP());
			adminRoleService.doUpdateById(role);
		}
		
		mav.setViewName("forward:/admin/role/role_list");
		return mav;
	}

}

