package com.ezcoding.auth.module.resource.service;

import com.ezcoding.auth.module.resource.bean.model.ResourceBundle;
import com.ezcoding.sdk.account.user.api.IUser;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2019-08-05 9:01
 */
public interface IResourceService {

    /**
     * 判断用户是否含有对应资源
     *
     * @param resourceBundle 资源详情
     * @param user           当前用户
     * @return 是否含有对应资源
     */
    boolean hasResource(ResourceBundle resourceBundle, IUser user);

}
