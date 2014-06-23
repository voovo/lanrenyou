package com.lanrenyou.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.lanrenyou.config.AppConfigs;

import freemarker.ext.servlet.IncludePage;

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
			String ctx = request.getContextPath();
			mv.addObject("configs", AppConfigs.getInstance().getConfigs());
			mv.addObject("request", request);
			mv.addObject("ctx", ctx);
			String resourceContextPath = AppConfigs.getInstance().get(
					"webapp.remote_static_resource"); // 远程 静态资源目录
			if (StringUtils.isEmpty(resourceContextPath)) {
				resourceContextPath = ctx + "/resources";
			}
			request.setAttribute("resourceContextPath", resourceContextPath);
			String localRCP = AppConfigs.getInstance().get(
					"webapp.local_static_resource"); // 本地 静态资源目录
			if (StringUtils.isEmpty(localRCP)) {
				localRCP = ctx + "/resources";
			}
			mv.addObject("localRCP", localRCP);
			mv.addObject("include_page", new IncludePage(request, response));
			String basePath = request.getScheme() + "://"
					+ request.getServerName() + ":" + request.getServerPort()
					+ ctx + "/";
			mv.addObject("basePath", basePath);
		}
	}

}
