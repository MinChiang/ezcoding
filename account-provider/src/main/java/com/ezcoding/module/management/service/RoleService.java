package com.ezcoding.module.management.service;

import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ezcoding.api.account.management.bean.model.RoleStatusEnum;
import com.ezcoding.common.foundation.core.exception.processor.WebExceptionBuilderFactory;
import com.ezcoding.common.foundation.util.AssertUtils;
import com.ezcoding.common.mybatis.model.BaseModelUtils;
import com.ezcoding.module.management.bean.model.Role;
import com.ezcoding.module.management.dao.RoleMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import static com.ezcoding.module.management.exception.AccountManagementExceptionConstants.GEN_MANAGEMENT_ROLES_NOT_VALID;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2019-09-24 17:42
 */
@Service
public class RoleService extends ServiceImpl<RoleMapper, Role> {

    @Autowired
    private GroupService groupService;
    @Autowired
    private DepartmentService departmentService;

    /**
     * 创建角色
     *
     * @param role 群组
     * @param user 创建人
     * @return 创建的群组
     */
    public Role createRole(Role role, String user) {
        BaseModelUtils.fillAllField(role, user);
        role.setStatus(RoleStatusEnum.ENABLE);

        save(role);
        return role;
    }

    /**
     * 查找用户所有角色
     * 1.用户所属部门（含）以及对应上级部门所属组群所拥有的角色
     * 2.用户所属组群（自定义）添加的角色
     *
     * @param user 创建人
     * @return 用户所属角色
     */
    public Collection<Role> findAllRoles(String user) {
        Collection<Role> departmentRoles = departmentService.listDepartmentRoles(user);
        Collection<Role> groupRoles = groupService.listGroupRoles(user);

        Set<Role> roles = new HashSet<>();
        roles.addAll(departmentRoles);
        roles.addAll(groupRoles);
        return roles;
    }

    /**
     * 判断是角色列表是否有效
     *
     * @param roles 待校验的角色列表id
     * @return 角色列表是否有效
     */
    public boolean checkRolesValid(Collection<Role> roles) {
        if (CollectionUtils.isEmpty(roles)) {
            return false;
        }

        return roles.stream().noneMatch(r -> r.getStatus() == RoleStatusEnum.DISABLE);
    }

    /**
     * 判断是角色列表是否有效，并且返回所有角色列表
     *
     * @param ids 待校验的角色列表id
     * @return 角色列表
     */
    public Collection<Role> assertCheckAndGetRole(Collection<Long> ids) {
        AssertUtils.mustNotEmpty(ids, () -> WebExceptionBuilderFactory.webExceptionBuilder(GEN_MANAGEMENT_ROLES_NOT_VALID).build());
        Collection<Role> roles = listByIds(ids);
        if (roles.size() != ids.size()) {
            AssertUtils.mustNotEmpty(ids, () -> WebExceptionBuilderFactory.webExceptionBuilder(GEN_MANAGEMENT_ROLES_NOT_VALID).build());
        }
        AssertUtils.mustTrue(checkRolesValid(roles), () -> WebExceptionBuilderFactory.webExceptionBuilder(GEN_MANAGEMENT_ROLES_NOT_VALID).build());
        return roles;
    }

}
