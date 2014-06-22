package com.lanrenyou.util.freemarker;

import freemarker.cache.TemplateLoader;
import freemarker.core.Environment;
import freemarker.template.Configuration;
import freemarker.template.TemplateException;
import freemarker.template.TemplateExceptionHandler;
import freemarker.template.TemplateModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import com.lanrenyou.config.AppConfigs;

import javax.servlet.ServletContext;
import java.io.File;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


/**
 * 扩展缺省的  FreemarkerManager以自动引入多组Macro及内置系统变量,并增加对HTML的处理
 * User:meiheng
 * Date: 2012/08/22
 */
public class CustomerFreemarkerConfigurationWithEscape extends FreeMarkerConfigurer{
    private static final Logger LOGGER = LoggerFactory.getLogger(CustomerFreemarkerConfigurationWithEscape.class);
    private ServletContext servletContext;

    private Map<String, ? extends TemplateModel> sharedVariables;
    List<String> escapeExcludeDirs = new ArrayList<String>();
    private String templateLoaderPath;

    @Override
    protected void postProcessConfiguration(Configuration config) throws IOException, TemplateException {
        LOGGER.info("post process configuration ...");
        super.postProcessConfiguration(config);
        //############
        ServletContext context = servletContext;
        if (context.getRealPath("/WEB-INF/ftl/common") != null) {
            escapeExcludeDirs.add(context.getRealPath("/WEB-INF/ftl/common"));
        }
        String escapeConfig = AppConfigs.getInstance().get("escape_exclude_dir");
        if (escapeConfig != null) {
            String[] escapeDirs = escapeConfig.split(",");
            for (String dir : escapeDirs) {
                String realPath = context.getRealPath(dir);
                if (realPath != null) {
                    escapeExcludeDirs.add(realPath);
                }
            }
        }
        try {
            final TemplateLoader templateLoader = new FileTemplateLoader4EscapeHtml(new File(context.getRealPath(this.templateLoaderPath)), escapeExcludeDirs);
            config.setTemplateLoader(templateLoader);
        } catch (IOException e) {
            logger.error("TemplateLoader error:" + e.getMessage());
            e.printStackTrace();
        }

        config.setObjectWrapper(new CustomerBeanWrapper());
        if (AppConfigs.getInstance().get("dev.mode") != null && AppConfigs.getInstance().get("dev.mode").equals("1")) {
            config.setTemplateExceptionHandler(TemplateExceptionHandler.HTML_DEBUG_HANDLER);
        } else {
            config.setTemplateExceptionHandler(new TemplateExceptionHandler() {
                @Override
                public void handleTemplateException(TemplateException e, Environment environment, Writer writer) throws TemplateException {
                    LOGGER.error("====>>>>" + e.getFTLInstructionStack());
                    try {
                        writer.write(" ");
                    } catch (Exception ee) {
                        LOGGER.error("set template exception handler error:{}", ee);
                    }
                }
            });
        }
        if (this.sharedVariables != null && this.sharedVariables.size() > 0) {
            for (Map.Entry<String, ? extends TemplateModel> e : this.sharedVariables.entrySet()) {
                config.setSharedVariable(e.getKey(), e.getValue());
            }
        }
        LOGGER.info("post process configuration end");
    }


    public void setTemplateLoaderPath(String templateLoaderPath) {
        this.templateLoaderPath = templateLoaderPath;
    }

    public void setSharedVariables(Map<String, ? extends TemplateModel> variables) {
        this.sharedVariables = variables;
    }

    @Override
    public void setServletContext(ServletContext servletContext) {
        super.setServletContext(servletContext);
        this.servletContext = servletContext;
    }
}
