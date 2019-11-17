package com.ezcoding.base.web.config;

import com.ezcoding.base.web.extend.validation.PrependMessageInterpolator;
import org.hibernate.validator.HibernateValidator;
import org.hibernate.validator.spi.resourceloading.ResourceBundleLocator;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.beanvalidation.LocaleContextMessageInterpolator;
import org.springframework.validation.beanvalidation.MessageSourceResourceBundleLocator;
import org.springframework.validation.beanvalidation.MethodValidationPostProcessor;

import javax.validation.MessageInterpolator;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

/**
 * 校验配置
 *
 * @author MinChiang
 * @version 1.0.0
 * @date 2018-08-08 9:23
 */
@Configuration
public class BeanValidationConfig {

    /**
     * 1.默认读取ValidationMessages.properties中的内容，注意此文件需要使用UTF-8进行编码
     * 2.开启快速校验模式，当遇到第一个校验失败时马上返回
     * 3.使用PrependMessageInterpolator默认消息插值模板，若想使用默认的消息插值方式，则在payload里面添加对应的Default.class
     *
     * @param messageSource 对应的messageSource
     * @return 校验器
     */
    @Bean
    public Validator validator(MessageSource messageSource) {
        ResourceBundleLocator resourceBundleLocator = new MessageSourceResourceBundleLocator(messageSource);
        MessageInterpolator messageInterpolator = new PrependMessageInterpolator(resourceBundleLocator);
        MessageInterpolator localeContextMessageInterpolator = new LocaleContextMessageInterpolator(messageInterpolator);

        ValidatorFactory validatorFactory = Validation.byProvider(HibernateValidator.class)
                .configure()
                //开启快速校验，只抛出第一个检测到的异常
                .failFast(true)
                .messageInterpolator(localeContextMessageInterpolator)
                .buildValidatorFactory();
        return validatorFactory.getValidator();
    }

    /**
     * 开启方法级校验机制
     * 注意事项：
     * 1.原生的spring mvc是采用resolver的方式给对象进行参数校验，校验的结果绑定在BindingResult的对象中
     * 2.项目采用非原生spring mvc的resolver进行报文解析，采用StandardMessageMethodProcessor进行解析，StandardMessageMethodProcessor类似mvc的对象校验写法，使用@Validated进行参数校验，不过没有绑定BindingResult
     * 3.在校验类上必须添加@Validated注解
     *
     * @param messageSource 对应的messageSource
     * @return 方法级校验执行器
     */
    @Bean
    public MethodValidationPostProcessor methodValidationPostProcessor(MessageSource messageSource) {
        MethodValidationPostProcessor postProcessor = new MethodValidationPostProcessor();
        postProcessor.setValidator(this.validator(messageSource));
        return postProcessor;
    }

}
