package com.lanrenyou.admin.controller.login;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.LocaleEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.support.RequestContextUtils;

import com.lanrenyou.admin.AdminConstrants;
import com.lanrenyou.admin.controller.base.BaseController;
import com.lanrenyou.admin.model.AdminRole;
import com.lanrenyou.admin.model.AdminUser;
import com.lanrenyou.admin.service.IAdminRoleService;
import com.lanrenyou.admin.service.IAdminUserService;
import com.lanrenyou.util.PasswordUtil;
import com.lanrenyou.util.SessionUtil;


@Controller
@RequestMapping("/admin/login")
public class AdminLoginController extends BaseController {
	
	@Autowired
	private IAdminUserService adminUserService;
	
	@Autowired
	private IAdminRoleService adminRoleService;
	
	@RequestMapping(value="")
	public ModelAndView login(){
		ModelAndView mav = new ModelAndView();
		mav.setViewName("/admin/login/login");
		return mav;
	}
	
	@RequestMapping(value="/submit", method=RequestMethod.POST)
	public ModelAndView loginSubmit(
			@RequestParam(value = "userName", required = true) String userName,
			@RequestParam(value = "password", required = true) String password,
			//@RequestParam(value = "code", required = true) String code,
			@RequestParam(value = "redir",required = false) String redir,
			HttpServletRequest request,
            HttpServletResponse response) {
		//TODO 校验验证码,暂时没有
		ModelAndView mav = new ModelAndView();
		AdminUser admin = adminUserService.findByName(userName);
		if (admin == null) {
			mav.setViewName("/admin/login/login");
			return mav;
		}
		logger.info("the administrator logined by name:{}",userName);
		String MD5Password = PasswordUtil.encryptAdminPassword(password.trim());//生成MD5密码
		if (StringUtils.isBlank(admin.getPassword()) || !admin.getPassword().equals(MD5Password)) {
			mav.setViewName("/admin/login/login");
			return mav;
		}
		Date lastLoginTime = admin.getUpdateTime();
		admin.setLastLoginTime(lastLoginTime);
		int loginNum = (null == admin.getLoginNum()? 0: admin.getLoginNum());
		admin.setLoginNum(loginNum + 1);
		admin.setUpdateUid(admin.getId());
		admin.setUpdateIp(getUserIP());
		adminUserService.updateAdminUser(admin);
		mav.setViewName("redirect:/admin/index");
		mav.addObject("adminUser", admin);
		
		// 获取用户权限信息，并放入session中
		AdminRole role = adminRoleService.findWithPowerIdListById(admin.getRoleId());
		super.setLoginSession(request, admin);
		logger.info("query role info:{}", gson.toJson(role));
		List<Integer> powerIdList = role.getAdminPowerItemList();
		Map<Integer, String> powerItemMap = (Map<Integer, String>) request.getSession().getServletContext().getAttribute(AdminConstrants.POWER_ITEM_MAP);
		if(null != powerItemMap){
			List<String> powerUrlList = new ArrayList<String>(powerIdList.size());
			for(Integer powerId : powerIdList){
				powerUrlList.add(powerItemMap.get(powerId));
			}
			request.getSession().setAttribute(AdminConstrants.ADMINISTRATOR_POWER_URL_LIST, powerUrlList);
		}
		return mav;
	}
	
	@RequestMapping(value="/logout")
	public ModelAndView logout(){
		ModelAndView mav = new ModelAndView();
		super.removeLoginSession(request);
		mav.setViewName("/admin/login/login");
		return mav;
	}
	
	@RequestMapping(value="/to_modify_password")
	public ModelAndView toModifyPasswordPage(){
		ModelAndView mav = new ModelAndView();
		mav.setViewName("/admin/administrator/admin_modify_password");
		return mav;
	}

}

