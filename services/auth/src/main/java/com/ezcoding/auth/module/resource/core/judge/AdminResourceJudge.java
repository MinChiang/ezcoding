package com.ezcoding.auth.module.resource.core.judge;

import com.ezcoding.auth.module.resource.bean.model.ResourceBundle;
import com.ezcoding.common.foundation.core.constant.GlobalConstants;
import com.ezcoding.sdk.account.user.api.IUser;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2019-10-06 11:12
 */
public class AdminResourceJudge implements IResourceJudgeable {

    @Override
    public boolean judge(ResourceBundle bundle, IUser user) {
        return user.getRoles().contains(GlobalConstants.Role.ADMIN);
    }

}
