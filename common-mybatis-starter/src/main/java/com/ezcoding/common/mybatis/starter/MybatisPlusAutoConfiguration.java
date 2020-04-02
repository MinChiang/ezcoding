package com.ezcoding.common.mybatis.starter;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.core.MybatisConfiguration;
import com.baomidou.mybatisplus.core.config.GlobalConfig;
import com.baomidou.mybatisplus.extension.plugins.OptimisticLockerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import com.baomidou.mybatisplus.extension.spring.MybatisSqlSessionFactoryBean;
import com.ezcoding.common.mybatis.enums.BooleanTypeEnum;
import com.ezcoding.common.mybatis.type.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.type.TypeHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2018-07-14 17:08
 */
@Configuration
@EnableTransactionManagement
public class MybatisPlusAutoConfiguration {

    @Autowired
    private ObjectMapper objectMapper;

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
        List<Interceptor> interceptors = new ArrayList<>();
        interceptors.add(paginationInterceptor);
        interceptors.add(optimisticLockerInterceptor);
        return interceptors.toArray(new Interceptor[0]);
    }

//    /**
//     * 自动注入处理器
//     *
//     * @return 自动注入处理器
//     */
//    private BaseModelMetaObjectHandler baseModelMetaObjectHandler() {
//        return new BaseModelMetaObjectHandler();
//    }

    @Bean
    public MybatisSqlSessionFactoryBean mybatisSqlSessionFactoryBean(DataSource dataSource) throws IOException {
        MybatisSqlSessionFactoryBean mybatisSqlSessionFactoryBean = new MybatisSqlSessionFactoryBean();

        PathMatchingResourcePatternResolver pathMatchingResourcePatternResolver = new PathMatchingResourcePatternResolver();
        mybatisSqlSessionFactoryBean.setMapperLocations(pathMatchingResourcePatternResolver.getResources("classpath:mybatis/mapper/**/*.xml"));
        mybatisSqlSessionFactoryBean.setTypeEnumsPackage("com.minchiang.**.model, com.ezcoding.**.model, com.minchiang.**.enums, com.ezcoding.**.enums");

        mybatisSqlSessionFactoryBean.setGlobalConfig(this.globalConfig());
        mybatisSqlSessionFactoryBean.setConfiguration(this.mybatisConfiguration());

        mybatisSqlSessionFactoryBean.setDataSource(dataSource);
        mybatisSqlSessionFactoryBean.setPlugins(this.interceptors());

        List<TypeHandler<?>> typeHandlers = baseTypeHandlers();
        mybatisSqlSessionFactoryBean.setTypeHandlers(typeHandlers.toArray(new TypeHandler[0]));
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
//        globalConfig.setMetaObjectHandler(this.baseModelMetaObjectHandler());
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
     * 注册额外的类型处理器
     *
     * @return 额外类型处理器列表
     */
    private List<TypeHandler<?>> baseTypeHandlers() {
        ArrayList<TypeHandler<?>> typeHandlers = new ArrayList<>();

        JsonTypeHandler jsonTypeHandler = new JsonTypeHandler();
        jsonTypeHandler.setObjectMapper(this.objectMapper);
        typeHandlers.add(jsonTypeHandler);

        UserStatusEnumHandler userStatusEnumHandler = new UserStatusEnumHandler();
        typeHandlers.add(userStatusEnumHandler);

        GenderEnumHandler genderEnumHandler = new GenderEnumHandler();
        typeHandlers.add(genderEnumHandler);

        DeviceTypeEnumHandler deviceTypeEnumHandler = new DeviceTypeEnumHandler();
        typeHandlers.add(deviceTypeEnumHandler);

        LoginRegisterTypeEnumHandler loginRegisterTypeEnumHandler = new LoginRegisterTypeEnumHandler();
        typeHandlers.add(loginRegisterTypeEnumHandler);

        return typeHandlers;
    }

}
