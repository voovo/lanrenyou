package com.lanrenyou.interceptor;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.lanrenyou.config.AppConfigs;
import com.lanrenyou.config.ConfigProperties;
import com.lanrenyou.util.SessionUtil;
import com.lanrenyou.util.UrlEncoderUtil;

public class LoginCheckInterceptor extends HandlerInterceptorAdapter {

	protected static final int DEFAULT_PORT = 80;

	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		// TODO 用户登录拦截，只使用session或者request中的的Attribute，不要使用cookie
		return true;
	}

	private void sendRedirect(HttpServletRequest request,HttpServletResponse response) throws IOException {
		String currentUrl = getForwardUrl(request);
        String defaultRedirectValue = "/";
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.removeAttribute("loginUserVo");
        }
		response.sendRedirect("http://"+ConfigProperties.getProperty("domain.www")+"/login?redir="
                        + UrlEncoderUtil.encodeByUtf8(currentUrl, defaultRedirectValue)
        );
		
	}

	/**
     * 获取用户的请求URL，主要用于URL调整
     *
     * @param request
     * @return
     */
    protected String getForwardUrl(HttpServletRequest request) {
        int port = request.getServerPort();
        String servletPath = request.getServletPath();
        if (servletPath == null || "/".equals(servletPath)) {
            servletPath = "";
        }
        StringBuilder stringBuilder = new StringBuilder(request.getScheme())
                .append("://").append(request.getServerName()).append(port == DEFAULT_PORT ? "" : ":" + port)
                .append(request.getContextPath()).append(servletPath).append(request.getPathInfo());
        if (request.getQueryString() != null && !request.getQueryString().isEmpty()) {
            stringBuilder.append("?").append(filterHeaderValue(request.getQueryString()));
        }
        return stringBuilder.toString();
    }
    
    public static String filterHeaderValue(String value) {
        if (value == null || value.length() == 0) {
            return null;
        }
        StringBuilder sb = new StringBuilder(value.length());
        for (int i = 0; i < value.length(); i++) {
            char c = value.charAt(i);
            if (c >= 32 && c < 127) {
                sb.append(c);
            }
        }
        return sb.toString();
    }
}
