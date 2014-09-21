/**
 * ViewInterceptor
 * com.lanrenyou.interceptor
 *
 * date		2013-3-13
 * author	peijin.zhang
 * Copyright (c) 2013, DaJie All Rights Reserved.
*/

package com.lanrenyou.interceptor;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.lanrenyou.admin.AdminConstrants;
import com.lanrenyou.admin.model.AdminUser;
import com.lanrenyou.admin.model.AdminPowerItem;
import com.lanrenyou.admin.service.IAdminPowerItemService;
import com.lanrenyou.util.HttpServletUtil;
import com.lanrenyou.util.SessionUtil;

/**
 * ClassName:ViewInterceptor
 * Function: 权限判断拦截服务
 * @author   peijin.zhang
 * @Date	 2013-3-13		下午02:48:08
 */
public class ViewInterceptor extends HandlerInterceptorAdapter {
	
	Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private IAdminPowerItemService powerItemService;
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		if(null == (request.getSession().getServletContext().getAttribute(AdminConstrants.POWER_ITEM_MAP))){
			List<AdminPowerItem> powerItemList = powerItemService.findAllAdminPowerItem();
			Map<Integer, String> powerItemMap = new HashMap<Integer, String>();
			for(AdminPowerItem pi : powerItemList){
				powerItemMap.put(pi.getId(), pi.getUrl());
			}
			request.getSession().getServletContext().setAttribute(AdminConstrants.POWER_ITEM_MAP, powerItemMap);
		}
		List<String> hasPowerList = (List<String>) request.getSession().getAttribute(AdminConstrants.ADMINISTRATOR_POWER_URL_LIST);
		String uri = HttpServletUtil.getURI(request);
		if(uri.startsWith("/css")){
			return true;
		}
		if(uri.startsWith("/images")){
			return true;
		}
		if(uri.startsWith("/img")){
			return true;
		}
		if(uri.startsWith("/js")){
			return true;
		}
		if(uri.startsWith("/src")){
			return true;
		}
		if(uri.startsWith("/admin/login")){
			return true;
		}
		if(uri.startsWith("/admin/index")){
			return true;
		}
		if(uri.startsWith("/admin/header")){
			return true;
		}
		if(uri.startsWith("/admin/menu")){
			return true;
		}
		if(uri.startsWith("/admin/main")){
			return true;
		}
		if(uri.startsWith("/admin/changelanguage")){
			return true;
		}
		for(String powerUrl : hasPowerList){
			Pattern dp = Pattern.compile(powerUrl);
			Matcher dm = dp.matcher(uri);
			if(dm.find()){
				logger.info("-------------- the request [{}] has matched [{}].", uri, powerUrl);
				return true;
			}
		}
		logger.error("you are no power to request this page {}.", uri);
		return false;
	}
}

