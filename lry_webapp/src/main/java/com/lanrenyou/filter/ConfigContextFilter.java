package com.lanrenyou.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lanrenyou.config.context.ConfigurationContext;
import com.lanrenyou.config.context.SaeContextWrapper;

import javax.servlet.*;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * User: haluo
 * Date: 12-6-21
 * Time: 下午4:33
 */
public class ConfigContextFilter implements Filter {
    private final static Logger LOGGER = LoggerFactory.getLogger(ConfigContextFilter.class);

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        LOGGER.debug("======>>>>>>>Init filter.....");
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        String serverName = servletRequest.getServerName();
        String configContextType = SaeContextWrapper.getConfigTagByHttpRequestHost(serverName);
        SaeContextWrapper.setContextByHttpRequestHost(serverName);
        servletRequest.setAttribute("configContextType", configContextType);
        LOGGER.debug("===>>>>ConfigContextType is set to {}, current thread is {}, thread group is {}", new Object[]{configContextType, Thread.currentThread().getName(), Thread.currentThread().getThreadGroup().getName()});
        try {
            if (SaeContextWrapper.isSaeContext()) {
                LOGGER.debug("======>>>>>Set a p3p header to handle cross-domain cookie for IE browser.");
                HttpServletResponse httpServletResponse = (HttpServletResponse) servletResponse;
                httpServletResponse.setHeader("P3P", "CP='IDC DSP COR ADM DEVi TAIi PSA PSD IVAi IVDi CONi HIS OUR IND CNT'");
            }
            filterChain.doFilter(servletRequest, servletResponse);
        } finally {
            ConfigurationContext.getInstance().cleanContext();
        }
    }

    @Override
    public void destroy() {
        LOGGER.debug("=====>>>>Destroy filter.....");
    }
}

