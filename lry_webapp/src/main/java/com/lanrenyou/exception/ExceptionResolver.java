package com.lanrenyou.exception;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.SimpleMappingExceptionResolver;

import com.lanrenyou.interceptor.FreemarkerVariablesInterceptor;

public class ExceptionResolver extends SimpleMappingExceptionResolver {
	static final Logger logger = LoggerFactory
			.getLogger(ExceptionResolver.class);

	@Override
	protected ModelAndView doResolveException(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception ex) {
		logger.error(
				"failed to run {}, request url is {}, host is {}, remote is {}, error is {}.",
				handler, request.getPathInfo(), request.getServerName(),
				request.getRemoteHost(), ex.getMessage(), ex);
		ModelAndView mv = super.doResolveException(request, response, handler,
				ex);
		FreemarkerVariablesInterceptor.setFtlVar(request, response, mv);
		return mv;
	}

}
