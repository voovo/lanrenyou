package com.lanrenyou.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import com.lanrenyou.config.AppConfigs;

public class AppContextListener implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        AppConfigs.getInstance();
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
    }
}
