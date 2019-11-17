package com.ezcoding.account.module.management.controller;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.ezcoding.account.module.management.bean.model.Department;
import com.ezcoding.account.module.management.service.DepartmentService;
import com.ezcoding.account.module.management.service.UserDepartmentService;
import com.ezcoding.base.web.extend.spring.resolver.CurrentUser;
import com.ezcoding.base.web.extend.spring.resolver.JsonParam;
import com.ezcoding.base.web.extend.spring.resolver.JsonResult;
import com.ezcoding.common.foundation.core.exception.CommonApplicationExceptionConstants;
import com.ezcoding.common.foundation.util.AssertUtils;
import com.ezcoding.common.foundation.util.BeanUtils;
import com.ezcoding.sdk.account.management.bean.dto.DepartmentCreateDTO;
import com.ezcoding.sdk.account.user.api.IUser;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Collection;
import java.util.Set;

import static com.ezcoding.sdk.account.management.constant.AccountManagementApiConstants.*;
import static com.ezcoding.sdk.account.management.constant.AccountManagementConstants.DEPARTMENT_USERS_SIZE_MIN;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2019-09-24 17:00
 */
@Validated
@RestController
@RequestMapping(DEPARTMENT_API)
public class DepartmentController {

    @Autowired
    private DepartmentService departmentService;
    @Autowired
    private UserDepartmentService userDepartmentService;

    /**
     * 获取上级所有的部门
     *
     * @param id   待筛选的id
     * @param code 带删选的code
     * @return 上级部门列表
     */
    @PostMapping(LIST_PARENT_DEPARTMENT)
    @JsonResult
    public Collection<Department> listParentDepartment(@JsonParam("id") Long id,
                                                       @JsonParam("code") String code) {
        AssertUtils.mustTrue(id != null || StringUtils.isNotEmpty(code), CommonApplicationExceptionConstants.COMMON_PARAM_VALIDATE_ERROR.instance().param("部门id或部门编号不能为空").build());

        Department department = new Department();
        department.setId(id);
        department.setCode(code);
        department = departmentService.getOne(Wrappers.query(department));

        return departmentService.findParentDepartments(department);
    }

    /**
     * 创建部门
     *
     * @param departmentCreateDTO 部门信息
     * @param user                登陆人
     * @return 创建的部门
     */
    @PostMapping(CREATE_DEPARTMENT)
    @JsonResult
    public Department createDepartment(@JsonParam @Validated DepartmentCreateDTO departmentCreateDTO,
                                       @CurrentUser IUser user) {
        Department department = BeanUtils.copy(departmentCreateDTO, Department.class);
        return departmentService.createDepartment(department, user);
    }

    /**
     * 添加用户
     *
     * @param departmentId 被添加的部门
     * @param users        添加的用户
     * @param user         添加人
     */
    @PostMapping(ADD_USERS)
    @JsonResult
    public void addUsers(@JsonParam("departmentId") @NotNull(message = "{department.id}") Long departmentId,
                         @JsonParam("users") @Size(min = DEPARTMENT_USERS_SIZE_MIN, message = "{department.users}") Set<String> users,
                         @CurrentUser IUser user) {
        Department department = departmentService.getById(departmentId);
        userDepartmentService.addUsers(department, users, user.getCode());
    }

}
