package com.lanrenyou.config.impl;

import com.lanrenyou.config.Configuration;

import java.util.Map;
import java.util.Properties;

public class ContextConfigurationWrapper implements Configuration {
    private final Configuration configuration;

    public ContextConfigurationWrapper(Configuration configuration) {
        this.configuration = configuration;
    }


    @Override
    public Double getDouble(String key) {
        return configuration.getDouble(key);
    }

    @Override
    public Float getFloat(String key) {
        return this.configuration.getFloat(key);
    }

    @Override
    public Integer getInt(String key) {
        return this.configuration.getInt(key);
    }

    @Override
    public Long getLong(String key) {
        return this.configuration.getLong(key);
    }

    @Override
    public String getString(String key) {
        return this.configuration.getString(key);
    }


    @Override
    public Properties toProperties() {
        return this.configuration.toProperties();
    }

    @Override
    public Map<String, String> toMap() {
        return this.configuration.toMap();
    }

}
