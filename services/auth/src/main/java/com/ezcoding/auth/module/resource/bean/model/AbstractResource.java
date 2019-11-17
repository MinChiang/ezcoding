package com.ezcoding.auth.module.resource.bean.model;

import com.ezcoding.base.web.extend.mybatis.model.BaseModel;
import com.ezcoding.sdk.auth.resource.bean.model.ResourceStatusEnum;
import com.ezcoding.sdk.auth.resource.bean.model.ResourceTypeEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2019-08-02 10:49
 */
@Data
@EqualsAndHashCode(callSuper = true)
public abstract class AbstractResource extends BaseModel {

    /**
     * 资源名称
     */
    private String name;

    /**
     * 资源唯一编号
     */
    private String code;

    /**
     * 所属模块号
     */
    private String applicationCode;

    /**
     * 备注
     */
    private String comment;

    /**
     * 状态
     */
    private ResourceStatusEnum status;

    /**
     * 类型
     */
    private ResourceTypeEnum type;

}
