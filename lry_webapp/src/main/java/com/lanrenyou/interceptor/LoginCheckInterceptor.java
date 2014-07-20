package com.lanrenyou.interceptor;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.lanrenyou.config.AppConfigs;
import com.lanrenyou.user.model.UserInfo;
import com.lanrenyou.util.HtmlFilterUtil;
import com.lanrenyou.util.ServletUtil;
import com.lanrenyou.util.StringUtil;
import com.lanrenyou.util.UrlEncoderUtil;
import com.lanrenyou.util.constants.LRYConstant;

public class LoginCheckInterceptor extends HandlerInterceptorAdapter {
	
	private static final Logger logger = LoggerFactory.getLogger(LoginCheckInterceptor.class);
	protected static final int DEFAULT_PORT = 80;
	

	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		UserInfo userInfo = (UserInfo)request.getAttribute(LRYConstant.LOGIN_USER);
	    if (null == userInfo) {
	    	if ((request.getHeader("X-Requested-With") != null) && (request.getHeader("X-Requested-With").equals("XMLHttpRequest"))) {
	    		return false;
	    	}
	    	String currentUrl = getForwardUrl(request);
	    	logger.debug("user does not login");
	    	response.sendRedirect("http://" + AppConfigs.getInstance().get("domains.www") + "/login?redir=" + UrlEncoderUtil.encodeByUtf8(currentUrl));
	    	return false;
	    }

	    Cookie mailCookie = ServletUtil.getCookie(request, LRYConstant.AUTH_EMAIL_COOKIE_KEY);
	    if ((mailCookie == null) || (StringUtil.isBlank(mailCookie.getValue())) || mailCookie.getValue().equals(userInfo.getEmail())) {
	    	ServletUtil.writeCookie(response, LRYConstant.AUTH_EMAIL_COOKIE_KEY, userInfo.getEmail(), AppConfigs.getInstance().get("domains.www"), -1);
	    }

	    return true;
	}

	protected String getForwardUrl(HttpServletRequest request) {
	    int port = request.getServerPort();
	    String servletPath = request.getServletPath();
	    if ((servletPath == null) || ("/".equals(servletPath))) {
	      servletPath = "";
	    }
	    StringBuilder stringBuilder = new StringBuilder(request.getScheme()).append("://").append(request.getServerName()).append(port == 80 ? "" : new StringBuilder().append(":").append(port).toString()).append(request.getContextPath()).append(servletPath).append(request.getPathInfo());

	    if ((request.getQueryString() != null) && (!request.getQueryString().isEmpty())) {
	    	stringBuilder.append("?").append(HtmlFilterUtil.filterHeaderValue(request.getQueryString()));
	    }
	    return stringBuilder.toString();
	}
}
