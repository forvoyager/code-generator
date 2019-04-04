package com.xr.code.generate.util;

import freemarker.template.Configuration;
import freemarker.template.TemplateException;

import java.io.StringWriter;

/**
 * @Time: 2019-04-02 19:37
 * @Author: forvoyager@outlook.com
 * @Description:
 */
public class FreemarkerUtils {
  private static Configuration configuration = null;

  public static Configuration getConfiguration() throws TemplateException {
    if (configuration == null) {
      if (configuration == null) {
        configuration = new Configuration(Configuration.VERSION_2_3_28);
        configuration.setDefaultEncoding("UTF-8");
        configuration.setClassicCompatible(true);
        configuration.setTemplateUpdateDelayMilliseconds(10000);
        configuration.setClassForTemplateLoading(FreemarkerUtils.class, "/tpl/");
      }
    }
    return configuration;
  }

  /**
   * 根据freemarker文件获取内容
   *
   * @param ftlFileName
   * @param rootMap
   * @return
   * @throws Exception
   */
  public static String getFtlToString(String ftlFileName, Object rootMap)
          throws Exception {
    StringWriter sw = new StringWriter();
    getConfiguration().getTemplate(ftlFileName + ".ftl").process(rootMap, sw);
    return sw.toString();
  }
}
