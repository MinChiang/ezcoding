package com.ezcoding.sdk.auth.resource.bean.dto;

import com.ezcoding.sdk.auth.resource.bean.model.ResourceTypeEnum;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2019-08-02 16:09
 */
@Data
public class ResourceBundleDTO {

    /**
     * 资源类型
     */
    @NotNull(message = "{resource.type}")
    private ResourceTypeEnum type;

    /**
     * 系统号
     */
    @NotNull(message = "{resource.applicationName}")
    private String applicationName;

    /**
     * 资源路径
     */
    private String accessPath;

    /**
     * 资源检索名称
     */
    private String permissionName;

}
