package com.ezcoding.account.module.management.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ezcoding.account.module.management.bean.model.Group;
import com.ezcoding.account.module.management.bean.model.Role;
import com.ezcoding.account.module.management.dao.GroupMapper;
import com.ezcoding.base.web.util.BaseModelUtils;
import com.ezcoding.common.foundation.util.AssertUtils;
import com.ezcoding.sdk.account.management.bean.model.GroupStatusEnum;
import org.springframework.stereotype.Service;

import java.util.Collection;

import static com.ezcoding.account.module.management.exception.ManagementExceptionConstants.GROUP_NOT_VALID;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2019-09-24 17:42
 */
@Service
public class GroupService extends ServiceImpl<GroupMapper, Group> {

    /**
     * 创建用户群组
     *
     * @param group 群组
     * @param user  创建人
     * @return 创建的群组
     */
    public Group createGroup(Group group, String user) {
        BaseModelUtils.fillAllField(group, user);
        group.setStatus(GroupStatusEnum.ENABLE);

        save(group);
        return group;
    }

    /**
     * 断言组群为可用状态
     *
     * @param group 需判断的组群
     */
    public void assertGroupValid(Group group) {
        AssertUtils.mustTrue(group != null && group.getStatus() == GroupStatusEnum.ENABLE, GROUP_NOT_VALID);
    }

    /**
     * 根据群组查找对应用户的角色
     *
     * @param user 待查找角色的用户
     */
    public Collection<Role> listRolesByGroup(String user) {
        return baseMapper.selectRolesByGroup(user);
    }

}
