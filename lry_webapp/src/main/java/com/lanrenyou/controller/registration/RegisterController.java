package com.lanrenyou.controller.registration;

import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

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
import com.lanrenyou.user.enums.UserInfoTypeEnum;
import com.lanrenyou.user.model.UserInfo;
import com.lanrenyou.user.service.IUserInfoService;
import com.lanrenyou.util.MailUtil;
import com.lanrenyou.util.ServletUtil;

@Controller
@RequestMapping("/regist")
public class RegisterController extends BaseController {
	
	@Autowired
	private IUserInfoService userInfoService;

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
		logger.info("############ Captcha:{}", captcha);
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
		
		try {
			int mailResult = MailUtil.sendEmail(submitEmail, "请您验证懒人游注册邮箱", "链接AAAA");	//TODO zhangpj
			if(mailResult <= 0){
				map.put("code", 0);
				map.put("msg", "验证邮件发送失败");
				return gson.toJson(map);
			}
		} catch (UnsupportedEncodingException e) {
			logger.error("{}", e);
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
			map.put("code", 1);
			map.put("msg", "创建成功，请验证邮箱");
			return gson.toJson(map);
		} else {
			map.put("code", 0);
			map.put("msg", "创建失败，请稍后重试");
			return gson.toJson(map);
		}
	}
}
