package com.lanrenyou.config.impl;

import com.lanrenyou.config.Configuration;
import com.lanrenyou.config.context.SaeContextWrapper;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * User:tao.li
 * Date: 12-7-26
 * Time: 下午12:28
 */
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
        return Utils.mergeString(key, this.configuration.getString(key));
    }


    @Override
    public Properties toProperties() {
        return Utils.mergeProperties(this.configuration.toProperties());
    }

    @Override
    public Map<String, String> toMap() {
        return Utils.mergeMap(this.configuration.toMap());
    }

    public static final class Utils {
        private static final Map<String, String> SINA_APP_MAP = SaeContextWrapper.getSpecialConfigMap();

        public static final String mergeString(String key, String defaultValue) {
            String result = null;
            if (isSinaApp()) {
                result = SINA_APP_MAP.get(key);
            }
            if (result == null) {
                result = defaultValue;
            }
            return result;
        }

        public static final Map<String, String> mergeMap(Map<String, String> source) {
            if (isSinaApp()) {
                Map<String, String> result = new HashMap<String, String>();
                result.putAll(source);
                for (Map.Entry<String, String> entry : SINA_APP_MAP.entrySet()) {
                    if (result.containsKey(entry.getKey())) {
                        result.put(entry.getKey(), entry.getValue());
                    }
                }
                return Collections.unmodifiableMap(result);
            }
            return source;
        }

        public static final Properties mergeProperties(Properties source) {
            if (isSinaApp()) {
                Properties properties = new Properties();
                properties.putAll(source);
                for (Map.Entry<String, String> entry : SINA_APP_MAP.entrySet()) {
                    if (properties.containsKey(entry.getKey())) {
                        properties.put(entry.getKey(), entry.getValue());
                    }
                }
                return properties;
            }
            return source;
        }

        private static boolean isSinaApp() {
            return SaeContextWrapper.isSaeContext();
        }
    }
}
