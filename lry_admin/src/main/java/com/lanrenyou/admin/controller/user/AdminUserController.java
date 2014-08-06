package com.lanrenyou.admin.controller.user;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import mybatis.framework.core.support.PageIterator;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.lanrenyou.admin.controller.base.BaseController;
import com.lanrenyou.admin.enums.AdminUserStatusEnum;
import com.lanrenyou.admin.model.AdminRole;
import com.lanrenyou.admin.model.AdminUser;
import com.lanrenyou.admin.service.IAdminRoleService;
import com.lanrenyou.admin.service.IAdminUserService;
import com.lanrenyou.util.PasswordUtil;


@Controller
@RequestMapping("/admin/user")
public class AdminUserController extends BaseController {
	
	@Autowired
	private IAdminUserService adminUserService;
	
	@Autowired
	private IAdminRoleService adminRoleService;
	
	@RequestMapping(value="/list")
	public ModelAndView list(
			@RequestParam(value="name",required=false) String name,
			@RequestParam(value="status",required=false, defaultValue="-1") int status,
			@RequestParam(value="roleId",required=false, defaultValue="0") int roleId,
			@RequestParam(value="pageNo",required=false, defaultValue="1") int pageNo,
			@RequestParam(value="pageSize",required=false, defaultValue="20") int pageSize
		){
		ModelAndView mav = new ModelAndView();
		PageIterator<AdminUser> pageIterator = adminUserService.pageQuery(name, status, roleId, pageNo, pageSize);
		mav.addObject("pageIterator", pageIterator);
		List<AdminRole> roleList = adminRoleService.findAllAdminRole();
		if(null != roleList && roleList.size() > 0){
			Map<Integer, String> roleMap = new HashMap<Integer, String>();
			for(AdminRole role : roleList){
				roleMap.put(role.getId(), role.getName());
			}
			mav.addObject("roleMap", roleMap);
		}
		mav.setViewName("/admin/administrator/admin_list");
		return mav;
	}
	
	@RequestMapping(value="/add")
	public ModelAndView add(){
		ModelAndView mav = new ModelAndView();
		mav.setViewName("/admin/administrator/admin_add");
		return mav;
	}
	
	@RequestMapping(value="/addsubmit", method=RequestMethod.POST)
	public ModelAndView addsubmit(AdminUser admin,
			@RequestParam(value="password2",required=true) String password2) {
		ModelAndView mav = new ModelAndView();
		if (null == admin) {
			mav.setViewName("/common/error");
			mav.addObject("errorMsg", "请填写用户信息");
			return mav;
		}
		if (StringUtils.isBlank(admin.getName())) {
			mav.setViewName("/common/error");
			mav.addObject("errorMsg", "请填写用户账户名称");
			return mav;
		}
		if (null == admin.getRoleId() || admin.getRoleId() <= 0) {
			mav.setViewName("/common/error");
			mav.addObject("errorMsg", "请选择角色");
			return mav;
		}
		if (StringUtils.isBlank(admin.getPassword()) || !password2.trim().equals(admin.getPassword())) {
			mav.setViewName("/common/error");
			mav.addObject("errorMsg", "两次输入密码不一致");
			return mav;
		}
		String md5Password = PasswordUtil.encryptAdminPassword(password2.trim());//生成MD5密码
		AdminUser addAdmin = new AdminUser();
		addAdmin.setName(admin.getName().trim());
		addAdmin.setPassword(md5Password);
		addAdmin.setRoleId(admin.getRoleId());
		addAdmin.setLastLoginTime(new Date());
		addAdmin.setLoginNum(0);
		addAdmin.setStatus(AdminUserStatusEnum.NORMAL.getValue());
		addAdmin.setCreateUid(getCurrentAdmin().getId());
		addAdmin.setCreateTime(new Date());
		addAdmin.setCreateIp(getUserIP());
		addAdmin.setUpdateUid(getCurrentAdmin().getId());
		addAdmin.setUpdateIp(getUserIP());
		adminUserService.addAdminUser(addAdmin);
		mav.setViewName("/admin/administrator/admin_list");
		return mav;
	}
	
	@RequestMapping(value="/update")
	public ModelAndView update(){
		ModelAndView mav = new ModelAndView();
		mav.setViewName("/admin/administrator/admin_modify");
		return mav;
	}

}

