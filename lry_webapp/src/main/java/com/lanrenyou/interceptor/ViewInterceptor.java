/**
 * ViewInterceptor
 * com.lanrenyou.interceptor
 *
 * date		2013-3-13
 * author	peijin.zhang
*/

package com.lanrenyou.interceptor;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.lanrenyou.util.ViewUtil;

/**
 * 页面展示的拦截器
 */
public class ViewInterceptor extends HandlerInterceptorAdapter {
	
	Logger logger = LoggerFactory.getLogger(getClass());
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		String uri = request.getRequestURI();
		logger.debug("uri:{}",uri);
		String exR = "\\.[A-Za-z]+";
		Pattern pat = Pattern.compile(exR);  
		Matcher mat = pat.matcher(uri);  
		boolean rs = mat.find();
		if (!rs) {
			if ("/".equals(uri)) {
				request.setAttribute("nav_select", ViewUtil.FIRST_NAV_MAP.get(""));
			} else {
				String nav = uri.split("/")[1];
				if (ViewUtil.FIRST_NAV_MAP.containsKey(nav)) {
					String navSelect = ViewUtil.FIRST_NAV_MAP.get(nav);
					request.setAttribute("nav_select",navSelect);
				}
			}
			
		}
		
		
		/*
		 * TODO 拦截器处理
		
		request.setAttribute("isDefaultBanner", isDefaultBanner);
		
		*/
		return true;
	}
}

