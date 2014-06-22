package com.lanrenyou.config.impl;

import java.util.Map;

public class MapConfiguration extends AbstractConfiguration {
    private final Map<String, String> config;

    public MapConfiguration(Map<String, String> configData) {
        this.config = configData;
    }

    @Override
    protected Map<String, String> getConfigData() {
        return this.config;
    }
}
