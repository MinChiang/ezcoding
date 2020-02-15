package com.ezcoding.common.foundation.core.exception.processor;

import com.ezcoding.common.foundation.core.application.ApplicationLayerModule;
import com.ezcoding.common.foundation.core.application.FunctionLayerModule;
import com.ezcoding.common.foundation.core.application.IModuleNameable;
import com.ezcoding.common.foundation.core.application.ModuleLayerModule;
import com.ezcoding.common.foundation.core.exception.ApplicationException;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2020-01-17 9:48
 */
public class ModuleApplicationExceptionManager extends AbstractApplicationExceptionManager {

    private Map<String, ApplicationLayerModuleProcessor> applicationLayerModuleProcessors = new ConcurrentHashMap<>(0);
    private AbstractLayerModuleProcessor defaultProcessor = new EmptyApplicationExceptionProcessor();

    public ModuleApplicationExceptionManager(AbstractLayerModuleProcessor defaultProcessor) {
        Optional
                .ofNullable(defaultProcessor)
                .ifPresent(processor -> this.defaultProcessor = processor);
    }

    public ModuleApplicationExceptionManager() {
        this(null);
    }

    @Override
    public boolean canProcessible(ApplicationException applicationException) {
        return false;
    }

    @Override
    public ProcessContext process(ApplicationException applicationException, ProcessContext processContext) {
        String applicationCode = applicationException
                .getIdentification()
                .substring(0, ApplicationLayerModule.getApplicationCodeLength());
        ApplicationLayerModuleProcessor applicationLayerModuleProcessor = applicationLayerModuleProcessors.get(applicationCode);
        ProcessContext result;
        if (applicationLayerModuleProcessor == null) {
            result = defaultProcessor.process(applicationException, processContext);
        } else {
            result = applicationLayerModuleProcessor.process(applicationException, processContext);
            if (!result.isProcessed()) {
                result = defaultProcessor.process(applicationException, processContext);
            }
        }
        return result;
    }

    private void checkParams(IModuleNameable moduleNameable, AbstractLayerModuleProcessor abstractLayerModuleProcessor) {
        if (moduleNameable == null || abstractLayerModuleProcessor == null) {
            throw new NullPointerException("模块和错误处理器不能为空");
        }
    }

    /**
     * 注册应用级别的错误处理器
     *
     * @param applicationLayerModule 服务模块
     * @param processor              错误处理器
     * @param defaultProcessor       默认层级处理器
     */
    public void registerApplicationProcessor(ApplicationLayerModule applicationLayerModule, ApplicationLayerModuleProcessor processor, AbstractLayerModuleProcessor defaultProcessor) {
        checkParams(applicationLayerModule, processor);
        applicationLayerModuleProcessors.put(applicationLayerModule.getApplicationCode(), processor);
        Optional
                .ofNullable(defaultProcessor)
                .ifPresent(d -> this.defaultProcessor = d);
    }

    /**
     * 注册应用级别的错误处理器
     *
     * @param applicationLayerModule 服务模块
     * @param processor              错误处理器
     */
    public void registerApplicationProcessor(ApplicationLayerModule applicationLayerModule, ApplicationLayerModuleProcessor processor) {
        registerApplicationProcessor(applicationLayerModule, processor, null);
    }

    /**
     * 注册业务级别的错误处理器
     *
     * @param moduleLayerModule 业务模块
     * @param processor         错误处理器
     * @param defaultProcessor  默认层级处理器
     */
    public void registerModuleProcessor(ModuleLayerModule moduleLayerModule, ModuleLayerModuleProcessor processor, AbstractLayerModuleProcessor defaultProcessor) {
        checkParams(moduleLayerModule, processor);
        applicationLayerModuleProcessors
                .computeIfAbsent(moduleLayerModule.getApplicationCode(), (key) -> new ApplicationLayerModuleProcessor(defaultProcessor))
                .registerProcessor(moduleLayerModule.getModuleCode(), processor);
    }

    /**
     * 注册业务级别的错误处理器
     *
     * @param moduleLayerModule 业务模块
     * @param processor         错误处理器
     */
    public void registerModuleProcessor(ModuleLayerModule moduleLayerModule, ModuleLayerModuleProcessor processor) {
        registerModuleProcessor(moduleLayerModule, processor, null);
    }

    /**
     * 注册功能级别的错误处理器
     *
     * @param functionLayerModule 功能模块
     * @param processor           错误处理器
     * @param defaultProcessor    默认层级处理器
     */
    public void registerFunctionProcessor(FunctionLayerModule functionLayerModule, AbstractFunctionLayerModuleProcessor processor, AbstractLayerModuleProcessor defaultProcessor) {
        checkParams(functionLayerModule, processor);
        applicationLayerModuleProcessors
                .computeIfAbsent(functionLayerModule.getApplicationCode(), (key) -> new ApplicationLayerModuleProcessor(null))
                .getModuleLayerModuleProcessors()
                .computeIfAbsent(functionLayerModule.getModuleCode(), (key) -> new ModuleLayerModuleProcessor(defaultProcessor))
                .registerProcessor(functionLayerModule.getFunctionCode(), processor);
    }

    /**
     * 注册功能级别的错误处理器
     *
     * @param functionLayerModule 功能模块
     * @param processor           错误处理器
     */
    public void registerFunctionProcessor(FunctionLayerModule functionLayerModule, AbstractFunctionLayerModuleProcessor processor) {
        registerFunctionProcessor(functionLayerModule, processor, null);
    }

}
