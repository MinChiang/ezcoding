package com.ezcoding.module.management.controller;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2019-09-24 17:00
 */
//@Validated
//@RestController
//@RequestMapping(GROUP_API)
public class GroupController {

//    @Autowired
//    private GroupService groupService;
//    @Autowired
//    private GroupRoleService groupRoleService;
//
//    /**
//     * 创建群组
//     *
//     * @param groupCreateDTO 群组信息
//     * @param user           登陆人
//     * @return 创建的部门
//     */
//    @PostMapping(CREATE_GROUP)
//    @JsonResult
//    public Group createGroup(@JsonParam @Validated GroupCreateDTO groupCreateDTO,
//                             @CurrentUser IUser user) {
//        Group group = BeanUtils.copy(groupCreateDTO, Group.class);
//        return groupService.createGroup(group, user.getCode());
//    }
//
//    /**
//     * 添加角色
//     *
//     * @param groupId 被添加的用户群组
//     * @param roles   添加的角色id
//     * @param user    添加人
//     */
//    @PostMapping(ADD_ROLES)
//    @JsonResult
//    public void addRoles(@JsonParam("groupId") @NotNull(message = "{group.id}") Long groupId,
//                         @JsonParam("roles") @Size(min = GROUP_ROLES_SIZE_MIN, message = "{group.roles}") Set<Long> roles,
//                         @CurrentUser IUser user) {
//        Group group = groupService.getById(groupId);
//        groupRoleService.addRoles(group, roles, user.getCode());
//    }

}
