/**
 * ViewInterceptor
 * com.lanrenyou.interceptor
 *
 * date		2013-3-13
 * author	peijin.zhang
*/

package com.lanrenyou.interceptor;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import mybatis.framework.core.support.PageIterator;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.lanrenyou.letter.model.PrivateLetter;
import com.lanrenyou.letter.service.IPrivateLetterService;
import com.lanrenyou.travel.model.TravelInfo;
import com.lanrenyou.travel.service.ITravelInfoService;
import com.lanrenyou.user.model.UserInfo;
import com.lanrenyou.user.service.IUserInfoService;
import com.lanrenyou.util.AesCryptUtil;
import com.lanrenyou.util.LRYEncryptKeyProperties;
import com.lanrenyou.util.ServletUtil;
import com.lanrenyou.util.StringUtil;
import com.lanrenyou.util.constants.LRYConstant;

/**
 * 页面展示的拦截器
 */
public class ViewInterceptor extends HandlerInterceptorAdapter {
	
	Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private ITravelInfoService travelInfoService;
	
	@Autowired
	protected IUserInfoService userInfoService;
	
	@Autowired
	protected IPrivateLetterService privateLetterService;
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		
		UserInfo userInfo = getUserBaseFromLoginCookie(request);

		if (userInfo != null) {
			request.setAttribute(LRYConstant.LOGIN_USER, userInfo);
			PageIterator<PrivateLetter> letterPage = privateLetterService.pageQueryPrivateLetter(userInfo.getId(), 1, 2);
			if(null != letterPage){
				request.setAttribute("headerLetterList", letterPage.getData());
				request.setAttribute("headerLetterCnt", letterPage.getTotalCount());
			}
		} else {
			ServletUtil.clearUserCookie(request, response);
		}
		
		String uri = request.getRequestURI();
		if(StringUtils.isNotBlank(uri) && uri.matches("/travel/\\d+(/.*)?")){
			String tidStr = uri.substring(8);
			int i = tidStr.indexOf('/');
			int tid = 0;
			try{
				if(i == -1){
					tid = Integer.parseInt(tidStr);
				} else {
					tidStr = tidStr.substring(0, i);
					tid = Integer.parseInt(tidStr);
				}
			}catch(Exception e){
				logger.warn("非游记ID：{}", tidStr);
			}
			
			TravelInfo travelInfo = travelInfoService.getTravelInfoById(tid);
			if(null != travelInfo){
				request.setAttribute("current_travel", travelInfo);
			}
		}
		if(StringUtils.isNotBlank(uri) && uri.matches("/user/\\d+(/.*)?")){
			String uidStr = uri.substring(6);
			int i = uidStr.indexOf('/');
			int uid = 0;
			try{
				if(i == -1){
					uid = Integer.parseInt(uidStr);
				} else {
					uidStr = uidStr.substring(0, i);
					uid = Integer.parseInt(uidStr);
				}
			}catch(Exception e){
				logger.warn("非用户ID：{}", uidStr);
			}
			
			UserInfo current_UserInfo = userInfoService.getUserInfoByUid(uid);
			if(null != current_UserInfo){
				request.setAttribute("current_user", current_UserInfo);
			}
		}
		
		return true;
	}
	
	private UserInfo getUserBaseFromLoginCookie(HttpServletRequest request) {
		Cookie c = ServletUtil.getCookie(request, LRYConstant.AUTH_COOKIE_KEY);

		if ((c == null) || (StringUtil.isEmpty(c.getValue()))) {
			return null;
		}
		String decode = AesCryptUtil.decrypt(c.getValue(), LRYEncryptKeyProperties.getProperty(LRYConstant.AUTH_ENCODE_KEY));
		StringBuffer requestUrlBuffer = request.getRequestURL();
		String requestUrl = (requestUrlBuffer == null) ? "" : requestUrlBuffer.toString();
		if (StringUtil.isEmpty(decode)) {
			logger.warn("===>>>Parse uid from login cookie failed.cookie value is {}, request url:{}", c.getValue(), requestUrl);
			return null;
		}
		String[] info = decode.split("\\|");
		if ((info.length != 2) || (!(StringUtil.isInteger(info[0])))) {
			return null;
		}
		int uid = Integer.parseInt(info[0]);
		String cookiePassword = info[1];
		if (uid <= 0) {
			logger.warn("===>>>Get uid from login cookie failed.cookie value is {}, request url:{}", c.getValue(), requestUrl);
			return null;
		}
		UserInfo userInfo = userInfoService.getUserInfoByUid(uid);
		if (userInfo == null) {
			logger.warn("===>>>Query user base by uid failed. uid:{}, request url:{}", Integer.valueOf(uid), requestUrl);
			return null;
		}
		if (!(userInfo.getUserPass().equalsIgnoreCase(cookiePassword))) {
			logger.warn( "===>>>Wrong password in login cookie. uid:{}, cookie password:{}, user base password:{}, cookie value:{}, request url:{}",
					new Object[] { Integer.valueOf(uid), cookiePassword, userInfo.getUserPass(), c.getValue(), requestUrl });
			return null;
		}
		logger.debug("===>>>Get login user from cookie success, uid:{}, request url:{}", userInfo.getId(), requestUrl);
		return userInfo;
	}
}

