
package com.lanrenyou.controller.login;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.lanrenyou.controller.base.BaseController;
import com.lanrenyou.user.service.IUserInfoService;


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
	
	@RequestMapping(value="/login/loginSubmit")
	@ResponseBody
	public String loginSubmit(
			@RequestParam(value = "userName", required = true) String userName,
			@RequestParam(value = "password", required = true) String password,
			//@RequestParam(value = "code", required = true) String code,
			@RequestParam(value = "redir",required = false) String redir,
			HttpServletRequest request,
            HttpServletResponse response) {
		//TODO 校验验证码,暂时没有
		
		
		return null;
	}

	@RequestMapping("/logout")
	public ModelAndView logout(HttpServletRequest request,
            HttpServletResponse response){
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

