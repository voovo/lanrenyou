/**
 * ViewUtil.java
 * com.lanrenyou.util
 *
 * Function：
 *
 * date		2013-3-8
 * author	peijin.zhang
*/

package com.lanrenyou.util;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

    /**
     * 一级导航
     */
    @SuppressWarnings("serial")
	public final static Map<String,String> FIRST_NAV_MAP = new HashMap<String, String>(){
		{
			put("","home");
			put("payment", "payment");
			put("about","about");
			put("products","products");
			put("platform","platform");
			put("tradingAccounts","tradingAccounts");
			put("guide","guide");
			put("contactus","contactus");
		}
	};
	
	
}

