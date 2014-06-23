package com.lanrenyou.config.loader;

public interface ConfigurationResourceFilter<T> {
    boolean checkResource(T resource);
}
