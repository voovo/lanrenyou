package com.lanrenyou.config.loader;


import java.util.List;

import com.lanrenyou.config.Configuration;

/**
 * User:tao.li
 * Date: 11-12-23
 * Time: 下午12:11
 */
public interface PropertiesLoader<T extends Configuration> {
    List<T> loadConfigurations();
}
