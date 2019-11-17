package com.ezcoding.account.module.management.controller;

import com.ezcoding.account.module.management.bean.model.Group;
import com.ezcoding.account.module.management.service.GroupRoleService;
import com.ezcoding.account.module.management.service.GroupService;
import com.ezcoding.base.web.extend.spring.resolver.CurrentUser;
import com.ezcoding.base.web.extend.spring.resolver.JsonParam;
import com.ezcoding.base.web.extend.spring.resolver.JsonResult;
import com.ezcoding.common.foundation.util.BeanUtils;
import com.ezcoding.sdk.account.management.bean.dto.GroupCreateDTO;
import com.ezcoding.sdk.account.user.api.IUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Set;

import static com.ezcoding.sdk.account.management.constant.AccountManagementApiConstants.*;
import static com.ezcoding.sdk.account.management.constant.AccountManagementConstants.GROUP_ROLES_SIZE_MIN;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2019-09-24 17:00
 */
@Validated
@RestController
@RequestMapping(GROUP_API)
public class GroupController {

    @Autowired
    private GroupService groupService;
    @Autowired
    private GroupRoleService groupRoleService;

    /**
     * 创建群组
     *
     * @param groupCreateDTO 群组信息
     * @param user           登陆人
     * @return 创建的部门
     */
    @PostMapping(CREATE_GROUP)
    @JsonResult
    public Group createGroup(@JsonParam @Validated GroupCreateDTO groupCreateDTO,
                             @CurrentUser IUser user) {
        Group group = BeanUtils.copy(groupCreateDTO, Group.class);
        return groupService.createGroup(group, user.getCode());
    }

    /**
     * 添加角色
     *
     * @param groupId 被添加的用户群组
     * @param roles   添加的角色id
     * @param user    添加人
     */
    @PostMapping(ADD_ROLES)
    @JsonResult
    public void addRoles(@JsonParam("groupId") @NotNull(message = "{group.id}") Long groupId,
                         @JsonParam("roles") @Size(min = GROUP_ROLES_SIZE_MIN, message = "{group.roles}") Set<Long> roles,
                         @CurrentUser IUser user) {
        Group group = groupService.getById(groupId);
        groupRoleService.addRoles(group, roles, user.getCode());
    }

}
