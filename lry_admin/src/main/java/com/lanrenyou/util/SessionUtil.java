package com.lanrenyou.util;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.lanrenyou.admin.AdminConstrants;
import com.lanrenyou.admin.model.AdminUser;

public class SessionUtil {
	
	public static AdminUser getLoginSession(HttpServletRequest request){
		HttpSession session = request.getSession(true);
		if(null == session){
			return null;
		}
		return (AdminUser)session.getAttribute(AdminConstrants.KEY_LOGIN_ADMIN_4_SESSION);
	}
}
