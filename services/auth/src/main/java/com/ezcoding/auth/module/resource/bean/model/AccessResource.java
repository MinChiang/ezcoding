package com.ezcoding.auth.module.resource.bean.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2019-08-02 11:01
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class AccessResource extends AbstractResource {

    /**
     * 对应保存的可匹配地址
     */
    private String matchPath;

}
