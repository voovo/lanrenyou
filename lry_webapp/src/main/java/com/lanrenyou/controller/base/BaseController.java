package com.lanrenyou.controller.base;

import org.springframework.web.servlet.ModelAndView;

import com.google.gson.Gson;
import com.lanrenyou.travel.model.TravelInfo;
import com.lanrenyou.user.model.UserInfo;
import com.lanrenyou.util.constants.LRYConstant;
import com.lanrenyou.util.freemarker.FreemarkerUtil;

/**
 * 项目抽象controller
 */
public abstract class BaseController extends AbstractCommonController {
	
	protected static final Gson gson = new Gson();

	public UserInfo getLoginUser(){
		return (UserInfo) request.getAttribute(LRYConstant.LOGIN_USER);
	}
	
	public TravelInfo getCurrentTravel(){
		return (TravelInfo) request.getAttribute(LRYConstant.LOGIN_USER);
	}
	
	/**
	 * 导入枚举到ftl中
	 * @param enumClass
	 * @param model
	 */
	@SuppressWarnings("rawtypes")
	protected void importEnum2Ftl(Class enumClass, ModelAndView mv) {
        FreemarkerUtil.importEnum2Ftl(enumClass, mv);
    }
	
	public ModelAndView to404(){
		logger.warn("ready forward 404 page,the request url is [{}]", request.getRequestURL().toString());
		return new ModelAndView("common/404");
	}
	
	public ModelAndView toError(String errorMsg){
		logger.warn("ready forward error page,the request url is [{}]", request.getRequestURL().toString());
		ModelAndView mav = new ModelAndView("common/error");
		mav.addObject("errorMsg", errorMsg);
		return mav;
	}
	
}
