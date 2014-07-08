package com.lanrenyou.util;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class HttpServletUtil
{


	/**
	 * @param request
	 * @return
	 */
	public static String getURI(HttpServletRequest request)
	{
		String uri = request.getRequestURI();
		String ctx = request.getContextPath();
		if (ctx.length() > 0)
		{
			uri = uri.substring(ctx.length());
		}
		return uri;
	}

	/**
	 * 获取客户端IP地址
	 * @param request
	 * @return
	 */
	public static String getRemoteAddr(HttpServletRequest request)
	{
        String ip = "";
        if (request != null) 
        {
            ip = request.getHeader("x-real-ip");
            if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip))
    		{
            	ip = request.getHeader("x-forwarded-for");
            	// TODO 代理的情况下，最后一个ip为客户的真实IP。
            	if (ip != null && ip.length() > 0)
				{
					String[] ips = ip.split(",");
					ip = ips[ips.length - 1];
				}
                if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip))
        		{
                	ip = request.getRemoteAddr();
        		}
    		}

        }
        
		return ip;
	}

	/**
	 * 
	 * @param response
	 */
	public static void noCache(HttpServletResponse response)
	{
		response.setHeader("Cache-control", "no-cache, no-store");
		response.setHeader("Pragma", "no-cache");
		response.setHeader("Expires", "-1");
	}
	
	
}
