package com.ezcoding.common.foundation.core.exception.processor;

import com.ezcoding.common.foundation.core.exception.ApplicationException;
import com.ezcoding.common.foundation.core.message.ErrorAppHead;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.AbstractHandlerExceptionResolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2020-01-19 14:54
 */
public class ApplicationExceptionResolver extends AbstractHandlerExceptionResolver {

    private static final Logger LOGGER = LoggerFactory.getLogger(ApplicationExceptionResolver.class);

    public static final String KEY_APPLICATION_EXPCETION = "com.ezcoding.common.foundation.core.exception.ApplicationException";

    private ApplicationExceptionProcessible processor;

    private HttpStatus defaultHttpStatus;

    private String defaultMessage;

    public ApplicationExceptionResolver(ApplicationExceptionProcessible processor, HttpStatus defaultHttpStatus, String defaultMessage) {
        this.processor = processor;
        this.defaultHttpStatus = defaultHttpStatus;
        this.defaultMessage = defaultMessage;
    }

    public ApplicationExceptionResolver(ApplicationExceptionProcessible processor) {
        this.processor = processor;
    }

    @Override
    protected ModelAndView doResolveException(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        if (!(ex instanceof ApplicationException)) {
            return null;
        }

        WebProcessContext processContext = createProcessContextWithDefaultValue(request, response, handler);
        processContext = (WebProcessContext) processor.process((ApplicationException) ex, processContext);

        HttpStatus processStatus;
        String processMessage;
        if (processContext.isProcessed()) {
            processStatus = processContext.getHttpStatus();
            processMessage = processContext.getReturnSummary();
        } else {
            processStatus = (defaultHttpStatus == null ? HttpStatus.INTERNAL_SERVER_ERROR : defaultHttpStatus);
            processMessage = (defaultMessage == null ? ErrorAppHead.DEFAULT_ERROR_MESSAGE : defaultMessage);
        }

        try {
            response.sendError(
                    processStatus.value(),
                    processContext.isProcessed() ? processMessage : ((ApplicationException) ex).getSummary()
            );

            Map<String, Exception> model = new HashMap<>();
            model.put(KEY_APPLICATION_EXPCETION, ex);

            //自动打印业务错误信息
            LOGGER.error("applicatin error: ", ex);
            return new ModelAndView(null, model, processStatus);
        } catch (IOException e) {
            LOGGER.error("io error: ", e);
        }

        return null;
    }

    /**
     * 创建处理器上下文
     *
     * @param request  请求
     * @param response 响应
     * @param handler  响应处理器
     * @return 处理器上下文
     */
    protected WebProcessContext createProcessContextWithDefaultValue(HttpServletRequest request, HttpServletResponse response, Object handler) {
        return new WebProcessContext(request, response, handler);
    }

    public ApplicationExceptionProcessible getProcessor() {
        return processor;
    }

    public void setProcessor(ApplicationExceptionProcessible processor) {
        this.processor = processor;
    }

}
