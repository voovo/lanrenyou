package com.lanrenyou.config.loader;


import java.util.List;

import com.lanrenyou.config.Configuration;

public interface PropertiesLoader<T extends Configuration> {
    List<T> loadConfigurations();
}
