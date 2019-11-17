package com.ezcoding.account.config;

import com.ezcoding.account.extend.cos.CosSettings;
import com.qcloud.cos.COSClient;
import com.qcloud.cos.ClientConfig;
import com.qcloud.cos.auth.BasicCOSCredentials;
import com.qcloud.cos.auth.COSCredentials;
import com.qcloud.cos.region.Region;
import com.qcloud.cos.transfer.TransferManager;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2018-12-12 15:44
 */
@Configuration
public class CosConfig {

    @Value("${cos.accessKey}")
    private String accessKey;
    @Value("${cos.secretKey}")
    private String secretKey;
    @Value("${cos.regionName}")
    private String regionName;
    @Value("${cos.threadPoolNum}")
    private int threadPoolNum;

    @Value("${headerCosSettings.basePath}")
    private String headerBasePath;
    @Value("${headerCosSettings.bucketName}")
    private String headerBucketName;

    @Bean
    public TransferManager cosClient() {
        // 1 初始化用户身份信息(secretId, secretKey)
        COSCredentials cred = new BasicCOSCredentials(accessKey, secretKey);
        // 2 设置bucket的区域, COS地域的简称请参照 https://cloud.tencent.com/document/product/436/6224
        // clientConfig中包含了设置region, https(默认http), 超时, 代理等set方法, 使用可参见源码或者接口文档FAQ中说明
        ClientConfig clientConfig = new ClientConfig(new Region(regionName));
        // 3 生成cos客户端
        COSClient cosClient = new COSClient(cred, clientConfig);
        return new TransferManager(cosClient,
                new ThreadPoolExecutor(
                        threadPoolNum,
                        threadPoolNum,
                        0L,
                        TimeUnit.MILLISECONDS,
                        new LinkedBlockingQueue<Runnable>(),
                        new ThreadFactory() {
                            private static final String NAME = "COS";
                            private int count;

                            @Override
                            public Thread newThread(Runnable r) {
                                Thread thread = new Thread(r, NAME + count);
                                count++;
                                return thread;
                            }
                        }
                )
        );
    }

    @Bean(name = "headerCosSettings")
    public CosSettings headerCosSettings() {
        CosSettings cosSettings = new CosSettings();
        cosSettings.setBasePath(headerBasePath);
        cosSettings.setBucketName(headerBucketName);
        String wholePath = doGetWholePath(headerBucketName, regionName, headerBasePath);
        cosSettings.setWholeBasePath(wholePath);
        return cosSettings;
    }

    /**
     * 获取资源的全路径
     *
     * @param bucketName 桶名称
     * @param regionName 区域名称
     * @param basePath   资源基本路径
     * @return 资源全路径
     */
    private String doGetWholePath(String bucketName, String regionName, String basePath) {
        return "https://" + headerBucketName + ".cos." + regionName + ".myqcloud.com/" + headerBasePath;
    }

}
