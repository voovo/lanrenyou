package com.lanrenyou.controller.user;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletResponse;

import mybatis.framework.core.support.PageIterator;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.lanrenyou.controller.base.BaseController;
import com.lanrenyou.controller.travel.TravelShowUtil;
import com.lanrenyou.search.index.util.SolrUtil;
import com.lanrenyou.travel.service.ITravelContentService;
import com.lanrenyou.travel.service.ITravelInfoService;
import com.lanrenyou.travel.service.ITravelVisitLogService;
import com.lanrenyou.travel.service.ITravelCollectService;
import com.lanrenyou.travel.model.TravelCollect;
import com.lanrenyou.travel.model.TravelContent;
import com.lanrenyou.travel.model.TravelInfo;
import com.lanrenyou.user.enums.UserInfoStatusEnum;
import com.lanrenyou.user.enums.UserPlannerStatusEnum;
import com.lanrenyou.user.model.UserInfo;
import com.lanrenyou.user.model.UserPlanner;
import com.lanrenyou.user.service.IUserFollowService;
import com.lanrenyou.user.service.IUserInfoService;
import com.lanrenyou.user.service.IUserPlannerService;
import com.lanrenyou.util.ServletUtil;
import com.lanrenyou.util.constants.LRYConstant;

@Controller
@RequestMapping("/user/setting")
public class UserSettingController  extends BaseController {
	
	@Autowired
	private IUserInfoService userInfoService;
	
	@Autowired
	private IUserPlannerService userPlannerService;
	
	@RequestMapping("/info")
	public ModelAndView toInfoPage() {
		if(null == this.getLoginUser()){
			return to404();
		}
		ModelAndView mav = new ModelAndView("/user/user_setting_info");
		mav.addObject("userInfo", this.getLoginUser());
		
		UserPlanner userPlanner = userPlannerService.getUserPlannerByUid(this.getLoginUser().getId());
		mav.addObject("userPlanner", userPlanner);
		
		return mav;
	}
	
	@RequestMapping("/infoSubmit")
	public ModelAndView infoSubmit(
			HttpServletResponse response,
            @RequestParam(value = "nickname", required = false, defaultValue = "") String nickname,
            @RequestParam(value = "userIntro", required = false, defaultValue = "") String userIntro,
            @RequestParam(value = "presentAddress", required = false, defaultValue = "") String presentAddress,
            @RequestParam(value = "previousAddress", required = false, defaultValue = "") String previousAddress,
            @RequestParam(value = "toBePlanner", required = false) Integer toBePlanner,
            @RequestParam(value = "targetCity", required = false, defaultValue = "") String targetCity){
		UserInfo userInfo = userInfoService.getUserInfoByUid(this.getLoginUser().getId());
		if(null == userInfo){
			return toError("用户信息不存在");
		}
		boolean hasUpdateUserInfo = false;
		if(StringUtils.isNotBlank(nickname.trim())){
			userInfo.setNickname(nickname.trim());
			hasUpdateUserInfo = true;
		}
		if(StringUtils.isNotBlank(userIntro)){
			userInfo.setUserIntro(userIntro);
			hasUpdateUserInfo = true;
		}
		if(StringUtils.isNotBlank(presentAddress)){
			userInfo.setPresentAddress(presentAddress);
			hasUpdateUserInfo = true;
		}
		if(StringUtils.isNotBlank(previousAddress)){
			userInfo.setPreviousAddress(previousAddress);
			hasUpdateUserInfo = true;
		}
		
		if(hasUpdateUserInfo){
			userInfo.setUpdateUid(this.getLoginUser().getId());
			userInfo.setUpdateIp(this.getRemoteAddr());
			userInfoService.updateUserInfo(userInfo);
		}
		
		if(null != toBePlanner && toBePlanner == 1 && StringUtils.isNotBlank(targetCity)){
			UserPlanner userPlanner = userPlannerService.getUserPlannerByUid(this.getLoginUser().getId());
			userPlanner.setTargetCity(targetCity);
			userPlanner.setStatus(UserPlannerStatusEnum.WAIT_AUDIT.getValue());
			userPlanner.setUpdateUid(this.getLoginUser().getId());
			userPlanner.setUpdateIp(this.getRemoteAddr());
			userPlannerService.updateUserPlanner(userPlanner);
		}
		ModelAndView mav = new ModelAndView("/user/user_setting_info");
		return mav;
	}
	
	@RequestMapping("/passwd")
	public ModelAndView toChangePasswdPage() {
		if(null == this.getLoginUser()){
			return to404();
		}
		ModelAndView mav = new ModelAndView("/user/user_setting_passwd");
		return mav;
	}
	
	@RequestMapping("/changePasswd")
	public ModelAndView changePasswd(
			HttpServletResponse response,
			@RequestParam(value = "old_passwd", required = false, defaultValue = "") String oldPasswd,
            @RequestParam(value = "new_passwd", required = false, defaultValue = "") String newPasswd,
            @RequestParam(value = "new_repasswd", required = false, defaultValue = "") String newRepasswd
			) {
		if(null == this.getLoginUser()){
			return to404();
		}
		
		
		
		
		ServletUtil.deleteCookie(request, response, LRYConstant.AUTH_COOKIE_KEY);
		ModelAndView mav = new ModelAndView();
		mav.setViewName("login/login");
		return mav;
	}
}
