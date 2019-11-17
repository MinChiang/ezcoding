package com.ezcoding.base.web.config;

import com.ezcoding.base.web.extend.zookeeper.InterProcessMutexFactory;
import com.ezcoding.common.foundation.core.application.ApplicationMetadata;
import com.google.common.base.Joiner;
import lombok.Data;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2019-06-12 22:18
 */
@Data
@Component
@Configuration
@ConfigurationProperties(prefix = "zookeeper")
public class ZookeeperConfig {

    public static final int BASE_SLEEP_TIME_MS = 1000;
    public static final int MAX_RETRIES = 5;

    private String auth;
    private List<String> ips;

    @Autowired
    private ApplicationMetadata applicationMetadata;

    @Bean
    public CuratorFramework curatorFramework() {
        CuratorFramework client = CuratorFrameworkFactory
                .builder()
                .connectString(Joiner.on(',').join(this.ips))
                .authorization("digest", this.auth.getBytes())
                .retryPolicy(new ExponentialBackoffRetry(BASE_SLEEP_TIME_MS, MAX_RETRIES))
                .build();

        client.start();
        return client;
    }

    @Bean
    public InterProcessMutexFactory interProcessMutexFactory() {
        CuratorFramework curatorFramework = curatorFramework();
        return new InterProcessMutexFactory(curatorFramework, applicationMetadata.getCategory());
    }

}
