/**
 * 
 */
package com.lanrenyou.cookie;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.i18n.CookieLocaleResolver;

import com.lanrenyou.config.AppConfigs;

/**
 *
 */
public class CrossDomainCookieLocaleResolver extends CookieLocaleResolver
{
	@Override
	public void addCookie(HttpServletResponse response, String cookieValue)
	{
		response.setHeader("P3P", "CP='IDC DSP COR ADM DEVi TAIi PSA PSD IVAi IVDi CONi HIS OUR IND CNT'");
		
		Cookie cookie = createCookie(cookieValue);
		Integer maxAge = getCookieMaxAge();
		if (maxAge != null) {
			cookie.setMaxAge(maxAge);
		}
		if (isCookieSecure()) {
			cookie.setSecure(true);
		}

		String domain = AppConfigs.getInstance().get("domains.root");
		cookie.setDomain(domain);
		
		response.addCookie(cookie);
		if (logger.isDebugEnabled()) {
			logger.debug("Added cookie with name [" + getCookieName() + "] and value [" + cookieValue + "]");
		}
	}
	

}
