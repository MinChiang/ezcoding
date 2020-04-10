package com.ezcoding.module.management.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ezcoding.common.foundation.core.exception.processor.WebExceptionBuilderFactory;
import com.ezcoding.common.foundation.util.AssertUtils;
import com.ezcoding.common.mybatis.model.BaseModelUtils;
import com.ezcoding.api.account.management.bean.model.GroupStatusEnum;
import com.ezcoding.module.management.bean.model.Group;
import com.ezcoding.module.management.bean.model.Role;
import com.ezcoding.module.management.dao.GroupMapper;
import org.springframework.stereotype.Service;

import java.util.Collection;

import static com.ezcoding.module.management.exception.AccountManagementExceptionConstants.GEN_MANAGEMENT_GROUP_NOT_VALID;

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
        AssertUtils.mustTrue(group != null && group.getStatus() == GroupStatusEnum.ENABLE, () -> WebExceptionBuilderFactory.webExceptionBuilder(GEN_MANAGEMENT_GROUP_NOT_VALID).build());
    }

    /**
     * 根据群组查找对应用户的角色
     *
     * @param user 待查找角色的用户
     */
    public Collection<Role> listGroupRoles(String user) {
        return baseMapper.selectRolesByGroup(user);
    }

}
