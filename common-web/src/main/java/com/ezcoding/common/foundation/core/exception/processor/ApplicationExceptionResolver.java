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

import static com.ezcoding.common.web.error.ApplicationExceptionErrorAttributes.KEY_APPLICATION_EXPCETION;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2020-01-19 14:54
 */
public class ApplicationExceptionResolver extends AbstractHandlerExceptionResolver {

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

        try {
            WebProcessContext processContext = createProcessContextWithDefaultValue(request, response, handler);
            processContext = (WebProcessContext) processor.process((ApplicationException) ex, processContext);

            if (!processContext.isProcessed()) {
                int value = Optional
                        .ofNullable(processContext.getHttpStatus())
                        .orElseGet(() -> defaultHttpStatus == null ? HttpStatus.INTERNAL_SERVER_ERROR : defaultHttpStatus)
                        .value();

                String message = Optional
                        .ofNullable(processContext.getReturnSummary())
                        .orElseGet(() -> defaultMessage == null ? ErrorAppHead.defaultErrorMessage : defaultMessage);

                response.sendError(value, message);
            }

            Map<String, ?> model = ImmutableMap
                    .<String, Object>builder()
                    .put(KEY_APPLICATION_EXPCETION, ex)
                    .build();
            return new ModelAndView(null, model, null);
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
