package com.ezcoding.base.web.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.ezcoding.common.foundation.core.application.ApplicationMetadata;
import io.seata.rm.datasource.DataSourceProxy;
import io.seata.spring.annotation.GlobalTransactionScanner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2019-08-21 16:23
 */
@Configuration
public class SeataConfig {

    @Autowired
    private ApplicationMetadata metadata;

    @Bean(destroyMethod = "close", initMethod = "init")
    @ConfigurationProperties(prefix = "spring.datasource.druid")
    public DruidDataSource druidDataSource() {
        return new DruidDataSource();
    }

    @Bean("dataSource")
    @Primary
    public DataSourceProxy dataSourceProxy(DruidDataSource druidDataSource) {
        return new DataSourceProxy(druidDataSource);
    }

    @Bean
    public GlobalTransactionScanner globalTransactionScanner() {
        return new GlobalTransactionScanner(metadata.getCategory(), "my_test_tx_group");
    }

}
