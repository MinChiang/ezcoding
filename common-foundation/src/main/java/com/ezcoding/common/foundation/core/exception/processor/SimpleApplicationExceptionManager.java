package com.ezcoding.common.foundation.core.exception.processor;

import com.ezcoding.common.foundation.core.exception.ApplicationException;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 简单错误处理器
 *
 * @author MinChiang
 * @version 1.0.0
 * @date 2020-01-17 9:16
 */
public class SimpleApplicationExceptionManager extends AbstractApplicationExceptionManager {

    private final Map<String, ApplicationExceptionProcessible> processors = new ConcurrentHashMap<>(0);

    public SimpleApplicationExceptionManager(Map<String, ApplicationExceptionProcessible> processors) {
        this.registerProcessors(processors);
    }

    public SimpleApplicationExceptionManager() {
    }

    @Override
    public boolean canProcess(ApplicationException applicationException) {
        return processors.containsKey(applicationException.getIdentification());
    }

    @Override
    public ProcessContext process(ApplicationException applicationException, ProcessContext processContext) {
        Optional
                .of(applicationException.getIdentification())
                .map(processors::get)
                .filter(p -> p.canProcess(applicationException))
                .ifPresent(p -> {
                    p.process(applicationException, processContext);
                    processContext.setProcessed(true);
                });
        return processContext;
    }

    public Map<String, ApplicationExceptionProcessible> getProcessors() {
        return processors;
    }

    /**
     * 注册处理器
     *
     * @param processors 被注册的处理器
     */
    public void registerProcessors(Map<String, ApplicationExceptionProcessible> processors) {
        this.processors.putAll(processors);
    }

}
