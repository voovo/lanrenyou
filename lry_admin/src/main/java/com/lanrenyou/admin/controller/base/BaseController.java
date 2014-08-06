/**
 * 
 */
package com.lanrenyou.admin.controller.base;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.Gson;
import com.lanrenyou.admin.AdminConstrants;
import com.lanrenyou.admin.model.AdminUser;
import com.lanrenyou.admin.service.IAdminUserService;
import com.lanrenyou.util.HttpServletUtil;
import com.lanrenyou.util.SessionUtil;
import com.lanrenyou.util.freemarker.FreemarkerUtil;

/**
 * 项目抽象controller
 */
public abstract class BaseController extends AbstractCommonController {
	protected static final Gson gson = new Gson();


    /**
	 * 
	 */
	public BaseController(){
	}
	
	
	/**
	 * 获取当前登陆信息
	 * 
	 * @return
	 */
	protected AdminUser getCurrentAdmin(){
		AdminUser loginUserVo = SessionUtil.getLoginSession(request); 
		return loginUserVo;
	}
	
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
	
	/**
	 * 注册时创建登陆的session信息
	 * 
	 * @param request
	 * @param userRegister
	 * @param user 
	 */
	public void setLoginSession(HttpServletRequest request, AdminUser admin) {
		//存登陆信息
		HttpSession session = request.getSession(true);
		//存session
		session.setAttribute(AdminConstrants.KEY_LOGIN_ADMIN_4_SESSION, admin);
	}
	
	public void removeLoginSession(HttpServletRequest request) {
		//存登陆信息
		HttpSession session = request.getSession(true);
		//存session
		session.removeAttribute(AdminConstrants.KEY_LOGIN_ADMIN_4_SESSION);
	}
	
}
