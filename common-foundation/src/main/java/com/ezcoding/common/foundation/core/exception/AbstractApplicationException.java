package com.ezcoding.common.foundation.core.exception;

import com.ezcoding.common.foundation.core.application.ModuleLayerModule;

/**
 * 业务定义异常
 * <p>
 * 异常规则：
 * 1.系统码，定义在全局包中（所有微服务以及额外包都需要引入使用）
 * 2.模块码，定义在各个微服务公共产量文件管理
 * 3.模块错误详情码，定义在各个微服务模块的包中，由包自治管理
 *
 * @author MinChiang
 * @version 1.0.0
 * @date 2018-08-26 23:22
 */
public abstract class AbstractApplicationException extends RuntimeException implements IApplicationException {

    /**
     * 模块号码
     */
    protected ModuleLayerModule moduleLayerModule;

    /**
     * 错误详情码
     */
    protected String detailCode;

    public AbstractApplicationException(ModuleLayerModule moduleLayerModule, String detailCode, String message, Throwable cause) {
        super(message, cause);
        this.moduleLayerModule = moduleLayerModule;
        this.detailCode = detailCode;
    }

    @Override
    public String getCode() {
        return this.moduleLayerModule.getCode() + this.detailCode;
    }

    @Override
    public String toString() {
        return "模块" +
                moduleLayerModule.getName() +
                "发生异常：\n错误类型：" +
                getClass().getName() +
                "\n指令代码：" +
                getCode() +
                "\n错误信息：" +
                getLocalizedMessage() +
                "\n发生原因：" +
                getCause();
    }

    public ModuleLayerModule getModuleLayerModule() {
        return moduleLayerModule;
    }

    public void setModuleLayerModule(ModuleLayerModule moduleLayerModule) {
        this.moduleLayerModule = moduleLayerModule;
    }

    public String getDetailCode() {
        return detailCode;
    }

    public void setDetailCode(String detailCode) {
        this.detailCode = detailCode;
    }

}
