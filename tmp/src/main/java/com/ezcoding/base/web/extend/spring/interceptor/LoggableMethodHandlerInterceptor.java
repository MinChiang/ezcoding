package com.ezcoding.base.web.extend.spring.interceptor;

import com.ezcoding.common.foundation.core.message.builder.IMessageBuilder;
import com.ezcoding.common.foundation.core.message.builder.MessageBuilder;
import org.apache.commons.io.IOUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 方法注解处理器
 *
 * @author MinChiang
 * @version 1.0.0
 * @date 2018-07-22 9:05
 */
public class LoggableMethodHandlerInterceptor implements HandlerInterceptor {

    private IMessageBuilder messageBuilder = MessageBuilder.getInstance();

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (handler instanceof HandlerMethod) {
            ServletInputStream inputStream = request.getInputStream();
            byte[] bytes = IOUtils.toByteArray(inputStream);
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
    }
}
