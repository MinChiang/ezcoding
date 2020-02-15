package com.ezcoding.common.test.config;

import com.ezcoding.common.foundation.core.exception.ApplicationException;
import com.ezcoding.common.foundation.core.exception.processor.AbstractLayerModuleProcessor;
import com.ezcoding.common.foundation.core.exception.processor.ModuleApplicationExceptionManager;
import com.ezcoding.common.foundation.core.exception.processor.WebFunctionLayerModuleProcessor;
import com.ezcoding.common.foundation.core.exception.processor.WebProcessContext;
import com.ezcoding.common.foundation.starter.IApplicationExceptionProcessorConfigurer;
import org.springframework.http.HttpStatus;

import static com.ezcoding.common.foundation.core.exception.CommonExceptionConstants.COMMON_PARAM_VALIDATE_ERROR;
import static com.ezcoding.common.foundation.core.exception.CommonExceptionConstants.COMMON_REQUEST_TYPE_ERROR;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2020-01-20 14:13
 */
public class TestApplicationExceptionProcessorConfigurer implements IApplicationExceptionProcessorConfigurer {

    @Override
    public void registerApplicationProcessor(ModuleApplicationExceptionManager moduleApplicationExceptionManager, AbstractLayerModuleProcessor defaultProcessor) {
    }

    @Override
    public void registerModuleProcessor(ModuleApplicationExceptionManager moduleApplicationExceptionManager, AbstractLayerModuleProcessor defaultProcessor) {

    }

    @Override
    public void registerFunctionProcessor(ModuleApplicationExceptionManager moduleApplicationExceptionManager, AbstractLayerModuleProcessor defaultProcessor) {
        moduleApplicationExceptionManager.registerFunctionProcessor(
                COMMON_PARAM_VALIDATE_ERROR,
                new WebFunctionLayerModuleProcessor() {
                    @Override
                    public void doProcess(ApplicationException applicationException, WebProcessContext processContext) {
                        processContext.setHttpStatus(HttpStatus.LOCKED);
                        processContext.setReturnSummary("111111111111111111111111");
                    }
                },
                defaultProcessor
        );

        moduleApplicationExceptionManager.registerFunctionProcessor(
                COMMON_REQUEST_TYPE_ERROR,
                new WebFunctionLayerModuleProcessor() {
                    @Override
                    public void doProcess(ApplicationException applicationException, WebProcessContext processContext) {
                        processContext.setHttpStatus(HttpStatus.OK);
                        processContext.setReturnSummary("222222222222222222222");
                    }
                },
                defaultProcessor
        );
    }

}
