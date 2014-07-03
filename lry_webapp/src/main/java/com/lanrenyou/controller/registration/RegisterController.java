package com.lanrenyou.controller.registration;

import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.lanrenyou.config.AppConfigs;
import com.lanrenyou.controller.base.BaseController;
import com.lanrenyou.user.enums.UserInfoStatusEnum;
import com.lanrenyou.user.enums.UserInfoTypeEnum;
import com.lanrenyou.user.enums.UserPlannerStatusEnum;
import com.lanrenyou.user.model.UserInfo;
import com.lanrenyou.user.model.UserPlanner;
import com.lanrenyou.user.service.IUserInfoService;
import com.lanrenyou.user.service.IUserPlannerService;
import com.lanrenyou.util.AesCryptUtil;
import com.lanrenyou.util.MailUtil;
import com.lanrenyou.util.ServletUtil;
import com.lanrenyou.util.constants.UserConstant;

@Controller
@RequestMapping("/regist")
public class RegisterController extends BaseController {
	
	@Autowired
	private IUserInfoService userInfoService;
	
	@Autowired
	private IUserPlannerService userPlannerService;
	
	private static final String REGIST_COOKIE_NAME = "regist_wait_verify_uid";
	
	@RequestMapping("/toPage")
	public ModelAndView toPage(HttpServletResponse response){
		UserInfo userInfo = getLoginUser();
		if(null != userInfo){
			ServletUtil.clearUserCookie(request, response);
		}
		ModelAndView mav = new ModelAndView("/regist/regist");
		return mav;
	}
	
	@RequestMapping(value="/checkEmail")
	@ResponseBody
	public String checkEmail(
			@RequestParam(value = "email") String submitEmail){
		Map<String, Object> map = new HashMap<String, Object>();
		if(StringUtils.isBlank(submitEmail)){
			map.put("code", 0);
			map.put("msg", "请输入邮箱");
			return gson.toJson(map);
		}
		
		UserInfo userInfo = userInfoService.getUserInfoByEmail(submitEmail);
		if(null != userInfo){
			map.put("code", 0);
			map.put("msg", "邮箱已被占用");
			return gson.toJson(map);
		} else {
			map.put("code", 1);
			map.put("msg", "恭喜，邮箱可使用");
			return gson.toJson(map);
		}
	}
	
	@RequestMapping(value="/submit", method=RequestMethod.POST)
	@ResponseBody
	public String submit(
			HttpServletResponse response,
			@RequestParam(value = "email") String submitEmail,
            @RequestParam(value = "userPass") String submitPassword,
            @RequestParam(value = "userPass2") String submitPassword2,
            @RequestParam(value = "captcha", required = false, defaultValue = "") String captcha){
		UserInfo userInfo = getLoginUser();
		if(null != userInfo){
			ServletUtil.clearUserCookie(request, response);
		}
		submitEmail = submitEmail.trim();
		Map<String, Object> map = new HashMap<String, Object>();
		if(StringUtils.isBlank(submitEmail)){
			map.put("code", 0);
			map.put("msg", "请输入邮箱");
			return gson.toJson(map);
		}
		if(StringUtils.isBlank(submitPassword) || StringUtils.isBlank(submitPassword2)){
			map.put("code", 0);
			map.put("msg", "密码不得为空");
			return gson.toJson(map);
		}
		if(!submitPassword.equals(submitPassword2)){
			map.put("code", 0);
			map.put("msg", "两次密码不一致");
			return gson.toJson(map);
		}
		if(StringUtils.isBlank(captcha)){
			map.put("code", 0);
			map.put("msg", "验证码不得为空");
			return gson.toJson(map);
		}
		if(captcha.equals(request.getSession().getAttribute("captchaValue"))){
			map.put("code", 0);
			map.put("msg", "验证码不正确");
			return gson.toJson(map);
		}
		
		UserInfo newUserInfo = new UserInfo();
		newUserInfo.setEmail(submitEmail);
		newUserInfo.setUserPass(submitPassword);
		newUserInfo.setStatus(UserInfoStatusEnum.WAIT_VERIFY_EMAIL.getValue());
		newUserInfo.setUserType(UserInfoTypeEnum.NOMAL.getValue());
		newUserInfo.setCreateUid(0);
		newUserInfo.setCreateTime(new Date());
		newUserInfo.setCreateIp(this.getRemoteAddr());
		int result = userInfoService.addUserInfo(newUserInfo);
		if(result > 0){
			try {
				StringBuilder code = new StringBuilder(newUserInfo.getId());
				code.append("|").append(System.currentTimeMillis());
				String encryptCode = AesCryptUtil.encrypt(code.toString(), AppConfigs.getInstance().get("REGIST_VERIFY_EMAIL_KEY"));
				int mailResult = MailUtil.sendEmail(submitEmail, "请您验证懒人游注册邮箱", "请在两小时内点击以下链接完成账号激活：\n <a href=\"http://www.lanrenyou.com/regist/verifyEmail?code="+encryptCode+"\" target=\"_blank\">"+"http://www.lanrenyou.com/regist/verifyEmail?code="+encryptCode+"</a>");	
				if(mailResult <= 0){
					map.put("code", 0);
					map.put("msg", "验证邮件发送失败");
					return gson.toJson(map);
				}
			} catch (UnsupportedEncodingException e) {
				logger.error("{}", e);
			}
			request.setAttribute(UserConstant.LOGIN_USER, newUserInfo);
			Cookie cookie = new Cookie(REGIST_COOKIE_NAME, String.valueOf(newUserInfo.getId()));
			cookie.setMaxAge(7200);
			cookie.setPath("/regist");
			cookie.setDomain("www.lanrenyou.com");
			response.addCookie(cookie);
			map.put("code", 1);
			map.put("msg", "创建成功，请验证邮箱");
			return gson.toJson(map);
		} else {
			map.put("code", 0);
			map.put("msg", "创建失败，请稍后重试");
			return gson.toJson(map);
		}
	}
	
	@RequestMapping(value="/waitEmailVerify")
	public ModelAndView toWaitEmailVerifyPage(){
		Cookie[] cookieArray = request.getCookies();
		int uid = 0;
		for(Cookie c : cookieArray){
			if (c.getName().equals(REGIST_COOKIE_NAME)) {
				uid = Integer.parseInt(c.getValue());
			}
		}
		if(uid <= 0){
			return toError("未获取到用户信息");
		}
		UserInfo userInfo = userInfoService.getUserInfoByUid(uid);
		if(null == userInfo){
			return toError("未获取到用户信息");
		}
		request.setAttribute(UserConstant.LOGIN_USER, userInfo);
		ModelAndView mav = new ModelAndView("/regist/regist_mail");
		return mav;
	}
	
	@RequestMapping(value="/sendVerifyEmail")
	@ResponseBody
	public String sendVerifyEmail(){
		Map<String, Object> map = new HashMap<String, Object>();
		Cookie[] cookieArray = request.getCookies();
		int uid = 0;
		for(Cookie c : cookieArray){
			if (c.getName().equals(REGIST_COOKIE_NAME)) {
				uid = Integer.parseInt(c.getValue());
			}
		}
		if(uid <= 0){
			map.put("code", 0);
			map.put("msg", "未获取到用户信息");
			return gson.toJson(map);
		}
		UserInfo userInfo = userInfoService.getUserInfoByUid(uid);
		if(null == userInfo){
			map.put("code", 0);
			map.put("msg", "未获取到用户信息");
			return gson.toJson(map);
		}
		
		if(userInfo.getStatus() == UserInfoStatusEnum.WAIT_VERIFY_EMAIL.getValue()){
			try {
				StringBuilder code = new StringBuilder(userInfo.getId());
				code.append("|").append(System.currentTimeMillis());
				String encryptCode = AesCryptUtil.encrypt(code.toString(), AppConfigs.getInstance().get("REGIST_VERIFY_EMAIL_KEY"));
				int mailResult = MailUtil.sendEmail(userInfo.getEmail(), "请您验证懒人游注册邮箱", "请在两小时内点击以下链接完成账号激活：\n <a href=\"http://www.lanrenyou.com/regist/verifyEmail?code="+encryptCode+"\" target=\"_blank\">"+"http://www.lanrenyou.com/regist/verifyEmail?code="+encryptCode+"</a>");	
				if(mailResult <= 0){
					map.put("code", 0);
					map.put("msg", "验证邮件发送失败");
					return gson.toJson(map);
				} else {
					map.put("code", 1);
					map.put("msg", "验证邮件发送成功");
					return gson.toJson(map);
				}
			} catch (UnsupportedEncodingException e) {
				logger.error("{}", e);
				map.put("code", 0);
				map.put("msg", "验证邮件发送失败，请稍后重试");
				return gson.toJson(map);
			}
		} else {
			map.put("code", 0);
			map.put("msg", "用户已经完成邮箱验证");
			return gson.toJson(map);
		}
	}
	
	@RequestMapping(value="/verifyEmail")
	public ModelAndView verifyEmail(
			@RequestParam(value = "code") String code){
		if(StringUtils.isBlank(code)){
			return toError("校验码为空");
		}
		
		String decrptCode = AesCryptUtil.decrypt(code, AppConfigs.getInstance().get("REGIST_VERIFY_EMAIL_KEY"));
		if(StringUtils.isBlank(decrptCode)){
			return toError("校验码错误");
		}
		String[] array = decrptCode.split("|");
		if(array.length != 2){
			return toError("校验码错误");
		}
		int uid = Integer.parseInt(array[0]);
		long createTime = Long.parseLong(array[1]);
		if(System.currentTimeMillis() - createTime > 7200000){
			return toError("校验码失效");
		}
		UserInfo userInfo = userInfoService.getUserInfoByUid(uid);
		if(null == userInfo){
			return toError("没有此用户信息");
		}
		if(userInfo.getStatus() == UserInfoStatusEnum.WAIT_VERIFY_EMAIL.getValue()){
			userInfo.setStatus(UserInfoStatusEnum.VERIFIED_EMAIL_WAIT_COMPLATE_INFO.getValue());
			userInfo.setUpdateUid(uid);
			userInfo.setUpdateIp(request.getRemoteAddr());
			userInfoService.updateUserInfo(userInfo);
			request.setAttribute(UserConstant.LOGIN_USER, userInfo);
		}
		ModelAndView mav = new ModelAndView("/regist/regist_form");
		return mav;
	}
	
	@RequestMapping(value="/submitInfo")
	@ResponseBody
	public String submitUserInfo(
			HttpServletResponse response,
			@RequestParam(value = "uid", required = true) Integer uid,
            @RequestParam(value = "nickname", required = false, defaultValue = "") String nickname,
            @RequestParam(value = "userIntro", required = false, defaultValue = "") String userIntro,
            @RequestParam(value = "presentAddress", required = false, defaultValue = "") String presentAddress,
            @RequestParam(value = "previousAddress", required = false, defaultValue = "") String previousAddress,
            @RequestParam(value = "toBePlanner", required = false) Integer toBePlanner,
            @RequestParam(value = "targetCity", required = false, defaultValue = "") String targetCity){
		Map<String, Object> map = new HashMap<String, Object>();
		if(null == uid || uid <= 0){
			map.put("code", 0);
			map.put("msg", "验证邮件发送失败");
			return gson.toJson(map);
		}
		UserInfo userInfo = userInfoService.getUserInfoByUid(uid);
		if(null == userInfo){
			map.put("code", 0);
			map.put("msg", "用户信息不存在");
			return gson.toJson(map);
		}
		if(userInfo.getStatus() != UserInfoStatusEnum.VERIFIED_EMAIL_WAIT_COMPLATE_INFO.getValue() ){
			map.put("code", 0);
			map.put("msg", "数据异常，用户不得进行此操作");
			return gson.toJson(map);
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
			userInfo.setUpdateUid(uid);
			userInfo.setUpdateIp(this.getRemoteAddr());
			userInfoService.updateUserInfo(userInfo);
		}
		
		if(null != toBePlanner && toBePlanner == 1 && StringUtils.isNotBlank(targetCity)){
			UserPlanner userPlanner = userPlannerService.getUserPlannerByUid(uid);
			if(null != userPlanner){
				map.put("code", 0);
				map.put("msg", "数据异常，规划师不得进行此操作");
				return gson.toJson(map);
			}
			userPlanner = new UserPlanner();
			userPlanner.setTargetCity(targetCity);
			userPlanner.setStatus(UserPlannerStatusEnum.WAIT_AUDIT.getValue());
			userPlanner.setCreateUid(uid);
			userPlanner.setCreateTime(new Date());
			userPlanner.setCreateIp(this.getRemoteAddr());
			userPlannerService.addUserPlanner(userPlanner);
		}
		
		map.put("code", 1);
		map.put("msg", "操作成功");
		return gson.toJson(map);
	}
	
}
