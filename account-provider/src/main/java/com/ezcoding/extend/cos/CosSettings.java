package com.ezcoding.extend.cos;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2018-12-14 13:58
 */
public class CosSettings {

    /**
     * 资源http全路径
     */
    private String wholeBasePath;

    /**
     * 桶名称
     */
    private String bucketName;

    /**
     * 存放图片的基础路径
     */
    private String basePath;

    public String getBucketName() {
        return bucketName;
    }

    public void setBucketName(String bucketName) {
        this.bucketName = bucketName;
    }

    public String getBasePath() {
        return basePath;
    }

    public void setBasePath(String basePath) {
        this.basePath = basePath;
    }

    public String getWholeBasePath() {
        return wholeBasePath;
    }

    public void setWholeBasePath(String wholeBasePath) {
        this.wholeBasePath = wholeBasePath;
    }
}
