package com.ezcoding.common.foundation.core.exception.processor;

import com.ezcoding.common.foundation.core.exception.ApplicationException;
import com.ezcoding.common.foundation.core.message.head.ErrorAppHead;
import com.google.common.collect.ImmutableMap;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.AbstractHandlerExceptionResolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;
import java.util.Optional;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2020-01-19 14:54
 */
public class ApplicationExceptionResolver extends AbstractHandlerExceptionResolver {

    public static final String KEY_APPLICATION_EXPCETION = "com.ezcoding.common.foundation.core.exception.ApplicationException";

    private IApplicationExceptionProcessible processor;

    private HttpStatus defaultHttpStatus;

    private String defaultMessage;

    public ApplicationExceptionResolver(IApplicationExceptionProcessible processor, HttpStatus defaultHttpStatus, String defaultMessage) {
        this.processor = processor;
        this.defaultHttpStatus = defaultHttpStatus;
        this.defaultMessage = defaultMessage;
    }

    public ApplicationExceptionResolver(IApplicationExceptionProcessible processor) {
        this.processor = processor;
    }

    @Override
    protected ModelAndView doResolveException(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        if (!(ex instanceof ApplicationException)) {
            return null;
        }

        WebProcessContext processContext = createProcessContextWithDefaultValue(request, response, handler);
        processContext = (WebProcessContext) processor.process((ApplicationException) ex, processContext);

        HttpStatus status = Optional
                .ofNullable(processContext.getHttpStatus())
                .orElseGet(() -> defaultHttpStatus == null ? HttpStatus.INTERNAL_SERVER_ERROR : defaultHttpStatus);
        String message = Optional
                .ofNullable(processContext.getReturnSummary())
                .orElseGet(() -> defaultMessage == null ? ErrorAppHead.getDefaultErrorMessage() : defaultMessage);

        try {
            response.sendError(status.value(), message);

            Map<String, ?> model = ImmutableMap
                    .<String, Object>builder()
                    .put(KEY_APPLICATION_EXPCETION, ex)
                    .build();

            //自动打印业务错误信息
            ex.printStackTrace();
            return new ModelAndView(null, model, status);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    private WebProcessContext createProcessContextWithDefaultValue(HttpServletRequest request, HttpServletResponse response, Object handler) {
        return new WebProcessContext(request, response, handler);
    }

    public IApplicationExceptionProcessible getProcessor() {
        return processor;
    }

    public void setProcessor(IApplicationExceptionProcessible processor) {
        this.processor = processor;
    }

}
