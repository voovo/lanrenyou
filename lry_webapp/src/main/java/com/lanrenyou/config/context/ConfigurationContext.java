package com.lanrenyou.config.context;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * User:tao.li
 * Date: 12-6-21
 * Time: 下午2:00
 */
public final class ConfigurationContext {
    private static final Logger LOGGER = LoggerFactory.getLogger(ConfigurationContext.class);
    private static final ConfigurationContext INSTANCE = new ConfigurationContext();
    private final ThreadLocal<ContextType> context = new ThreadLocal<ContextType>() {
        @Override
        protected ContextType initialValue() {
            return ContextType.NORMAL;
        }
    };

    private ConfigurationContext() {
    }

    public static ConfigurationContext getInstance() {
        return INSTANCE;
    }

    public void setContext(ContextType contextType) {
        if(contextType == null){
            LOGGER.warn("===>>>>>Current thread:{}, current thread group:{}, contextType from parameter is null, set failed.", Thread.currentThread().getName(), Thread.currentThread().getThreadGroup().getName());
            return;
        }
        LOGGER.debug("===>>>>>Current thread:{}, current thread group:{}, set contextType:{}", new Object[]{Thread.currentThread().getName(), Thread.currentThread().getThreadGroup().getName(), contextType.name()});
        context.set(contextType);
    }

    public void cleanContext() {
        LOGGER.debug("===>>>>>Current thread:{}, current thread group:{},contextType will be removed.", Thread.currentThread().getName(), Thread.currentThread().getThreadGroup().getName());
        context.remove();
    }

    public ContextType getContextType() {
        ContextType result = context.get();
        if (result == null) {
            result = ContextType.NORMAL;
        }
        return result;
    }
}
