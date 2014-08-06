/**
 * 
 */
package com.lanrenyou.interceptor;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.lanrenyou.admin.AdminConstrants;
import com.lanrenyou.admin.model.AdminUser;
import com.lanrenyou.admin.model.AdminPowerItem;
import com.lanrenyou.admin.service.IAdminPowerItemService;
import com.lanrenyou.util.ConfigProperties;
import com.lanrenyou.util.HttpServletUtil;
import com.lanrenyou.util.SessionUtil;

public class LoginCheckInterceptor extends HandlerInterceptorAdapter {
	
	Logger logger = LoggerFactory.getLogger(getClass());
	
	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		// TODO 用户登录拦截，只使用session或者request中的的Attribute，不要使用cookie
		//session 登陆信息
		AdminUser admin = SessionUtil.getLoginSession(request);
		request.setAttribute("userIP",HttpServletUtil.getRemoteAddr(request));
		if (null == admin) {
			String uri = HttpServletUtil.getURI(request);
			logger.info("-------------- request uri :{}", uri);
			if(uri.startsWith("/admin/login")){
				return true;
			}
			this.sendRedirect(request,response);
			return false;
		}
		
		request.setAttribute("adminUser", admin);
		return true;
	}

	private void sendRedirect(HttpServletRequest request,HttpServletResponse response) throws IOException {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.removeAttribute(AdminConstrants.KEY_LOGIN_ADMIN_4_SESSION);
        }
		response.sendRedirect("http://"+ConfigProperties.getProperty("domain.admin")+"/admin/login");
	}
    
}
