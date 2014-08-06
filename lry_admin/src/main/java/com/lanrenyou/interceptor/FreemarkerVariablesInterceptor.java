/**
 * 
 */
package com.lanrenyou.interceptor;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import freemarker.ext.servlet.IncludePage;

/**
 *
 */
public class FreemarkerVariablesInterceptor extends HandlerInterceptorAdapter {
	@Override
	public void postHandle(HttpServletRequest request,
			HttpServletResponse response, Object obj, ModelAndView mv)
			throws Exception {
		setFtlVar(request, response, mv);
	}

	public static void setFtlVar(HttpServletRequest request,
			HttpServletResponse response, ModelAndView mv) {
		if (mv != null) {
			mv.addObject("request", request);
			mv.addObject("include_page", new IncludePage(request, response));
		}
	}

}
