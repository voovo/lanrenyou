package com.lanrenyou.csrf;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lanrenyou.user.model.UserInfo;

import javax.servlet.http.HttpServletRequest;

public final class LoginAuthParser {
    private static final Logger LOGGER = LoggerFactory.getLogger(LoginAuthParser.class);
    protected static final String LOGIN_USER = "loginUser";
    
    private LoginAuthParser() {
    }

    public static int getLoginUid(HttpServletRequest request) {
    	UserInfo userInfo = (UserInfo) request.getAttribute(LOGIN_USER);
    	if(null != userInfo){
    		LOGGER.debug("CSRFToken Manager Parser UID:{}", userInfo.getId());
    		return userInfo.getId();
    	}
        return 0;
    }


}
