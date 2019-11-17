package com.ezcoding.auth.module.resource.core.judge;

import com.ezcoding.auth.module.resource.bean.model.ResourceBundle;
import com.ezcoding.sdk.account.user.api.IUser;
import com.google.common.collect.Lists;

import java.util.Collection;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2019-10-07 11:21
 */
public class ResourceJudgeChain implements IResourceJudgeable {

    private Collection<IResourceJudgeable> judges = Lists.newArrayList();

    @Override
    public boolean judge(ResourceBundle bundle, IUser user) {
        for (IResourceJudgeable judge : judges) {
            if (judge.judge(bundle, user)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 增加链式判断认证器
     *
     * @param judge 需要增加的认证器
     */
    public void addJudge(IResourceJudgeable judge) {
        judges.add(judge);
    }

}
