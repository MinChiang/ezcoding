package com.ezcoding.common.foundation.core.exception.processor;

import com.ezcoding.common.foundation.core.application.ApplicationLayerModule;
import com.ezcoding.common.foundation.core.application.FunctionLayerModule;
import com.ezcoding.common.foundation.core.application.ModuleLayerModule;
import com.ezcoding.common.foundation.core.exception.ApplicationException;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2020-01-17 10:25
 */
public class ModuleLayerModuleProcessor extends AbstractLayerModuleProcessor {

    private Map<String, FunctionLayerModuleProcessor> functionLayerModuleProcessors = new ConcurrentHashMap<>(0);
    private AbstractLayerModuleProcessor defaultProcessor = new EmptyApplicationExceptionProcessor();

    public ModuleLayerModuleProcessor(Map<String, FunctionLayerModuleProcessor> functionLayerModuleProcessors, AbstractLayerModuleProcessor defaultProcessor) {
        if (functionLayerModuleProcessors != null && functionLayerModuleProcessors.size() > 0) {
            this.functionLayerModuleProcessors.putAll(functionLayerModuleProcessors);
        }
        Optional
                .of(defaultProcessor)
                .ifPresent(p -> this.defaultProcessor = defaultProcessor);
    }

    public ModuleLayerModuleProcessor(AbstractLayerModuleProcessor defaultProcessor) {
        this(null, defaultProcessor);
    }

    public ModuleLayerModuleProcessor() {
        this(null);
    }

    @Override
    public ProcessContext process(ApplicationException applicationException) {
        int startInclude = ApplicationLayerModule.getApplicationCodeLength() + ModuleLayerModule.getModuleCodeLength();
        int endExclude = startInclude + FunctionLayerModule.getFunctionCodeLength();
        String functionCode = applicationException
                .getIdentification()
                .substring(startInclude, endExclude);
        FunctionLayerModuleProcessor functionLayerModuleProcessor = functionLayerModuleProcessors.get(functionCode);
        ProcessContext result;
        if (functionLayerModuleProcessor == null) {
            result = defaultProcessor.process(applicationException);
        } else {
            result = functionLayerModuleProcessor.process(applicationException);
            if (!result.isProcessed()) {
                result = defaultProcessor.process(applicationException);
            }
        }
        return result;
    }

    public void registerProcessors(Map<String, FunctionLayerModuleProcessor> processors) {
        this.functionLayerModuleProcessors.putAll(processors);
    }

    public void registerProcessor(String functionCode, FunctionLayerModuleProcessor processor) {
        this.functionLayerModuleProcessors.put(functionCode, processor);
    }

    public Map<String, FunctionLayerModuleProcessor> getFunctionLayerModuleProcessors() {
        return functionLayerModuleProcessors;
    }

    public AbstractLayerModuleProcessor getDefaultProcessor() {
        return defaultProcessor;
    }

    public void setDefaultProcessor(AbstractLayerModuleProcessor defaultProcessor) {
        this.defaultProcessor = defaultProcessor;
    }

}
