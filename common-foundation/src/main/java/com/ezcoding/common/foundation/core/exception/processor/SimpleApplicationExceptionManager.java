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

    private Map<String, IApplicationExceptionProcessible> processors = new ConcurrentHashMap<>(0);

    public SimpleApplicationExceptionManager(Map<String, IApplicationExceptionProcessible> processors) {
        this.registerProcessors(processors);
    }

    public SimpleApplicationExceptionManager() {
    }

    @Override
    public boolean canProcessible(ApplicationException applicationException) {
        return processors.containsKey(applicationException.getIdentification());
    }

    @Override
    public ProcessContext process(ApplicationException applicationException) {
        ProcessContext processContext = new ProcessContext();
        Optional
                .of(applicationException.getIdentification())
                .map(processors::get)
                .filter(p -> p.canProcessible(applicationException))
                .ifPresent(p -> {
                    p.process(applicationException);
                    processContext.setProcessed(true);
                });
        return processContext;
    }

    public Map<String, IApplicationExceptionProcessible> getProcessors() {
        return processors;
    }

    /**
     * 注册处理器
     *
     * @param processors 被注册的处理器
     */
    public void registerProcessors(Map<String, IApplicationExceptionProcessible> processors) {
        this.processors.putAll(processors);
    }

}
