package com.ezcoding.common.foundation.core.exception.processor;

import com.ezcoding.common.foundation.core.application.ModuleNameable;
import com.ezcoding.common.foundation.core.exception.ApplicationException;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2020-01-17 10:16
 */
public class ApplicationLayerModuleProcessor extends AbstractLayerModuleProcessor {

    private final Map<String, ModuleLayerModuleProcessor> moduleLayerModuleProcessors = new ConcurrentHashMap<>(0);
    private AbstractLayerModuleProcessor defaultProcessor = new EmptyApplicationExceptionProcessor();

    public ApplicationLayerModuleProcessor(Map<String, ModuleLayerModuleProcessor> moduleLayerModuleProcessors, AbstractLayerModuleProcessor defaultProcessor) {
        if (moduleLayerModuleProcessors != null && moduleLayerModuleProcessors.size() > 0) {
            this.moduleLayerModuleProcessors.putAll(moduleLayerModuleProcessors);
        }
        Optional
                .ofNullable(defaultProcessor)
                .ifPresent(processor -> this.defaultProcessor = processor);
    }

    public ApplicationLayerModuleProcessor(AbstractLayerModuleProcessor defaultProcessor) {
        this(null, defaultProcessor);
    }

    public ApplicationLayerModuleProcessor() {
        this(null);
    }

    @Override
    public ProcessContext process(ApplicationException applicationException, ProcessContext processContext) {
        String moduleCode = applicationException
                .getIdentification()
                .substring(ModuleNameable.APPLICATION_CODE_LENGTH, MODULE_BEGIN);
        ModuleLayerModuleProcessor moduleLayerModuleProcessor = moduleLayerModuleProcessors.get(moduleCode);
        ProcessContext result;
        if (moduleLayerModuleProcessor == null) {
            result = defaultProcessor.process(applicationException, processContext);
        } else {
            result = moduleLayerModuleProcessor.process(applicationException, processContext);
            if (!result.isProcessed()) {
                result = defaultProcessor.process(applicationException, processContext);
            }
        }
        return result;
    }

    public void registerProcessors(Map<String, ModuleLayerModuleProcessor> processors) {
        this.moduleLayerModuleProcessors.putAll(processors);
    }

    public void registerProcessor(String moduleCode, ModuleLayerModuleProcessor processor) {
        this.moduleLayerModuleProcessors.put(moduleCode, processor);
    }

    public Map<String, ModuleLayerModuleProcessor> getModuleLayerModuleProcessors() {
        return moduleLayerModuleProcessors;
    }

    public AbstractLayerModuleProcessor getDefaultProcessor() {
        return defaultProcessor;
    }

    public void setDefaultProcessor(AbstractLayerModuleProcessor defaultProcessor) {
        this.defaultProcessor = defaultProcessor;
    }

}
