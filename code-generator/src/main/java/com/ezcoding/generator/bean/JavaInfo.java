package com.ezcoding.generator.bean;

import java.io.File;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2018-11-21 23:12
 */
public class JavaInfo extends PackageInfo {

    private String javaName;
    private String author;
    private File javaFile;

    public String getJavaName() {
        return javaName;
    }

    public void setJavaName(String javaName) {
        this.javaName = javaName;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public File getJavaFile() {
        return javaFile;
    }

    public void setJavaFile(File javaFile) {
        this.javaFile = javaFile;
    }
}
