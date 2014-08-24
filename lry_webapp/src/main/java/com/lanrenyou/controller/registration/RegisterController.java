package com.lanrenyou.controller.registration;

import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

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
import org.springframework.web.servlet.view.RedirectView;

import com.lanrenyou.common.PasswordUtil;
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
import com.lanrenyou.util.LRYEncryptKeyProperties;
import com.lanrenyou.util.MailUtil;
import com.lanrenyou.util.ServletUtil;
import com.lanrenyou.util.constants.LRYConstant;

@Controller
@RequestMapping("/regist")
public class RegisterController extends BaseController {
	
	@Autowired
	private IUserInfoService userInfoService;
	
	@Autowired
	private IUserPlannerService userPlannerService;
	
	private static final ExecutorService EXECUTOR_SERVICE = Executors.newFixedThreadPool(3);
	
	@RequestMapping("/toPage")
	public ModelAndView toPage(HttpServletResponse response){
		UserInfo userInfo = getLoginUser();
		if(null != userInfo){
			ServletUtil.clearUserCookie(request, response);
		}
		ModelAndView mav = new ModelAndView("/regist/regist");
		return mav;
	}
	
	@RequestMapping(value="/checkEmail", method=RequestMethod.POST)
	@ResponseBody
	public String checkEmail(
			@RequestParam(value = "reg_email") String submitEmail){
		Map<String, Object> map = new HashMap<String, Object>();
		if(StringUtils.isBlank(submitEmail)){
			map.put("status", "n");
			map.put("info", "请输入邮箱");
			return gson.toJson(map);
		}
		
		UserInfo userInfo = userInfoService.getUserInfoByEmail(submitEmail);
		if(null != userInfo){
			map.put("status", "n");
			map.put("info", "邮箱已被占用");
			return gson.toJson(map);
		} else {
			map.put("status", "y");
			map.put("info", "恭喜，邮箱可使用");
			return gson.toJson(map);
		}
	}
	
	@RequestMapping(value="/submit", method=RequestMethod.POST)
	@ResponseBody
	public String submit(
			HttpServletResponse response,
			@RequestParam(value = "reg_email") String submitEmail,
            @RequestParam(value = "reg_pwd") String submitPassword,
            @RequestParam(value = "reg_repwd") String submitPassword2,
            @RequestParam(value = "captcha", required = false, defaultValue = "") String captcha){
		UserInfo userInfo = getLoginUser();
		if(null != userInfo){
			ServletUtil.clearUserCookie(request, response);
		}
		submitEmail = submitEmail.trim();
		Map<String, Object> map = new HashMap<String, Object>();
		if(StringUtils.isBlank(submitEmail)){
			map.put("status", "n");
			map.put("info", "请输入邮箱");
			return gson.toJson(map);
		}
		if(StringUtils.isBlank(submitPassword) || StringUtils.isBlank(submitPassword2)){
			map.put("status", "n");
			map.put("info", "密码不得为空");
			return gson.toJson(map);
		}
		if(!submitPassword.equals(submitPassword2)){
			map.put("status", "n");
			map.put("info", "两次密码不一致");
			return gson.toJson(map);
		}
		if(StringUtils.isBlank(captcha)){
			map.put("status", "n");
			map.put("info", "验证码不得为空");
			return gson.toJson(map);
		}
		String sessionCaptcha = (String) request.getSession().getAttribute("captchaValue");
		if(null == sessionCaptcha){
			map.put("status", "n");
			map.put("info", "验证码验证失败");
			return gson.toJson(map);
		}
		String[] arr = sessionCaptcha.split("#");
		if(arr.length != 2){
			map.put("status", "n");
			map.put("info", "验证码验证失败");
			return gson.toJson(map);
		}
		long startTime = Long.parseLong(arr[1]);
		if(System.currentTimeMillis() - startTime > 600000){
			map.put("status", "n");
			map.put("info", "验证码失效");
			return gson.toJson(map);
		}
		
		if(!captcha.toUpperCase().equals(arr[0])){
			map.put("status", "n");
			map.put("info", "验证码不正确");
			return gson.toJson(map);
		}
		
		UserInfo newUserInfo = new UserInfo();
		newUserInfo.setEmail(submitEmail);
		newUserInfo.setUserPass(PasswordUtil.convertToMd5(submitPassword));
		newUserInfo.setStatus(UserInfoStatusEnum.WAIT_VERIFY_EMAIL.getValue());
		newUserInfo.setUserType(UserInfoTypeEnum.NOMAL.getValue());
		newUserInfo.setCreateUid(0);
		newUserInfo.setCreateTime(new Date());
		newUserInfo.setCreateIp(this.getRemoteAddr());
		int result = userInfoService.addUserInfo(newUserInfo);
		if(result > 0){
			final int uid = newUserInfo.getId();
			final String email = newUserInfo.getEmail();
			EXECUTOR_SERVICE.submit(new Runnable() {
            	@Override
            	public void run() {
            		try{
	            		StringBuilder code = new StringBuilder();
	            		code.append(uid).append("#").append(System.currentTimeMillis());
	            		logger.debug("Source Code:{}", code.toString());
	            		String encryptCode = AesCryptUtil.encrypt(code.toString(), LRYEncryptKeyProperties.getProperty("REGIST_VERIFY_EMAIL_KEY"));
	            		int mailResult = MailUtil.sendEmail(email, "请您验证懒人游注册邮箱", "请在两小时内点击以下链接完成账号激活：\n <a href=\"http://"+AppConfigs.getInstance().get("domains.www")+ "/regist/verifyEmail?code="+encryptCode+"\" target=\"_blank\">"+"http://www.lanrenyou.com/regist/verifyEmail?code="+encryptCode+"</a>");
	            		logger.info("http://"+AppConfigs.getInstance().get("domains.www")+ "/regist/verifyEmail?code="+encryptCode);
	            		if(mailResult <= 0){
	            			logger.error("Registion Send Mail Fail, UID:{} | Email:{} | Verify Code:{}", uid, email, encryptCode);
	            		}
            		}catch(Exception e){
            			logger.error("{}", e);
            		}
            	}
            });
			request.setAttribute(LRYConstant.LOGIN_USER, newUserInfo);
			Cookie cookie = new Cookie(LRYConstant.REGIST_COOKIE_NAME, String.valueOf(newUserInfo.getId()));
			cookie.setMaxAge(7200);
			cookie.setPath("/regist");
			cookie.setDomain(AppConfigs.getInstance().get("domains.www"));
			response.addCookie(cookie);
			map.put("status", "y");
			map.put("info", "创建成功，请验证邮箱");
			return gson.toJson(map);
		} else {
			map.put("status", "n");
			map.put("info", "创建失败，请稍后重试");
			return gson.toJson(map);
		}
	}
	
	@RequestMapping(value="/waitEmailVerify")
	public ModelAndView toWaitEmailVerifyPage(){
		Cookie[] cookieArray = request.getCookies();
		int uid = 0;
		for(Cookie c : cookieArray){
			if (c.getName().equals(LRYConstant.REGIST_COOKIE_NAME)) {
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
		request.setAttribute("userInfo", userInfo);
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
			if (c.getName().equals(LRYConstant.REGIST_COOKIE_NAME)) {
				uid = Integer.parseInt(c.getValue());
			}
		}
		if(uid <= 0){
			map.put("status", "n");
			map.put("info", "未获取到用户信息");
			return gson.toJson(map);
		}
		UserInfo userInfo = userInfoService.getUserInfoByUid(uid);
		if(null == userInfo){
			map.put("status", "n");
			map.put("info", "未获取到用户信息");
			return gson.toJson(map);
		}
		
		if(userInfo.getStatus() == UserInfoStatusEnum.WAIT_VERIFY_EMAIL.getValue()){
			try {
				StringBuilder code = new StringBuilder();
				code.append(userInfo.getId().intValue()).append("#").append(System.currentTimeMillis());
				logger.debug("Source Code:{}", code.toString());
				String encryptCode = AesCryptUtil.encrypt(code.toString(), LRYEncryptKeyProperties.getProperty("REGIST_VERIFY_EMAIL_KEY"));
				int mailResult = MailUtil.sendEmail(userInfo.getEmail(), "请您验证懒人游注册邮箱", "请在两小时内点击以下链接完成账号激活：\n <a href=\"http://"+AppConfigs.getInstance().get("domains.www")+ "/regist/verifyEmail?code="+encryptCode+"\" target=\"_blank\">"+"http://"+AppConfigs.getInstance().get("domains.www")+ "/regist/verifyEmail?code="+encryptCode+"</a>");	
				if(mailResult <= 0){
					map.put("status", "n");
					map.put("info", "验证邮件发送失败");
					return gson.toJson(map);
				} else {
					map.put("status", "y");
					map.put("info", "验证邮件发送成功");
					return gson.toJson(map);
				}
			} catch (UnsupportedEncodingException e) {
				logger.error("{}", e);
				map.put("status", "n");
				map.put("info", "验证邮件发送失败，请稍后重试");
				return gson.toJson(map);
			}
		} else {
			map.put("status", "n");
			map.put("info", "用户已经完成邮箱验证");
			return gson.toJson(map);
		}
	}
	
	@RequestMapping(value="/verifyEmail")
	public ModelAndView verifyEmail(
			@RequestParam(value = "code") String code){
		if(StringUtils.isBlank(code)){
			return toError("校验码为空");
		}
		ModelAndView mav = new ModelAndView();
		String decrptCode = AesCryptUtil.decrypt(code, LRYEncryptKeyProperties.getProperty("REGIST_VERIFY_EMAIL_KEY"));
		logger.debug("DecryptCode:{}", decrptCode);
		if(StringUtils.isBlank(decrptCode)){
			return toError("校验码错误--1");
		}
		String[] array = decrptCode.split("#");
		if(array.length != 2){
			return toError("校验码错误--2");
		}
		int uid = Integer.parseInt(array[0]);
		UserInfo userInfo = userInfoService.getUserInfoByUid(uid);
		if(null == userInfo){
			return toError("没有此用户信息");
		}
		long createTime = Long.parseLong(array[1]);
		if(System.currentTimeMillis() - createTime > 7200000){
			mav.addObject("userInfo", userInfo);
			mav.setViewName("regist/regist_verify_fail");
			return mav;
		}
		if(userInfo.getStatus() == UserInfoStatusEnum.WAIT_VERIFY_EMAIL.getValue()){
			userInfo.setStatus(UserInfoStatusEnum.VERIFIED_EMAIL_WAIT_COMPLATE_INFO.getValue());
			userInfo.setUpdateUid(uid);
			userInfo.setUpdateIp(request.getRemoteAddr());
			userInfoService.updateUserInfo(userInfo);
			request.setAttribute(LRYConstant.LOGIN_USER, userInfo);
		} else if(userInfo.getStatus() == UserInfoStatusEnum.VERIFIED_EMAIL_WAIT_COMPLATE_INFO.getValue()){
			request.setAttribute(LRYConstant.LOGIN_USER, userInfo);
		} else {
			mav.setView(new RedirectView("/index"));
			return mav;
		}
		mav.setViewName("/regist/regist_form");
		return mav;
	}
	
	@RequestMapping(value="/submitInfo", method=RequestMethod.POST)
	public ModelAndView submitUserInfo(
			HttpServletResponse response,
			@RequestParam(value = "uid", required = true) Integer uid,
            @RequestParam(value = "nickname", required = false, defaultValue = "") String nickname,
            @RequestParam(value = "userIntro", required = false, defaultValue = "") String userIntro,
            @RequestParam(value = "presentAddress", required = false, defaultValue = "") String presentAddress,
            @RequestParam(value = "previousAddress", required = false, defaultValue = "") String previousAddress,
            @RequestParam(value = "toBePlanner", required = false) Integer toBePlanner,
            @RequestParam(value = "targetCity", required = false, defaultValue = "") String targetCity,
            @RequestParam(value = "fees", required = false, defaultValue = "") String fees){
		Map<String, Object> map = new HashMap<String, Object>();
		if(null == uid || uid <= 0){
			return toError("没有用户ID");
		}
		UserInfo userInfo = userInfoService.getUserInfoByUid(uid);
		if(null == userInfo){
			return toError("用户信息不存在");
		}
		if(userInfo.getStatus() != UserInfoStatusEnum.VERIFIED_EMAIL_WAIT_COMPLATE_INFO.getValue() ){
			return toError("数据异常，用户不得进行此操作");
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
		
		if(StringUtils.isNotBlank(targetCity)){
			UserPlanner userPlanner = userPlannerService.getUserPlannerByUid(uid);
			if(null == userPlanner){
				userPlanner = new UserPlanner();
				userPlanner.setUid(uid);
				
				userPlanner.setTargetCity(targetCity);
				userPlanner.setFees(fees);
				userPlanner.setStatus(UserPlannerStatusEnum.WAIT_AUDIT.getValue());
				userPlanner.setCreateUid(uid);
				userPlanner.setCreateTime(new Date());
				userPlanner.setCreateIp(this.getRemoteAddr());
				userPlannerService.addUserPlanner(userPlanner);
			}
		}
		
		ModelAndView mav = new ModelAndView(new RedirectView("/user/"+userInfo.getId()));
		
		ServletUtil.writeUserAuthCookie(response, userInfo.getId(), userInfo.getUserPass(), AppConfigs.getInstance().get("domains.www"), 2592000);
		ServletUtil.writeCookie(response, LRYConstant.AUTH_EMAIL_COOKIE_KEY, userInfo.getEmail(), AppConfigs.getInstance().get("domains.www"), -1);
		
		return mav;
	}
	
}
