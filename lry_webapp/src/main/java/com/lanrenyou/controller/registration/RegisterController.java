package com.lanrenyou.controller.registration;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.lanrenyou.controller.base.BaseController;
import com.lanrenyou.user.model.UserInfo;
import com.lanrenyou.util.ServletUtil;

@Controller
@RequestMapping("/regist")
public class RegisterController extends BaseController {

	@RequestMapping("/toPage")
	public ModelAndView toPage(HttpServletResponse response){
		UserInfo userInfo = getLoginUser();
		if(null != userInfo){
			ServletUtil.clearUserCookie(request, response);
		}
		ModelAndView mav = new ModelAndView("/regist/regist");
		return mav;
	}
	
	@RequestMapping(value="/submit")
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
		logger.info("############ Captcha:{}", captcha);
		if(StringUtils.isNotBlank(submitEmail)){
			return "";
		}
		
		return null;
	}
}
