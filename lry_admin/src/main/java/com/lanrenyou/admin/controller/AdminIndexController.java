package com.lanrenyou.admin.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.support.RequestContextUtils;

import com.lanrenyou.admin.controller.base.BaseController;
import com.lanrenyou.user.service.IUserInfoService;


@Controller
@RequestMapping("/admin")
public class AdminIndexController extends BaseController {
	
	@Autowired
	private IUserInfoService userInfoService;
	
	@RequestMapping(value="index")
	public ModelAndView index(HttpServletRequest request){
		ModelAndView mav = new ModelAndView();
		mav.setViewName("/admin/index");
		return mav;
	}
	
	@RequestMapping(value="header")
	public ModelAndView header(HttpServletRequest request){
		ModelAndView mav = new ModelAndView();
		mav.setViewName("/admin/header");
		return mav;
	}
	
	
	@RequestMapping(value="/menu")
	public ModelAndView menu(HttpServletRequest request){
		ModelAndView mav = new ModelAndView();
		mav.setViewName("/admin/menu");
		return mav;
	}
	
	@RequestMapping(value="/main")
	public ModelAndView main(HttpServletRequest request){
		ModelAndView mav = new ModelAndView();
		mav.setViewName("/admin/main");
		int totalCount = userInfoService.getUserCount();
		mav.addObject("totalCount", totalCount);
		return mav;
	}
	

}

