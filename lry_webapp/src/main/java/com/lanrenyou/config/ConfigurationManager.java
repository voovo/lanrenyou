package com.lanrenyou.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lanrenyou.config.constants.ConfigConstant;
import com.lanrenyou.config.impl.AbstractConfiguration;
import com.lanrenyou.config.impl.ContextConfigurationWrapper;
import com.lanrenyou.config.impl.JarPropertiesConfiguration;
import com.lanrenyou.config.impl.MapConfiguration;
import com.lanrenyou.config.impl.PropertiesConfiguration;
import com.lanrenyou.config.loader.ConfigurationResourceFilter;
import com.lanrenyou.config.loader.PropertiesLoader;
import com.lanrenyou.config.loader.impl.ClassesPropertiesLoader;
import com.lanrenyou.config.loader.impl.JarPropertiesLoader;

import java.io.File;
import java.util.*;
import java.util.jar.JarEntry;

/**
 * User:tao.li
 * Date: 11-12-23
 * Time: 上午11:47
 */
public final class ConfigurationManager extends AbstractConfiguration {
    private static final Logger LOGGER = LoggerFactory.getLogger(ConfigurationManager.class);

    private static ConfigurationManager instance = new ConfigurationManager();

    //the configuration load from classes dir , key is file name ,value is configuration
    private final Map<String, PropertiesConfiguration> classesConfigurationMap =
            Collections.synchronizedMap(new HashMap<String, PropertiesConfiguration>());

    //the configuration load from jars, key is file name ,value is configuration
    private final Map<String, JarPropertiesConfiguration> jarConfigurationMap =
            Collections.synchronizedMap(new HashMap<String, JarPropertiesConfiguration>());

    private final PropertiesLoader<JarPropertiesConfiguration> jarPropertiesLoader = new JarPropertiesLoader(new JarResourceFilter());

    private final PropertiesLoader<PropertiesConfiguration> classesPropertiesLoader = new ClassesPropertiesLoader(new ClassResourceFilter());

    //the result after merge all configurations in classes dir
    private Configuration classesConfiguration;

    //the result after override the key of jar configuration using classes configuration
    private Map<String, String> globalConfigurationMap;

    private String envName;

    private String languageName;

    public static ConfigurationManager getInstance() {
        return instance;
    }

    /**
     * 获取指定配置文件中的配置信息<br/>
     *
     * @param fileName 待获取文件的名称（逻辑名称）
     *
     * @return
     */
    public Configuration getByFileName(String fileName) {
        String realFileName = fillFileName(fileName);
        Configuration configuration = classesConfigurationMap.get(realFileName);
        if (configuration == null) {
            configuration = jarConfigurationMap.get(realFileName);
        }
        if (configuration == null && isDevMode()) {
            configuration = readFromConfigPath(realFileName);
        }
        if (configuration != null) {
            return new ContextConfigurationWrapper(new MapConfiguration(mergeConfiguration(classesConfiguration, configuration)));
        }
        return null;
    }

    @Override
    public String getString(String key) {
        return ContextConfigurationWrapper.Utils.mergeString(key, super.getString(key));
    }


    @Override
    public Properties toProperties() {
        return ContextConfigurationWrapper.Utils.mergeProperties(super.toProperties());
    }

    @Override
    public Map<String, String> toMap() {
        return ContextConfigurationWrapper.Utils.mergeMap(super.toMap());
    }

    private Configuration readFromConfigPath(String fileName) {
        return new PropertiesConfiguration("config/" + fileName);
    }

    private boolean isDevMode() {
        return "dev".equals(this.envName);
    }

    private String fillFileName(String fName) {
        if (fName != null && fName.endsWith(ConfigConstant.HADWINS_CONFIG_SUFFIX)) {
            return fName;
        } else {
            return fName + "_" + languageName + "_" + envName + ConfigConstant.HADWINS_CONFIG_SUFFIX;
        }
    }

    private ConfigurationManager() {
        initClassesConfigurationMap();
        initClassesConfiguration();

        initEnvName();
        initLanguageName();

        initJarConfigurationMap();
        initGlobalConfigurationMap();
    }

    private void initClassesConfigurationMap() {
        List<PropertiesConfiguration> classesConfigurations = classesPropertiesLoader.loadConfigurations();
        LOGGER.info("load from class path.");
        for (PropertiesConfiguration configuration : classesConfigurations) {
            if (classesConfigurationMap.containsKey(configuration.getFileName())) {
                LOGGER.error("in the class path find the name {}.", configuration.getFileName());
                throw new RuntimeException(String.format("in the class path find the name %s.", configuration.getFileName()));
            } else {
                classesConfigurationMap.put(configuration.getFileName(), configuration);
            }
        }
        LOGGER.info("init class path configuration success, key size:{}.", classesConfigurationMap.size());
    }

    private void initClassesConfiguration() {
        LOGGER.info("init class path configuration.");
        Map<String, String> margeMap = new HashMap<String, String>();
        for (PropertiesConfiguration configuration : this.classesConfigurationMap.values()) {
            for (Map.Entry<String, String> e : configuration.toMap().entrySet()) {
                if (margeMap.containsKey(e.getKey())) {
                    throw new RuntimeException("class path properties have same key " + e.getKey() + " in "
                            + configuration.getFileName());
                } else {
                    margeMap.put(e.getKey(), e.getValue());
                }
            }
        }
        LOGGER.info("class path configuration have key size:{}.", margeMap.size());
        this.classesConfiguration = new MapConfiguration(margeMap);
    }

    private void initEnvName() {
        String configEnvName = this.classesConfiguration.getString(ConfigConstant.HADWINS_APP_KEY_ENV);
        if (configEnvName != null && !"".equals(configEnvName.trim())) {
            LOGGER.info("set environment,use config environment:{}.", configEnvName);
            this.envName = configEnvName.toLowerCase();
        } else {
            LOGGER.warn("not config environment, use default value:{}.", ConfigConstant.HADWINS_APP_DEFAULT_ENV);
            this.envName = ConfigConstant.HADWINS_APP_DEFAULT_ENV;
        }
    }

    private void initLanguageName() {
        String language = this.classesConfiguration.getString(ConfigConstant.HADWINS_APP_KEY_LAN);
        if (language != null && !"".equals(language.trim())) {
            LOGGER.info("set config language, use config language:{}.", language);
            this.languageName = language.toLowerCase();
        } else {
            LOGGER.warn("not config language, use default value:{}.", ConfigConstant.HADWINS_APP_DEFAULT_LAN);
            this.languageName = ConfigConstant.HADWINS_APP_DEFAULT_LAN;
        }
    }

    private void initJarConfigurationMap() {
        List<JarPropertiesConfiguration> jarPropertiesConfigurations = jarPropertiesLoader.loadConfigurations();
        LOGGER.info("from jar load file size:{}.", jarPropertiesConfigurations.size());
        for (JarPropertiesConfiguration configuration : jarPropertiesConfigurations) {
            if (jarConfigurationMap.containsKey(configuration.getFileName())) {
                LOGGER.warn("have the same config name {} in {} and {}.",
                        new Object[]{
                                configuration.getFileName(),
                                jarConfigurationMap.get(configuration.getFileName()).getJarName(),
                                configuration.getJarName()}
                );
                LOGGER.warn("use the {} file config.", configuration.getJarName());
                throw new RuntimeException(
                        "have the same config name " + configuration.getFileName() + " in "
                                + jarConfigurationMap.get(configuration.getFileName()).getJarName()
                                + " and " + configuration.getJarName()
                );
            } else {
                jarConfigurationMap.put(configuration.getFileName(), configuration);
            }
        }
        LOGGER.info("init jar configuration success, key size:{}.", jarConfigurationMap.size());
    }

    private void initGlobalConfigurationMap() {
        LOGGER.info("init global configuration.");
        Configuration[] jarConfigurations = new Configuration[this.jarConfigurationMap.values().size()];
        this.jarConfigurationMap.values().toArray(jarConfigurations);
        LOGGER.info("class path have key size:{}.", this.classesConfiguration.toMap().size());
        LOGGER.info("jar have file size:{}.", jarConfigurations.length);
        Map<String, String> globalMap = mergeConfiguration(this.classesConfiguration, jarConfigurations);
        LOGGER.info("after merge have key size:{}.", globalMap.size());
        addClassesOtherConfig(globalMap);
        this.globalConfigurationMap = globalMap;
    }

    private void addClassesOtherConfig(Map<String, String> globalMap) {
        for (Map.Entry<String, String> e : classesConfiguration.toMap().entrySet()) {
            if (!globalMap.containsKey(e.getKey())) {
                globalMap.put(e.getKey(), e.getValue());
            }
        }
    }

    /**
     * 合并配置信息<br/>
     * 用mastConfiguration的配置合并覆盖other中的配置信息
     *
     * @param mastConfiguration
     * @param other
     *
     * @return
     */
    private Map<String, String> mergeConfiguration(Configuration mastConfiguration, Configuration... other) {
        Map<String, String> mastMap = mastConfiguration.toMap();
        Map<String, String> result = new HashMap<String, String>();
        if (other != null && other.length > 0) {
            for (Configuration configuration : other) {
                for (Map.Entry<String, String> e : configuration.toMap().entrySet()) {
                    if (result.containsKey(e.getKey())) {
                        LOGGER.warn("contain key:{}, value:{}.", e.getKey(), e.getValue());
                        throw new RuntimeException("have key " + e.getKey() + " with value " + e.getValue());
                    } else if (mastMap.containsKey(e.getKey())) {
                        LOGGER.warn("override key:{}, oldValue:{}, newValue={}.",
                                new Object[]{e.getKey(), result.get(e.getKey()), mastMap.get(e.getKey())});
                        result.put(e.getKey(), mastMap.get(e.getKey()));
                    } else {
                        LOGGER.debug("add key:{}, value:{}.", e.getKey(), e.getValue());
                        result.put(e.getKey(), e.getValue());
                    }
                }
            }
        } else {
            result.putAll(mastConfiguration.toMap());
        }
        return result;
    }

    @Override
    protected Map<String, String> getConfigData() {
        return globalConfigurationMap;
    }

    private class JarResourceFilter implements ConfigurationResourceFilter<JarEntry> {
        @Override
        public boolean checkResource(JarEntry resource) {
            return checkRoot(resource) || checkConfigDir(resource);
        }

        /**
         * 检测配置目录资源
         *
         * @param jarEntry
         *
         * @return
         */
        private boolean checkConfigDir(JarEntry jarEntry) {
            String lowerEntryPath = jarEntry.getName().toLowerCase();
            return lowerEntryPath.endsWith(("_" + languageName + "_" + envName + ConfigConstant.HADWINS_CONFIG_SUFFIX).toLowerCase())
                    && lowerEntryPath.startsWith((ConfigConstant.JAR_CONFIG_BASE_DIR + "/").toLowerCase());
        }

        /**
         * 检测root目录资源
         *
         * @param jarEntry
         *
         * @return
         */
        private boolean checkRoot(JarEntry jarEntry) {
            String lowerEntryPath = jarEntry.getName().toLowerCase();
            return lowerEntryPath.startsWith(ConfigConstant.HADWINS_CONFIG_PREFIX)
                    && lowerEntryPath.endsWith(ConfigConstant.HADWINS_CONFIG_SUFFIX)
                    && lowerEntryPath.indexOf("/") < 0;
        }
    }

    private static class ClassResourceFilter implements ConfigurationResourceFilter<File> {
        @Override
        public boolean checkResource(File resource) {
            return resource.getName().startsWith(ConfigConstant.HADWINS_CONFIG_PREFIX)
                    && resource.getName().endsWith(ConfigConstant.HADWINS_CONFIG_SUFFIX);
        }
    }
}
