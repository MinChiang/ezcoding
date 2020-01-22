package com.ezcoding.common.test.config;

import com.ezcoding.common.foundation.core.application.FunctionLayerModule;
import com.ezcoding.common.foundation.core.exception.ApplicationException;
import com.ezcoding.common.foundation.core.exception.processor.*;
import com.ezcoding.common.foundation.starter.IApplicationExceptionProcessorConfigurer;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.ezcoding.common.foundation.core.exception.ModuleConstants.DEFAULT_MODULE_LAYER_MODULE;

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
                new FunctionLayerModule(DEFAULT_MODULE_LAYER_MODULE, "11", "1"),
                new FunctionLayerModuleProcessor() {
                    @Override
                    public ProcessContext process(ApplicationException applicationException, ProcessContext processContext) {
                        if (processContext instanceof WebProcessContext) {
                            HttpServletResponse response = ((WebProcessContext) processContext).getResponse();
                            try {
                                response.sendError(200, "11111");
                                processContext.setProcessed(true);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                        return processContext;
                    }
                },
                defaultProcessor
        );

        moduleApplicationExceptionManager.registerFunctionProcessor(
                new FunctionLayerModule(DEFAULT_MODULE_LAYER_MODULE, "22", "2"),
                new FunctionLayerModuleProcessor() {
                    @Override
                    public ProcessContext process(ApplicationException applicationException, ProcessContext processContext) {
                        if (processContext instanceof WebProcessContext) {
                            HttpServletResponse response = ((WebProcessContext) processContext).getResponse();
                            try {
                                response.sendError(200, "22222");
                                processContext.setProcessed(true);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                        return processContext;
                    }
                },
                defaultProcessor
        );
    }

}
