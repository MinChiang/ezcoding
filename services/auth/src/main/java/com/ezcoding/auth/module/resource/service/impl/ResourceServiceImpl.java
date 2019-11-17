package com.ezcoding.auth.module.resource.service.impl;

import com.ezcoding.auth.module.resource.bean.model.ResourceBundle;
import com.ezcoding.auth.module.resource.core.judge.IResourceJudgeable;
import com.ezcoding.auth.module.resource.service.IResourceService;
import com.ezcoding.sdk.account.user.api.IUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2019-08-05 14:26
 */
@Service
public class ResourceServiceImpl implements IResourceService {

    @Autowired
    private IResourceJudgeable resourceJudgeable;

    @Override
    public boolean hasResource(ResourceBundle resourceBundle, IUser user) {
        return resourceJudgeable.judge(resourceBundle, user);
    }

}
