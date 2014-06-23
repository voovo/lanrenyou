package com.lanrenyou.config.loader.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lanrenyou.config.constants.ConfigConstant;
import com.lanrenyou.config.impl.JarPropertiesConfiguration;
import com.lanrenyou.config.loader.ConfigurationResourceFilter;
import com.lanrenyou.config.loader.PropertiesLoader;

import java.io.IOException;
import java.net.JarURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.*;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public class JarPropertiesLoader implements PropertiesLoader<JarPropertiesConfiguration> {
    private static final Logger LOGGER = LoggerFactory.getLogger(PropertiesLoader.class);

    private final ConfigurationResourceFilter<JarEntry> jarEntryFilter;

    public JarPropertiesLoader(ConfigurationResourceFilter<JarEntry> jarEntryFilter) {
        this.jarEntryFilter = jarEntryFilter;
    }

    @Override
    public List<JarPropertiesConfiguration> loadConfigurations() {
        LOGGER.info("load configurations from jars , use [{}] to scan the jars.", ConfigConstant.JAR_SCAN_BASE_PACKAGE);
        List<JarPropertiesConfiguration> result = new ArrayList<JarPropertiesConfiguration>();
        Set<JarFile> jarFiles = getLoadAllJarFiles();
        if (jarFiles != null && !jarFiles.isEmpty()) {
            List<JarPropertiesConfiguration> tmp = null;
            for (JarFile jarFile : jarFiles) {
                tmp = loadFromJarFile(jarFile);
                if (tmp != null && !tmp.isEmpty()) {
                    LOGGER.info("load configurations from {} , configs [{}].", jarFile.getName(), Arrays.toString(tmp.toArray()));
                    result.addAll(tmp);
                } else {
                    LOGGER.info("not config file in {}.", jarFile.getName());
                }
            }
        } else {
            LOGGER.info("not jar file to load.");
        }
        return new ArrayList<JarPropertiesConfiguration>(result);
    }

    private List<JarPropertiesConfiguration> loadFromJarFile(JarFile jarFile) {
        List<JarPropertiesConfiguration> configs = new ArrayList<JarPropertiesConfiguration>();
        String jarName = parseJarName(jarFile.getName());
        LOGGER.info("load configurations from {}.", jarName);
        Enumeration entries = jarFile.entries();
        while (entries.hasMoreElements()) {
            JarEntry entry = (JarEntry) entries.nextElement();
            String entryPath = entry.getName();
            if (jarEntryFilter.checkResource(entry)) {
                String fileName = parseFileName(entryPath);
                try {
                    JarPropertiesConfiguration configuration = new JarPropertiesConfiguration(jarName, fileName);
                    configuration.load(jarFile.getInputStream(entry));
                    configs.add(configuration);
                    LOGGER.info("load configurations from {}!/{}, config:{}", new Object[]{jarName, entryPath, configuration});
                } catch (IOException e) {
                    LOGGER.error("load configurations from {} error, e:{}", jarName, e);
                }
            }
        }
        return configs;
    }

    private Set<JarFile> getLoadAllJarFiles() {
        HashMap<String, JarFile> jars = new HashMap<String, JarFile>();
        ClassLoader classLoader = getClassLoader();
        try {
            Enumeration<URL> dirUrls = classLoader.getResources(ConfigConstant.JAR_SCAN_BASE_PACKAGE);
            while (dirUrls.hasMoreElements()) {
                URL dir = dirUrls.nextElement();
                URLConnection con = dir.openConnection();
                if (con instanceof JarURLConnection) {
                    JarURLConnection jarCon = (JarURLConnection) con;
                    jars.put(jarCon.getJarFile().getName(), jarCon.getJarFile());
                }
            }
        } catch (IOException e) {
            LOGGER.error("scan jars error,base package:{}, e:{}", ConfigConstant.JAR_SCAN_BASE_PACKAGE, e);
        }
        return new HashSet<JarFile>(jars.values());
    }

    private String parseFileName(String pathName) {
        int startIndex = pathName.lastIndexOf("/");
        if (startIndex == -1) {
            startIndex = 0;
        } else {
            startIndex++;
        }
        return pathName.substring(startIndex, pathName.length());
    }

    private String parseJarName(String pathName) {
        String path = pathName.replaceAll("\\\\", "/");
        int index = path.lastIndexOf("/");
        if (index < 0) {
            index = path.length();
        } else {
            index++;
        }
        return path.substring(index);
    }


    private ClassLoader getClassLoader() {
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        if (classLoader == null) {
            classLoader = getClass().getClassLoader();
        }
        return classLoader;
    }
}
