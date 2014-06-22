package com.lanrenyou.config.context;

import java.util.HashMap;
import java.util.Map;

/**
 * User: haluo
 * Date: 12-7-4
 * Time: 下午10:01
 */
public final class SaeContextWrapper {
    private final static Map<String, String> SAE_SPECIAL_CONFIG = new HashMap<String, String>();
    private final static Map<String, String> SAE_TEMPLATE_REPLACE = new HashMap<String, String>();
    private final static Map<String, Integer> SAE_SPECIAL_DOMAIN = new HashMap<String, Integer>();
    private final static String NORMAL_CONFIG_TAG = "normal";
    private final static String SAE_CONFIG_TAG = "sae";

    static {
        SAE_SPECIAL_CONFIG.put("domains.www", "c.lanrenyou.com");
        SAE_SPECIAL_CONFIG.put("domains.job", "job.c.lanrenyou.com");
        SAE_SPECIAL_CONFIG.put("domains.sae", "guide.c.lanrenyou.com");
        SAE_SPECIAL_CONFIG.put("domains.club", "company.c.lanrenyou.com");

        for (String value : SAE_SPECIAL_CONFIG.values()) {
            SAE_SPECIAL_DOMAIN.put(value, 0); //key只用来判断域名是否匹配，value没有实质性作用
        }

        SAE_TEMPLATE_REPLACE.put("www.lanrenyou.com", "c.lanrenyou.com");
        SAE_TEMPLATE_REPLACE.put("job.lanrenyou.com", "job.c.lanrenyou.com");
        SAE_TEMPLATE_REPLACE.put("company.lanrenyou.com", "company.c.lanrenyou.com");
    }

    private SaeContextWrapper() {
    }

    public static Map<String, String> getSpecialConfigMap() {
        return SAE_SPECIAL_CONFIG;
    }

    public static boolean isSaeContext() {
        return ContextType.SINA_APP.equals(ConfigurationContext.getInstance().getContextType());
    }

    public static boolean isSaeContextByHttpRequestHost(String httpRequestHost) {
        return httpRequestHost != null && SAE_SPECIAL_DOMAIN.containsKey(httpRequestHost.toLowerCase());
    }

    public static String getConfigTagByHttpRequestHost(String httpRequestHost) {
        return isSaeContextByHttpRequestHost(httpRequestHost) ? SAE_CONFIG_TAG : NORMAL_CONFIG_TAG;
    }

    public static ContextType setContextByHttpRequestHost(String httpRequestHost) {
        ContextType contextType = isSaeContextByHttpRequestHost(httpRequestHost) ? ContextType.SINA_APP : ContextType.NORMAL;
        ConfigurationContext.getInstance().setContext(contextType);
        return contextType;
    }

    public static String getSaeContent(String originalContent) {
        if (originalContent != null && !originalContent.trim().isEmpty()) {
            for (Map.Entry<String, String> entry : SAE_TEMPLATE_REPLACE.entrySet()) {
                originalContent = originalContent.replaceAll(entry.getKey(), entry.getValue());
            }
        }
        return originalContent;
    }
}
