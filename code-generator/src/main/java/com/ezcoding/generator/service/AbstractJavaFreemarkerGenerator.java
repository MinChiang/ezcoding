package com.ezcoding.generator.service;

import com.ezcoding.common.foundation.util.BeanUtils;
import com.ezcoding.generator.bean.JavaInfo;
import com.ezcoding.generator.bean.PackageInfo;
import com.ezcoding.generator.constant.GeneratorConstants;
import com.ezcoding.generator.service.impl.PackageGenerator;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.TemplateExceptionHandler;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2018-11-21 21:40
 */
public abstract class AbstractJavaFreemarkerGenerator implements ICodeGenerator<JavaInfo> {

    private static final Logger LOGGER = LoggerFactory.getLogger(PackageGenerator.class);

    protected static Configuration configuration;

    protected Map<String, Object> context;
    protected String templateName;
    protected String basePath;
    protected String wholeJavaName;

    public AbstractJavaFreemarkerGenerator() {
    }

    public AbstractJavaFreemarkerGenerator(Map<String, Object> context, String templateName, String basePath, String wholeJavaName) {
        this.context = context;
        this.templateName = templateName;
        this.basePath = basePath;
        this.wholeJavaName = wholeJavaName;
        if (configuration == null) {
            init();
        }
    }

    private void init() {
        configuration = new Configuration(Configuration.VERSION_2_3_28);
        try {
            File file = ResourceUtils.getFile("classpath:generator");
            configuration.setDirectoryForTemplateLoading(file);
            configuration.setDefaultEncoding(GeneratorConstants.DEFAULT_CHARSET);
            configuration.setTemplateExceptionHandler(TemplateExceptionHandler.DEBUG_HANDLER);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private boolean check() {
        return templateName != null
                && basePath != null
                && wholeJavaName != null;
    }

    @Override
    public JavaInfo generate() {
        JavaInfo javaInfo = new JavaInfo();
        javaInfo.setResult(false);
        try {
            if (!check()) {
                if (LOGGER.isErrorEnabled()) {
                    LOGGER.error("参数校验不正确，templateName、basePath、wholeJavaName不能为空");
                }
                return javaInfo;
            }

            int index = StringUtils.lastIndexOf(wholeJavaName, GeneratorConstants.PACKAGE_SPLITTER);
            String packageName = index == -1 ? wholeJavaName : StringUtils.substring(wholeJavaName, 0, index);
            String javaName = StringUtils.capitalize(StringUtils.substring(wholeJavaName, index + 1));

            PackageGenerator packageGenerator = new PackageGenerator(basePath, packageName);
            PackageInfo packageInfo = packageGenerator.generate();
            if (!packageInfo.result()) {

            }

            File packageFile = packageInfo.getPackageFile();
            File javaFile = new File(packageFile, javaName + GeneratorConstants.JAVA_SUFFIX);
            javaFile.createNewFile();

            BeanUtils.copy(packageInfo, javaInfo);
            javaInfo.setJavaName(javaName);
            javaInfo.setJavaFile(javaFile);
            this.extGenerate(javaInfo);

            Template template = configuration.getTemplate(templateName, GeneratorConstants.DEFAULT_CHARSET);
            template.process(this.context, new FileWriter(javaFile));
            javaInfo.setResult(true);
        } catch (IOException | TemplateException e) {
            e.printStackTrace();
        }
        return javaInfo;
    }

    /**
     * 设置值扩展接口
     *
     * @param javaInfo 生成信息
     */
    public abstract void extGenerate(JavaInfo javaInfo);

    public Map<String, Object> getContext() {
        return context;
    }

    public void setContext(Map<String, Object> context) {
        this.context = context;
    }

    public String getTemplateName() {
        return templateName;
    }

    public void setTemplateName(String templateName) {
        this.templateName = templateName;
    }

    public String getBasePath() {
        return basePath;
    }

    public void setBasePath(String basePath) {
        this.basePath = basePath;
    }

    public String getWholeJavaName() {
        return wholeJavaName;
    }

    public void setWholeJavaName(String wholeJavaName) {
        this.wholeJavaName = wholeJavaName;
    }
}
