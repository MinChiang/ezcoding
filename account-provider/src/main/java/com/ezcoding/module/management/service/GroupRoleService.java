package com.ezcoding.module.management.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ezcoding.common.mybatis.model.BaseModelUtils;
import com.ezcoding.module.management.bean.model.Group;
import com.ezcoding.module.management.bean.model.GroupRole;
import com.ezcoding.module.management.dao.GroupRoleMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2019-09-24 17:42
 */
@Service
public class GroupRoleService extends ServiceImpl<GroupRoleMapper, GroupRole> {

    @Autowired
    private GroupService groupService;
    @Autowired
    private RoleService roleService;

    /**
     * 加入对应的角色
     *
     * @param group 群组
     * @param ids   添加的角色id
     * @param user  添加人
     */
    @Transactional(rollbackFor = Exception.class)
    public void addRoles(Group group,
                         Set<Long> ids,
                         String user) {
        groupService.assertGroupValid(group);
        roleService.assertCheckAndGetRole(ids);

        List<GroupRole> groupRoles = ids.stream().map(id -> {
            GroupRole groupRole = new GroupRole();
            BaseModelUtils.fillAllField(groupRole, user);

            groupRole.setGroupId(group.getId());
            groupRole.setRoleId(id);
            return groupRole;
        }).collect(Collectors.toList());
        saveBatch(groupRoles);
    }

}
