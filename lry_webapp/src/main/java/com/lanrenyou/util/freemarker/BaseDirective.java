package com.lanrenyou.util.freemarker;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;
import java.util.regex.Pattern;

import freemarker.core.Environment;
import freemarker.ext.beans.ArrayModel;
import freemarker.ext.beans.BeansWrapper;
import freemarker.ext.beans.StringModel;
import freemarker.template.SimpleSequence;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateDirectiveModel;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;
import freemarker.template.TemplateModelException;

/**
 * @author 许健
 *
 */
public class BaseDirective implements TemplateDirectiveModel {
    /**
     * @throws TemplateModelException
     *
     */
    protected void requireJs(Environment env, String path) throws TemplateModelException {

        SimpleSequence pageImported = (SimpleSequence) env.getVariable("importedAssets");

        ArrayModel requiredJsModel = (ArrayModel) env.getVariable("requiredJs");
        ArrayList<String> requiredJsArray = new ArrayList<String>();

        if (requiredJsModel == null) {
            requiredJsArray.add(path);
            env.setVariable("requiredJs", new ArrayModel(requiredJsArray.toArray(), BeansWrapper.getDefaultInstance()));
        } else {
            for (int i = 0; i < requiredJsModel.size(); i++) {
                requiredJsArray.add(requiredJsModel.get(i).toString());
            }

            Boolean matched = false;

            /*
             * 2010-1-13 许健 加入检查page imported的过程 page imported是在FTL文件<@page
             * assets=[] />加入的引入项, 包含CSS及JS。 至此, requireJs可检查所有脚本资源入口
             */
            if (pageImported != null) {
                for (int i = 0; i < pageImported.size(); i++) {
                    if (pageImported.get(i).toString().equals(path)) {
                        matched = true;
                    }
                }
            }

            for (String item : requiredJsArray) {
                if (item.equals(path)) {
                    matched = true;
                }
            }
            if (!matched) {
                requiredJsArray.add(path);
                env.setVariable("requiredJs", new ArrayModel(requiredJsArray.toArray(), BeansWrapper.getDefaultInstance()));
            }
        }
    }

    /**
     *
     */
    protected void requireCss(Environment env, String path) {

    }

    /**
     * 依据给定Macro attribute解析为正确的JavaScript event handler 给定JavaScript标识符则给出为原文,
     * 给定JavaScript语句则给出function enclosure, 给定function closure则原样输出
     *
     * @return
     */
    protected String parseEventHandler(Map<String, TemplateModel> params, String key) {
        String result;
        if (!params.containsKey(key)) {
            result = "";
        } else {
            result = params.get(key).toString();
            if (!Pattern.matches("^\\w+$", result)) {
                if (!result.startsWith("function(")) {
                    result = "function(){" + result + "}";
                }
            }
        }
        return result;

    }

    protected void hold(Environment env, String script) throws TemplateModelException {

        StringModel deposit = (StringModel) env.getVariable("holderDeposit");
        String content = "";
        if (deposit != null) {
            content = deposit.getAsString();
        }

        content += script;

        env.setVariable("holderDeposit", new StringModel(content, BeansWrapper.getDefaultInstance()));
    }

    /*
     * (non-Javadoc)
     *
     * @seefreemarker.template.TemplateDirectiveModel#execute(freemarker.core.
     * Environment, java.util.Map, freemarker.template.TemplateModel[],
     * freemarker.template.TemplateDirectiveBody)
     */
    @Override
    public void execute(Environment env, Map attribs, TemplateModel[] loopVars, TemplateDirectiveBody body) throws TemplateException, IOException {

    }

}
