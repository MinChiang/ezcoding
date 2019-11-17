package com.ezcoding.common.foundation.core.exception;

import org.apache.commons.lang3.StringUtils;

import static com.ezcoding.common.foundation.core.constant.GlobalConstants.Application.*;

/**
 * 业务定义异常
 * <p>
 * 异常规则：
 * 1.2位的系统码，定义在全局包中（所有微服务以及额外包都需要引入使用）
 * 2.4位的模块码，定义在各个微服务公共产量文件管理
 * 3.4位的模块错误详情码，定义在各个微服务模块的包中，由包自治管理
 *
 * @author MinChiang
 * @version 1.0.0
 * @date 2018-08-26 23:22
 */
public abstract class AbstractApplicationException extends RuntimeException {

    /**
     * 系统码
     */
    protected String applicationCode;

    /**
     * 模块码
     */
    protected String moduleCode;

    /**
     * 错误详情码
     */
    protected String detailCode;

    public AbstractApplicationException(String applicationCode, String moduleCode, String detailCode, String message, Throwable cause) {
        super(message, cause);
        this.validateErrorCode(applicationCode, moduleCode, detailCode);
        this.applicationCode = applicationCode;
        this.moduleCode = moduleCode;
        this.detailCode = detailCode;
    }

    /**
     * 校验错误码长度是否符合规范
     *
     * @param applicationCode 系统号
     * @param moduleCode      模块号码
     * @param detailCode      错误详情码
     */
    private void validateErrorCode(String applicationCode, String moduleCode, String detailCode) {
        if (StringUtils.isAnyEmpty(applicationCode, moduleCode, detailCode)) {
            throw new IllegalArgumentException("系统码、模块码、错误详情码不能为空");
        }
        if (applicationCode.length() != APPLICATION_CODE_LENGTH) {
            throw new IllegalArgumentException("系统码长度必须为" + APPLICATION_CODE_LENGTH);
        }
        if (moduleCode.length() != MODULE_CODE_LENGTH) {
            throw new IllegalArgumentException("模块码长度必须为" + MODULE_CODE_LENGTH);
        }
        if (detailCode.length() != DETAIL_CODE_LENGTH) {
            throw new IllegalArgumentException("错误详情码长度必须为" + DETAIL_CODE_LENGTH);
        }
    }

    /**
     * 获取完整的错误码
     *
     * @return 完整的错误码
     */
    public String getCode() {
        return this.applicationCode + this.moduleCode + this.detailCode;
    }

    @Override
    public String toString() {
        String temp = getClass().getSimpleName() + "<" + getCode() + ">";
        String message = getLocalizedMessage();
        return (message == null) ? temp : temp + ": " + message;
    }
}
