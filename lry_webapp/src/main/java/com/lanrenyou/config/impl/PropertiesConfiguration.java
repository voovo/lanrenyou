package com.lanrenyou.config.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URL;
import java.util.Collections;
import java.util.Map;

public class PropertiesConfiguration extends AbstractConfiguration {
    private static final Logger LOGGER = LoggerFactory.getLogger(PropertiesConfiguration.class);

    private Map<String, String> configData;

    private final String configName;

    public PropertiesConfiguration(String configName) {
        this.configName = configName;
        this.configData = buildConfig();
    }

    @Override
    protected Map<String, String> getConfigData() {
        return this.configData;
    }

    public String getFileName() {
        return configName;
    }

    private Map<String, String> buildConfig() {
        ClassLoader classLoader = Thread.currentThread()
                .getContextClassLoader();
        if (classLoader == null) {
            classLoader = getClass().getClassLoader();
        }
        return loadFromClassLoader(classLoader);
    }

    private Map<String, String> loadFromClassLoader(ClassLoader classLoader) {
        LOGGER.debug("load config from class loader.", classLoader);
        if (classLoader != null) {
            try {
                URL url = classLoader.getResource(this.configName);
                return readProperties(url);
            } catch (Exception e) {
                LOGGER.error("load from class loader error, e:{}.", e);
            }
        }
        return Collections.emptyMap();
    }
}
