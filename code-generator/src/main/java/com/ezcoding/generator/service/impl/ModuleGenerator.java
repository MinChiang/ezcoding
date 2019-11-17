package com.ezcoding.generator.service.impl;

import com.ezcoding.generator.bean.ModuleInfo;
import com.ezcoding.generator.bean.PackageInfo;
import com.ezcoding.generator.constant.GeneratorConstants;
import com.ezcoding.generator.service.ICodeGenerator;
import com.google.common.collect.Maps;
import org.apache.commons.lang3.StringUtils;

import java.util.Map;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2018-11-22 9:29
 */
public class ModuleGenerator implements ICodeGenerator<ModuleInfo> {

    private String author;
    private String moduleNum;
    private String basePath;
    private String wholeModuleName;
    private String moduleName;
    private Map<String, Object> context;

    public ModuleGenerator(String basePath, String wholeModuleName, String author, String moduleNum) {
        this(Maps.newHashMap(), basePath, wholeModuleName, author, moduleNum);
    }

    public ModuleGenerator(Map<String, Object> context, String basePath, String wholeModuleName, String author, String moduleNum) {
        this.context = context;
        this.basePath = basePath;
        this.wholeModuleName = wholeModuleName;
        this.moduleName = StringUtils.substringAfterLast(this.wholeModuleName, GeneratorConstants.PACKAGE_SPLITTER);
        this.author = author;
        this.moduleNum = moduleNum;
    }

    @Override
    public ModuleInfo generate() {
        ModuleInfo result = new ModuleInfo();

        //生成模块文件夹
        PackageGenerator packageGenerator = new PackageGenerator(this.basePath, this.wholeModuleName);
        PackageInfo packageInfo = packageGenerator.generate();

        //生成对应的子文件夹内容
        for (String packageName : GeneratorConstants.PACKAGE_GROUP) {
            PackageGenerator tmp = new PackageGenerator(packageInfo.getPackageFile().getPath(), packageName);
            tmp.generate();
        }

        Map<String, Object> map = Maps.newHashMap();

        //生成constants文件
        String constantsJavaName = this.wholeModuleName + GeneratorConstants.PACKAGE_SPLITTER + GeneratorConstants.CONSTANTS_NAME + GeneratorConstants.PACKAGE_SPLITTER + this.moduleName;
        ConstantsGenerator constantsGenerator = new ConstantsGenerator(map, this.basePath, constantsJavaName, this.author);
        constantsGenerator.generate();

        //生成exception文件
        String exceptionJavaName = this.wholeModuleName + GeneratorConstants.PACKAGE_SPLITTER + GeneratorConstants.EXCEPTION_NAME + GeneratorConstants.PACKAGE_SPLITTER + this.moduleName;
        ExceptionGenerator exceptionGenerator = new ExceptionGenerator(context, this.basePath, exceptionJavaName, this.author, this.moduleNum);
        exceptionGenerator.generate();
        exceptionJavaName += GeneratorConstants.EXCEPTION_SUFFIX;
        constantsGenerator = new ConstantsGenerator(map, this.basePath, exceptionJavaName, this.author);
        constantsGenerator.generate();

        result.setResult(true);
        return result;
    }
}
