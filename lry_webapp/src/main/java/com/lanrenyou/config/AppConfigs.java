package com.lanrenyou.config;

import java.util.Map;

/**
 * User: hui.ouyang
 * Date: 2010-1-6
 * Time: 12:17:53
 */
public final class AppConfigs {
    private static final ConfigurationManager CONFIGURATION_MANAGER = ConfigurationManager.getInstance();

    private static AppConfigs instance = new AppConfigs();

    private AppConfigs() {
    }

    public static AppConfigs getInstance() {
        return instance;
    }

    public synchronized void reload() {
    }

    public void init() {
    }

    public Map<String, String> getConfigs() {
        return CONFIGURATION_MANAGER.toMap();
    }

    public String get(String key) {
        return CONFIGURATION_MANAGER.getString(key);
    }

    public String get(String key, String defaultStr) {
        String result = get(key);
        if (result == null) {
            result = defaultStr;
        }
        return result;
    }
}
