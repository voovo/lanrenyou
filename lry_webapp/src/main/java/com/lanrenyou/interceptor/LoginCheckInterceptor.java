package com.lanrenyou.interceptor;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.lanrenyou.user.model.UserInfo;
import com.lanrenyou.user.service.IUserInfoService;
import com.lanrenyou.util.AesCryptUtil;
import com.lanrenyou.util.ServletUtil;
import com.lanrenyou.util.StringUtil;
import com.lanrenyou.util.constants.UserConstant;

public class LoginCheckInterceptor extends HandlerInterceptorAdapter {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(LoginCheckInterceptor.class);
	protected static final int DEFAULT_PORT = 80;
	
	@Autowired
	protected IUserInfoService userInfoService;

	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		UserInfo userInfo = getUserBaseFromLoginCookie(request);

		if (userInfo != null) {
			request.setAttribute(UserConstant.LOGIN_USER, userInfo);
		} else {
			ServletUtil.clearUserCookie(request, response);
		}
		return true;
	}

	private UserInfo getUserBaseFromLoginCookie(HttpServletRequest request) {
		Cookie c = ServletUtil.getCookie(request, UserConstant.AUTH_COOKIE_KEY);

		if ((c == null) || (StringUtil.isEmpty(c.getValue()))) {
			return null;
		}
		String decode = AesCryptUtil.decrypt(c.getValue(), UserConstant.AUTH_ENCODE_KEY);
		StringBuffer requestUrlBuffer = request.getRequestURL();
		String requestUrl = (requestUrlBuffer == null) ? "" : requestUrlBuffer.toString();
		if (StringUtil.isEmpty(decode)) {
			LOGGER.warn("===>>>Parse uid from login cookie failed.cookie value is {}, request url:{}", c.getValue(), requestUrl);
			return null;
		}
		String[] info = decode.split("\\|");
		if ((info.length != 2) || (!(StringUtil.isInteger(info[0])))) {
			return null;
		}
		int uid = Integer.parseInt(info[0]);
		String cookiePassword = info[1];
		if (uid <= 0) {
			LOGGER.warn("===>>>Get uid from login cookie failed.cookie value is {}, request url:{}", c.getValue(), requestUrl);
			return null;
		}
		UserInfo userInfo = userInfoService.getUserInfoByUid(uid);
		if (userInfo == null) {
			LOGGER.warn("===>>>Query user base by uid failed. uid:{}, request url:{}", Integer.valueOf(uid), requestUrl);
			return null;
		}
		if (!(userInfo.getUserPass().equalsIgnoreCase(cookiePassword))) {
			LOGGER.warn( "===>>>Wrong password in login cookie. uid:{}, cookie password:{}, user base password:{}, cookie value:{}, request url:{}",
					new Object[] { Integer.valueOf(uid), cookiePassword, userInfo.getUserPass(), c.getValue(), requestUrl });
			return null;
		}
		LOGGER.debug("===>>>Get login user from cookie success, uid:{}, request url:{}", userInfo.getId(), requestUrl);
		return userInfo;
	}
}
