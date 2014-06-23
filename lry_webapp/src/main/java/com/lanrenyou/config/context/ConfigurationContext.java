package com.lanrenyou.config.context;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class ConfigurationContext {
    private static final Logger LOGGER = LoggerFactory.getLogger(ConfigurationContext.class);
    private static final ConfigurationContext INSTANCE = new ConfigurationContext();

    private ConfigurationContext() {
    }

    public static ConfigurationContext getInstance() {
        return INSTANCE;
    }

}
