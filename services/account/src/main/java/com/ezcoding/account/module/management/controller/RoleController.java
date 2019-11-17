package com.ezcoding.account.module.management.controller;

import com.ezcoding.account.module.management.bean.model.Role;
import com.ezcoding.account.module.management.service.RoleService;
import com.ezcoding.base.web.extend.spring.resolver.CurrentUser;
import com.ezcoding.base.web.extend.spring.resolver.JsonParam;
import com.ezcoding.base.web.extend.spring.resolver.JsonResult;
import com.ezcoding.common.foundation.util.BeanUtils;
import com.ezcoding.sdk.account.management.bean.dto.RoleCreateDTO;
import com.ezcoding.sdk.account.user.api.IUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

import static com.ezcoding.sdk.account.management.constant.AccountManagementApiConstants.*;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2019-09-25 9:17
 */
@Validated
@RestController
@RequestMapping(ROLE_API)
public class RoleController {

    @Autowired
    private RoleService roleService;

    /**
     * 创建角色
     *
     * @param roleCreateDTO 群组信息
     * @param user          登陆人
     * @return 创建的部门
     */
    @PostMapping(CREATE_ROLE)
    @JsonResult
    public Role createRole(@JsonParam @Validated RoleCreateDTO roleCreateDTO,
                           @CurrentUser IUser user) {
        Role role = BeanUtils.copy(roleCreateDTO, Role.class);
        return roleService.createRole(role, user.getCode());
    }

    /**
     * 查找用户所有的角色
     *
     * @param user 登陆人
     * @return 所属角色
     */
    @PostMapping(FIND_ALL_ROLES)
    @JsonResult
    public Collection<Role> findAllRoles(@CurrentUser IUser user) {
        return roleService.findAllRoles(user.getCode());
    }

}
