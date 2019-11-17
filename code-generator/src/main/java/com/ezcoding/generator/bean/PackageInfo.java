package com.ezcoding.generator.bean;

import java.io.File;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2018-11-22 14:38
 */
public class PackageInfo extends GenerateResult {

    /**
     * 包所在的相对路径
     */
    private String basePath;

    /**
     * 包路径全程
     */
    private String packageName;

    /**
     * 包文件
     */
    private File packageFile;

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public File getPackageFile() {
        return packageFile;
    }

    public void setPackageFile(File packageFile) {
        this.packageFile = packageFile;
    }

    public String getBasePath() {
        return basePath;
    }

    public void setBasePath(String basePath) {
        this.basePath = basePath;
    }
}
