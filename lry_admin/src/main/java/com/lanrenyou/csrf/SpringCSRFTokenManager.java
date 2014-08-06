package com.lanrenyou.csrf;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * Say something?
 * User: George
 * Date: 13-1-4
 * Time: 上午11:08
 */
public class SpringCSRFTokenManager extends CSRFTokenManager<ModelAndView> {
    private static final Logger LOG = LoggerFactory.getLogger(SpringCSRFTokenManager.class);

    @Override
    public int getLoginUserId(HttpServletRequest request) {
    	// TODO CSRF 校验
//        UserBase user = (UserBase) request.getAttribute("loginUser");
//        if (null != user) {
//            return user.getUid();
//        }
        return 0;
    }

    @Override
    public boolean appendCSRFToken(String csrfToken, ModelAndView modelAndView) {
        if (null != modelAndView) {
            Map<String, Object> model = modelAndView.getModel();
            Object previous = model.put(CSRF_TOKEN_NAME, csrfToken);
            return null == previous || csrfToken.equals(previous);
        }
        return false;
    }
}
