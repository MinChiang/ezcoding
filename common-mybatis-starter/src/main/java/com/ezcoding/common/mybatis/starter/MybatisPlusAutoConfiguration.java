package com.ezcoding.common.mybatis.starter;

import com.baomidou.mybatisplus.extension.plugins.OptimisticLockerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import com.ezcoding.common.core.user.UserLoadable;
import com.ezcoding.common.foundation.core.constant.AopConstants;
import com.ezcoding.common.mybatis.handler.BaseModelMetaObjectHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2018-07-14 17:08
 */
@Configuration
@EnableTransactionManagement(proxyTargetClass = true, order = AopConstants.Order.TRANSACTION_ORDER)
public class MybatisPlusAutoConfiguration {

    @Autowired(required = false)
    private UserLoadable loader;

    /**
     * 分页插件
     *
     * @return 分页插件
     */
    @Bean
    public PaginationInterceptor paginationInterceptor() {
        return new PaginationInterceptor();
    }

    /**
     * 乐观锁插件
     *
     * @return 乐观锁插件
     */
    @Bean
    public OptimisticLockerInterceptor optimisticLockerInterceptor() {
        return new OptimisticLockerInterceptor();
    }

    /**
     * 自动注入处理器
     *
     * @return 自动注入处理器
     */
    @Bean
    public BaseModelMetaObjectHandler baseModelMetaObjectHandler() {
        return new BaseModelMetaObjectHandler(this.loader);
    }

}
