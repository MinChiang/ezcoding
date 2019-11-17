package com.ezcoding.auth.module.resource.core.judge;

import com.ezcoding.auth.module.resource.bean.model.ResourceBundle;
import com.ezcoding.sdk.account.user.api.IUser;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2019-08-02 10:27
 */
public interface IResourceJudgeable {

    /**
     * 判断是否拥有本资源权限
     *
     * @param bundle 资源内容
     * @return 是否拥有本资源权限
     */
    boolean judge(ResourceBundle bundle, IUser user);

}
