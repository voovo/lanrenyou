package com.lanrenyou.controller.registration;

import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
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
}
