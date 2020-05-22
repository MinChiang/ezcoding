package com.ezcoding.auth.module.resource.bean.model;

import com.ezcoding.common.foundation.core.exception.CommonApplicationExceptionConstants;
import com.ezcoding.common.foundation.util.AssertUtils;
import com.ezcoding.sdk.auth.resource.bean.model.ResourceTypeEnum;
import lombok.Getter;
import lombok.Setter;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2019-08-02 10:43
 */
@Getter
@Setter
public class ResourceBundle {

    /**
     * 资源类型
     */
    private ResourceTypeEnum type;

    /**
     * 系统号
     */
    private String applicationName;

    /**
     * 资源路径
     */
    private String accessPath;

    /**
     * 资源检索名称
     */
    private String permissionName;

    public ResourceBundle() {
    }

    public ResourceBundle(ResourceTypeEnum type, String applicationName, String accessPath, String permissionName) {
        this.type = type;
        this.applicationName = applicationName;
        this.accessPath = accessPath;
        this.permissionName = permissionName;
        this.valid();
    }

    private void valid() {
        switch (this.type) {
            case PERMISSION:
                AssertUtils.mustNotNull(this.permissionName, CommonApplicationExceptionConstants.COMMON_PARAM_VALIDATE_ERROR.instance().param("权限名称").build());
                break;
            case ACCESS_PATH:
                AssertUtils.mustNotNull(this.accessPath, CommonApplicationExceptionConstants.COMMON_PARAM_VALIDATE_ERROR.instance().param("访问路径").build());
                break;
            default:
                break;
        }
    }

}
