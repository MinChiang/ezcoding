package com.ezcoding.module.management.controller;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2019-09-24 17:00
 */
//@Validated
//@RestController
//@RequestMapping("department")
public class DepartmentController {

//    @Autowired
//    private DepartmentService departmentService;
//    @Autowired
//    private UserDepartmentService userDepartmentService;
//
//    /**
//     * 获取上级所有的部门
//     *
//     * @param id   待筛选的id
//     * @param code 带删选的code
//     * @return 上级部门列表
//     */
//    @PostMapping()
//    @JsonResult
//    public Collection<Department> listParentDepartment(@JsonParam("id") Long id,
//                                                       @JsonParam("code") String code) {
//        AssertUtils.mustTrue(id != null || StringUtils.isNotEmpty(code), CommonApplicationExceptionConstants.COMMON_PARAM_VALIDATE_ERROR.instance().param("部门id或部门编号不能为空").build());
//
//        Department department = new Department();
//        department.setId(id);
//        department.setCode(code);
//        department = departmentService.getOne(Wrappers.query(department));
//
//        return departmentService.findParentDepartments(department);
//    }
//
//    /**
//     * 创建部门
//     *
//     * @param departmentCreateDTO 部门信息
//     * @param user                登陆人
//     * @return 创建的部门
//     */
//    @PostMapping(CREATE_DEPARTMENT)
//    @JsonResult
//    public Department createDepartment(@JsonParam @Validated DepartmentCreateDTO departmentCreateDTO,
//                                       @CurrentUser IUser user) {
//        Department department = BeanUtils.copy(departmentCreateDTO, Department.class);
//        return departmentService.createDepartment(department, user);
//    }
//
//    /**
//     * 添加用户
//     *
//     * @param departmentId 被添加的部门
//     * @param users        添加的用户
//     * @param user         添加人
//     */
//    @PostMapping(ADD_USERS)
//    @JsonResult
//    public void addUsers(@JsonParam("departmentId") @NotNull(message = "{department.id}") Long departmentId,
//                         @JsonParam("users") @Size(min = DEPARTMENT_USERS_SIZE_MIN, message = "{department.users}") Set<String> users,
//                         @CurrentUser IUser user) {
//        Department department = departmentService.getById(departmentId);
//        userDepartmentService.addUsers(department, users, user.getCode());
//    }

}
