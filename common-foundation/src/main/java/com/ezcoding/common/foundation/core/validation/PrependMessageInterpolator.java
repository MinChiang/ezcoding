package com.ezcoding.common.foundation.core.validation;

import org.hibernate.validator.internal.metadata.descriptor.ConstraintDescriptorImpl;
import org.hibernate.validator.messageinterpolation.ResourceBundleMessageInterpolator;
import org.hibernate.validator.spi.resourceloading.ResourceBundleLocator;

import java.util.Locale;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2019-08-13 17:19
 */
public class PrependMessageInterpolator extends ResourceBundleMessageInterpolator {

    public PrependMessageInterpolator() {
    }

    public PrependMessageInterpolator(ResourceBundleLocator userResourceBundleLocator) {
        super(userResourceBundleLocator);
    }

    public PrependMessageInterpolator(ResourceBundleLocator userResourceBundleLocator, ResourceBundleLocator contributorResourceBundleLocator) {
        super(userResourceBundleLocator, contributorResourceBundleLocator);
    }

    public PrependMessageInterpolator(ResourceBundleLocator userResourceBundleLocator, ResourceBundleLocator contributorResourceBundleLocator, boolean cachingEnabled) {
        super(userResourceBundleLocator, contributorResourceBundleLocator, cachingEnabled);
    }

    public PrependMessageInterpolator(ResourceBundleLocator userResourceBundleLocator, boolean cachingEnabled) {
        super(userResourceBundleLocator, cachingEnabled);
    }

    @Override
    public String interpolate(String messageTemplate, Context context) {
        String handleMessageTemplate = this.handleMessageTemplate(messageTemplate, context);
        return super.interpolate(handleMessageTemplate, context);
    }

    @Override
    public String interpolate(String messageTemplate, Context context, Locale locale) {
        String handleMessageTemplate = this.handleMessageTemplate(messageTemplate, context);
        return super.interpolate(handleMessageTemplate, context, locale);
    }

    /**
     * 处理消息模板
     *
     * @param messageTemplate 原生的消息模板
     * @param context         上下文
     * @return 处理后的消息模板
     */
    private String handleMessageTemplate(String messageTemplate, Context context) {
        if (context.getConstraintDescriptor().getPayload().contains(Default.class)) {
            return messageTemplate;
        }

        //需要作为目标插值代理进行插值替换
        Class<?> clz = ((ConstraintDescriptorImpl<?>) context.getConstraintDescriptor()).getAnnotationType();
        if (clz == null) {
            return messageTemplate;
        }

        return messageTemplate + "{" + clz.getName() + "}";
    }

}
