package com.lanrenyou.config.loader;

/**
 * User:tao.li
 * Date: 11-12-29
 * Time: 下午5:58
 */
public interface ConfigurationResourceFilter<T> {
    boolean checkResource(T resource);
}
