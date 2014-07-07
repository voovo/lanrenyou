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

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


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
	
}

