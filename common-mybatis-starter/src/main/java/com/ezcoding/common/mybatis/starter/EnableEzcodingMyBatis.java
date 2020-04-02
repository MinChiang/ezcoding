package com.ezcoding.common.mybatis.starter;

import org.mybatis.spring.annotation.MapperScan;
import org.mybatis.spring.mapper.MapperFactoryBean;
import org.springframework.beans.factory.support.BeanNameGenerator;
import org.springframework.context.annotation.Import;
import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.*;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2019-11-11 10:54
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
@Import(MybatisPlusAutoConfiguration.class)
@MapperScan
public @interface EnableEzcodingMyBatis {

    @AliasFor(
            annotation = MapperScan.class
    )
    String[] value() default {};

    @AliasFor(
            annotation = MapperScan.class
    )
    String[] basePackages() default {};

    @AliasFor(
            annotation = MapperScan.class
    )
    Class<?>[] basePackageClasses() default {};

    @AliasFor(
            annotation = MapperScan.class
    )
    Class<? extends BeanNameGenerator> nameGenerator() default BeanNameGenerator.class;

    @AliasFor(
            annotation = MapperScan.class
    )
    Class<? extends Annotation> annotationClass() default Annotation.class;

    @AliasFor(
            annotation = MapperScan.class
    )
    Class<?> markerInterface() default Class.class;

    @AliasFor(
            annotation = MapperScan.class
    )
    String sqlSessionTemplateRef() default "";

    @AliasFor(
            annotation = MapperScan.class
    )
    String sqlSessionFactoryRef() default "";

    @AliasFor(
            annotation = MapperScan.class
    )
    Class<? extends MapperFactoryBean> factoryBean() default MapperFactoryBean.class;

}
