/**
 * ViewInterceptor
 * com.lanrenyou.interceptor
 *
 * date		2013-3-13
 * author	peijin.zhang
*/

package com.lanrenyou.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.lanrenyou.travel.model.TravelInfo;
import com.lanrenyou.travel.service.ITravelInfoService;

/**
 * 页面展示的拦截器
 */
public class ViewInterceptor extends HandlerInterceptorAdapter {
	
	Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private ITravelInfoService travelInfoService;
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
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
		
		return true;
	}
}

