/**
 * ViewUtil.java
 * com.lanrenyou.util
 *
 * Function：
 *
 * date		2013-3-8
 * author	peijin.zhang
 * Copyright (c) 2013, DaJie All Rights Reserved.
*/

package com.lanrenyou.util;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lanrenyou.csrf.SpringCSRFTokenManager;


/**
 * ClassName:ViewUtil
 * Function: 展现
 */
public class ViewUtil {
	private static Logger logger = LoggerFactory.getLogger(ViewUtil.class);
	
	
	//两个对象(可能是null)是否相等
	public static boolean isEqual(Object old, Object current) {
		if (old != null && current != null) {
			return old.equals(current) ? true : false;
		}
		if (old == null && current == null) {
			return true;
		}
		return false;
	}
	
	
	/**
     * 获取CSRF TOKEN
     *
     * @param uid
     * @return
     * @author shuqiang.wang
     */
    public static String getCSRFToken(int uid) {
        return SpringCSRFTokenManager.generateCSRFToken(uid);
    }
    
    /**
     * verify CSRFToken
     * @param uid
     * @param csrfToken
     * @return
     */
    public static boolean verifyCSRFToken(Integer uid,String csrfToken) {
    	return SpringCSRFTokenManager.verifyCSRFToken(uid, csrfToken);
    }

}

