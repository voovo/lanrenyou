package com.lanrenyou.util.freemarker;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.ModelAndView;

import freemarker.ext.beans.BeansWrapper;
import freemarker.template.TemplateHashModel;
import freemarker.template.TemplateModelException;

public class FreemarkerUtil {

	private static Logger logger = LoggerFactory.getLogger(FreemarkerUtil.class);
	
	/**
	 * 导入枚举到ftl中
	 * @param enumClass
	 * @param model
	 */
    @SuppressWarnings({ "rawtypes" })
	public static void importEnum2Ftl(Class enumClass, ModelAndView mv) {
    	importEnum2Ftl(enumClass, mv.getModelMap());
    }

	public static void importEnum2Ftl(Class enumClass, Map<String, Object> modelMap) {
        try {
            String classFullName = enumClass.getName();
            Integer lastPoint = classFullName.lastIndexOf('.');
            String className = classFullName.substring(lastPoint + 1);
            BeansWrapper wrapper = BeansWrapper.getDefaultInstance();
            TemplateHashModel enumModels = wrapper.getEnumModels();
            TemplateHashModel enumS = (TemplateHashModel) enumModels.get(classFullName);
            modelMap.put(className, enumS);
        } catch (TemplateModelException templateModelException) {
            logger.error("import enum to ftl error, msg:{}", templateModelException);
        }
    }
}
