
package com.lanrenyou.controller.login;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.lanrenyou.controller.base.BaseController;
import com.lanrenyou.user.enums.UserInfoStatusEnum;
import com.lanrenyou.user.model.UserInfo;
import com.lanrenyou.user.service.IUserInfoService;
import com.lanrenyou.util.AesCryptUtil;
import com.lanrenyou.util.LRYEncryptKeyProperties;
import com.lanrenyou.util.MailUtil;
import com.lanrenyou.util.ServletUtil;
import com.lanrenyou.util.constants.UserConstant;


@Controller
@RequestMapping("")
public class LoginController extends BaseController {
	
	@Autowired
	private IUserInfoService userInfoService;
	
	@RequestMapping({"/login"})
	public ModelAndView about(){
		
		ModelAndView mav = new ModelAndView();
		mav.setViewName("login/login");
		return mav;
	}
	
	@RequestMapping(value="/login/loginSubmit",method=RequestMethod.POST)
	@ResponseBody
	public String loginSubmit(
			@RequestParam(value = "userName", required = true) String email,
			@RequestParam(value = "password", required = true) String password,
			@RequestParam(value = "captcha", required = true) String captcha,
			@RequestParam(value = "redir",required = false) String redir,
			HttpServletRequest request,
            HttpServletResponse response) {
		Map<String, Object> map = new HashMap<String, Object>();
		if(StringUtils.isBlank(email)){
			map.put("status", "n");
			map.put("info", "请输入邮箱");
			return gson.toJson(map);
		}
		if(StringUtils.isBlank(password)){
			map.put("status", "n");
			map.put("info", "请输入密码");
			return gson.toJson(map);
		}
		if(StringUtils.isBlank(captcha)){
			map.put("status", "n");
			map.put("info", "请输入验证码");
			return gson.toJson(map);
		}

		UserInfo userInfo = userInfoService.getUserInfoByEmail(email);
		if(userInfo==null){
			map.put("status", "n");
			map.put("info", "邮箱错误");
			return gson.toJson(map);
		}
		if(!password.equals(userInfo.getUserPass())){
			map.put("status", "n");
			map.put("info", "密码错误");
			return gson.toJson(map);
		}
		if(captcha.equals(request.getSession().getAttribute("captchaValue"))){
			map.put("status", "n");
			map.put("info", "验证码不正确");
			return gson.toJson(map);
		}
		
		if(userInfo.getStatus() == UserInfoStatusEnum.WAIT_VERIFY_EMAIL.getValue()){
			try {
				StringBuilder code = new StringBuilder();
				code.append(userInfo.getId().intValue()).append("#").append(System.currentTimeMillis());
				logger.debug("Source Code:{}", code.toString());
				String encryptCode = AesCryptUtil.encrypt(code.toString(), LRYEncryptKeyProperties.getProperty("REGIST_VERIFY_EMAIL_KEY"));
				int mailResult = MailUtil.sendEmail(userInfo.getEmail(), "请您验证懒人游注册邮箱", "请在两小时内点击以下链接完成账号激活：\n <a href=\"http://www.lanrenyou.com/regist/verifyEmail?code="+encryptCode+"\" target=\"_blank\">"+"http://www.lanrenyou.com/regist/verifyEmail?code="+encryptCode+"</a>");
				logger.info("http://www.lanrenyou.com/regist/verifyEmail?code="+encryptCode);
				if(mailResult <= 0){
					logger.error("Registion Send Mail Fail, UID:{} | Email:{} | Verify Code:{}", userInfo.getId(), userInfo.getEmail(), encryptCode);
				}
			} catch (UnsupportedEncodingException e) {
				logger.error("{}", e);
			}
			request.setAttribute(UserConstant.LOGIN_USER, userInfo);
			Cookie cookie = new Cookie(UserConstant.REGIST_COOKIE_NAME, String.valueOf(userInfo.getId()));
			cookie.setMaxAge(7200);
			cookie.setPath("/regist");
			cookie.setDomain("www.lanrenyou.com");
			response.addCookie(cookie);
			
			redir = "/regist/waitEmailVerify";
			map.put("status", "y");
			map.put("info", redir);
			return gson.toJson(map);
		} else if (userInfo.getStatus() == UserInfoStatusEnum.VERIFIED_EMAIL_WAIT_COMPLATE_INFO.getValue()){
			
		}
				
		ServletUtil.writeUserAuthCookie(response, userInfo.getId(), userInfo.getUserPass(), "www.lanrenyou.com", 2592000);
		ServletUtil.writeCookie(response, UserConstant.AUTH_EMAIL_COOKIE_KEY, userInfo.getEmail(), "www.lanrenyou.com", 2592000);

		if(StringUtils.isEmpty(redir)){
			redir = "/index";
		}
		map.put("status", "y");
		map.put("info", redir);
		return gson.toJson(map);
	}

	@RequestMapping("/logout")
	public ModelAndView logout(HttpServletRequest request,
            HttpServletResponse response){
		
		ServletUtil.deleteCookie(request, response, UserConstant.AUTH_EMAIL_COOKIE_KEY);
		
		ModelAndView mav = new ModelAndView();
		mav.setViewName("login/login");
		return mav;
	}
	
	
	@RequestMapping(value="/submitChangePassword")
	@ResponseBody
	public String submitChangePassword(
			@RequestParam(value = "oldPassword", required = true) String oldPassword,
			@RequestParam(value = "newPassword", required = true) String newPassword,
			HttpServletRequest request,
            HttpServletResponse response) {
		return "/login";
	}
}

