package com.ezcoding.generator.service.impl;

import com.ezcoding.generator.bean.PackageInfo;
import com.ezcoding.generator.constant.GeneratorConstants;
import com.ezcoding.generator.service.ICodeGenerator;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileNotFoundException;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2018-11-21 20:48
 */
public class PackageGenerator implements ICodeGenerator<PackageInfo> {

    private static final Logger LOGGER = LoggerFactory.getLogger(PackageGenerator.class);

    protected String autoBasePackage = GeneratorConstants.DEFAULT_AUTO_BASE_PACKAGE;
    protected String basePath;
    protected String packageName;

    public PackageGenerator() {
    }

    public PackageGenerator(String basePath, String packageName) {
        this.basePath = basePath;
        this.packageName = packageName;
    }

    /**
     * 生成基础信息
     *
     * @param result 处理信息
     * @return 处理信息
     */
    private void generatePackageInfo(PackageInfo result) {
        try {
            File file = ResourceUtils.getFile(basePath);
            String replace = transformPackageName(packageName);
            File dic = new File(file, replace);
            String path = dic.getPath();

            int index = StringUtils.lastIndexOf(path, autoBasePackage);
            String basePath = StringUtils.substring(path, 0, index);
            String packageName = StringUtils.replace(StringUtils.substring(path, index), File.separator, GeneratorConstants.PACKAGE_SPLITTER);

            result.setPackageFile(dic);
            result.setPackageName(packageName);
            result.setBasePath(basePath);
        } catch (FileNotFoundException e) {
            if (LOGGER.isErrorEnabled()) {
                LOGGER.error("无法找到{}", basePath);
            }
        }
    }

    /**
     * 转换包名
     * 1.将.转换成/
     * 2.全部字母小写
     *
     * @param packageName 包名
     * @return 转换后的包名
     */
    public String transformPackageName(String packageName) {
        return StringUtils.replace(StringUtils.lowerCase(packageName), GeneratorConstants.PACKAGE_SPLITTER, String.valueOf(File.separatorChar));
    }

    @Override
    public PackageInfo generate() {
        PackageInfo result = new PackageInfo();

        if (StringUtils.isBlank(basePath) || StringUtils.isBlank(packageName)) {
            if (LOGGER.isErrorEnabled()) {
                LOGGER.error("basePackage、generatePackage不能为空");
            }
            result.setResult(false);
        }
        generatePackageInfo(result);
        result.getPackageFile().mkdirs();
        return result;
    }

    public String getBasePath() {
        return basePath;
    }

    public void setBasePath(String basePath) {
        this.basePath = basePath;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }
}
