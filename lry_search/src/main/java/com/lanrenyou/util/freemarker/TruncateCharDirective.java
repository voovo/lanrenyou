/**
 * TruncateChar.java
 * com.lanrenyou.util
 *
 * Function： 截取字符串 
 *
 * date		2013-3-12
 * author	peijin.zhang
 * Copyright (c) 2013, DaJie All Rights Reserved.
 */

package com.lanrenyou.util.freemarker;

import java.io.IOException;
import java.io.Writer;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import freemarker.core.Environment;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;

/**
 * ClassName:TruncateChar
 * 
 * @author peijin.zhang
 * @Date 2013-3-12 下午08:15:29
 */
public class TruncateCharDirective extends BaseDirective {

	public void execute(Environment env, Map params, TemplateModel[] model,
			TemplateDirectiveBody body) throws TemplateException, IOException {
		String value = "";
		if (params.containsKey("value")) {
			value = params.get("value").toString();
			String[] array = value.split(" ");
	    	StringBuilder sb = new StringBuilder();
	    	for(String a : array){
	    		if(StringUtils.isNotBlank(a)){
	    			sb.append(a).append(" ");
	    		}
	    	}
	    	value = sb.toString().trim();
		}
		int length = -1;
		if (params.containsKey("length")) {
			String lengthStr = params.get("length").toString();
			length = Integer.parseInt(lengthStr);
		}
		Writer out = env.getOut();
		if(value.length() > length){
			value = value.substring(0, length-1);
			value = value.replace("<", "&lt;");
			value = value.replace(">", "&gt;");
			out.write(value + "...");
		} else {
			out.write(value);
		}
	}
}
