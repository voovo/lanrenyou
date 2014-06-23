package com.lanrenyou.csrf;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

public final class LoginAuthParser {
    private static final Logger LOGGER = LoggerFactory.getLogger(LoginAuthParser.class);

    private LoginAuthParser() {
    }

    public static int getLoginUid(HttpServletRequest request) {
        // TODO 从session或者request中获取user对象，返回这个user对象的uid
        return 0;
    }


}
