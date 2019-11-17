package com.ezcoding.base.web.config;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.core.MybatisConfiguration;
import com.baomidou.mybatisplus.core.config.GlobalConfig;
import com.baomidou.mybatisplus.extension.plugins.OptimisticLockerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import com.baomidou.mybatisplus.extension.spring.MybatisSqlSessionFactoryBean;
import com.ezcoding.base.web.extend.mybatis.handler.BaseModelMetaObjectHandler;
import com.ezcoding.base.web.extend.mybatis.type.BooleanTypeEnum;
import com.ezcoding.base.web.extend.mybatis.type.JsonTypeHandler;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Lists;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.io.IOException;
import java.util.List;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2018-07-14 17:08
 */
@Configuration
@EnableTransactionManagement
@MapperScan({"com.ezcoding.**.dao"})
public class MybatisPlusConfig {

    /**
     * 分页插件
     *
     * @return 分页插件
     */
    private PaginationInterceptor paginationInterceptor() {
        return new PaginationInterceptor();
    }

    /**
     * 乐观锁插件
     *
     * @return 乐观锁插件
     */
    private OptimisticLockerInterceptor optimisticLockerInterceptor() {
        return new OptimisticLockerInterceptor();
    }

    /**
     * 乐观锁插件
     *
     * @return 乐观锁插件
     */
    private Interceptor[] interceptors() {
        PaginationInterceptor paginationInterceptor = this.paginationInterceptor();
        OptimisticLockerInterceptor optimisticLockerInterceptor = this.optimisticLockerInterceptor();
        List<Interceptor> interceptors = Lists.newArrayList();
        interceptors.add(paginationInterceptor);
        interceptors.add(optimisticLockerInterceptor);
        return interceptors.toArray(new Interceptor[interceptors.size()]);
    }

    /**
     * 自动注入处理器
     *
     * @return 自动注入处理器
     */
    private BaseModelMetaObjectHandler baseModelMetaObjectHandler() {
        return new BaseModelMetaObjectHandler();
    }

    @Bean
    public MybatisSqlSessionFactoryBean mybatisSqlSessionFactoryBean(DataSource dataSource) throws IOException {
        MybatisSqlSessionFactoryBean mybatisSqlSessionFactoryBean = new MybatisSqlSessionFactoryBean();

        PathMatchingResourcePatternResolver pathMatchingResourcePatternResolver = new PathMatchingResourcePatternResolver();
        mybatisSqlSessionFactoryBean.setMapperLocations(pathMatchingResourcePatternResolver.getResources("classpath:mybatis/mapper/**/*.xml"));
        mybatisSqlSessionFactoryBean.setTypeEnumsPackage("com.ezcoding.**");

        mybatisSqlSessionFactoryBean.setGlobalConfig(this.globalConfig());
        mybatisSqlSessionFactoryBean.setConfiguration(this.mybatisConfiguration());

        mybatisSqlSessionFactoryBean.setDataSource(dataSource);
        mybatisSqlSessionFactoryBean.setPlugins(this.interceptors());
        return mybatisSqlSessionFactoryBean;
    }

    private GlobalConfig globalConfig() {
        GlobalConfig globalConfig = new GlobalConfig();

        GlobalConfig.DbConfig dbConfig = new GlobalConfig.DbConfig();
        //设置主键类型：表自动增长
        dbConfig.setIdType(IdType.AUTO);
        //逻辑删除配置
        dbConfig.setLogicDeleteValue(String.valueOf(BooleanTypeEnum.TRUE.getId()));
        dbConfig.setLogicNotDeleteValue(String.valueOf(BooleanTypeEnum.FALSE.getId()));
        globalConfig.setDbConfig(dbConfig);
        //设置自动注入元数据
        globalConfig.setMetaObjectHandler(this.baseModelMetaObjectHandler());
        //关闭标签
        globalConfig.setBanner(false);
        return globalConfig;
    }

    private MybatisConfiguration mybatisConfiguration() {
        MybatisConfiguration mybatisConfiguration = new MybatisConfiguration();
        mybatisConfiguration.setMapUnderscoreToCamelCase(true);
        return mybatisConfiguration;
    }

    /**
     * 独立内部类进行数据装载，解决循环依赖的问题
     */
    @Configuration
    class InnerMybatisConfig implements InitializingBean {

        @Autowired
        private ObjectMapper objectMapper;

        @Autowired
        private SqlSessionFactory sqlSessionFactory;

        @Override
        public void afterPropertiesSet() {
            JsonTypeHandler jsonTypeHandler = new JsonTypeHandler();
            jsonTypeHandler.setObjectMapper(this.objectMapper);
            this.sqlSessionFactory.getConfiguration().getTypeHandlerRegistry().register(jsonTypeHandler);
        }

    }
}
