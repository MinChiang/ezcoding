package com.ezcoding.common.foundation.core.exception.processor;

import com.ezcoding.common.foundation.core.application.ApplicationLayerModule;
import com.ezcoding.common.foundation.core.application.ModuleLayerModule;
import com.ezcoding.common.foundation.core.exception.ApplicationException;
import com.ezcoding.common.foundation.core.exception.ModuleExceptionCodeGenerator;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2020-01-17 10:25
 */
public class ModuleLayerModuleProcessor extends AbstractLayerModuleProcessor {

    private final Map<String, AbstractErrorSuffixCodeProcessor> errorSuffixCodeProcessors = new ConcurrentHashMap<>(0);
    private AbstractLayerModuleProcessor defaultProcessor = new EmptyApplicationExceptionProcessor();

    public ModuleLayerModuleProcessor(Map<String, AbstractErrorSuffixCodeProcessor> errorSuffixCodeProcessors, AbstractLayerModuleProcessor defaultProcessor) {
        if (errorSuffixCodeProcessors != null && errorSuffixCodeProcessors.size() > 0) {
            this.errorSuffixCodeProcessors.putAll(errorSuffixCodeProcessors);
        }
        Optional
                .ofNullable(defaultProcessor)
                .ifPresent(processor -> this.defaultProcessor = processor);
    }

    public ModuleLayerModuleProcessor(AbstractLayerModuleProcessor defaultProcessor) {
        this(null, defaultProcessor);
    }

    public ModuleLayerModuleProcessor() {
        this(null);
    }

    @Override
    public ProcessContext process(ApplicationException applicationException, ProcessContext processContext) {
        int startInclude = ApplicationLayerModule.getApplicationCodeLength() + ModuleLayerModule.getModuleCodeLength();
        int endExclude = startInclude + ModuleExceptionCodeGenerator.getErrorSuffixCodeLength();
        String errorSuffixCode = applicationException
                .getIdentification()
                .substring(startInclude, endExclude);
        AbstractErrorSuffixCodeProcessor abstractErrorSuffixCodeProcessor = errorSuffixCodeProcessors.get(errorSuffixCode);
        ProcessContext result;
        if (abstractErrorSuffixCodeProcessor == null) {
            result = defaultProcessor.process(applicationException, processContext);
        } else {
            result = abstractErrorSuffixCodeProcessor.process(applicationException, processContext);
            if (!result.isProcessed()) {
                result = defaultProcessor.process(applicationException, processContext);
            }
        }
        return result;
    }

    public void registerProcessors(Map<String, AbstractErrorSuffixCodeProcessor> processors) {
        this.errorSuffixCodeProcessors.putAll(processors);
    }

    public void registerProcessor(String errorSuffixCode, AbstractErrorSuffixCodeProcessor processor) {
        this.errorSuffixCodeProcessors.put(errorSuffixCode, processor);
    }

    public Map<String, AbstractErrorSuffixCodeProcessor> getErrorSuffixCodeProcessors() {
        return errorSuffixCodeProcessors;
    }

    public AbstractLayerModuleProcessor getDefaultProcessor() {
        return defaultProcessor;
    }

    public void setDefaultProcessor(AbstractLayerModuleProcessor defaultProcessor) {
        this.defaultProcessor = defaultProcessor;
    }

}
