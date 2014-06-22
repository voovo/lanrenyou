/**
 * 
 */
package com.lanrenyou.controller.base;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.Gson;
import com.lanrenyou.util.HttpServletUtil;
import com.lanrenyou.util.SessionUtil;
import com.lanrenyou.util.freemarker.FreemarkerUtil;

/**
 * 项目抽象controller
 */
public abstract class BaseController extends AbstractCommonController {
	protected static final Gson gson = new Gson();

	/**
	 * 取用户IP地址
	 * @return
	 */
	protected String getUserIP() {
		return HttpServletUtil.getRemoteAddr(request);
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
