package com.ezcoding.account.module.management.exception;

import com.ezcoding.common.foundation.core.exception.ExceptionBuilderFactory;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2018-11-14 15:57
 */
public class ManagementExceptionConstants {

    public static final ExceptionBuilderFactory<ManagementException>
            PARENT_DEPARTMENT_NOT_FOUND = ExceptionBuilderFactory.create(ManagementException.class, "0001", "未找到对应的上级部门"),
            GROUP_NOT_VALID = ExceptionBuilderFactory.create(ManagementException.class, "0002", "对应的群组不可用"),
            ROLES_NOT_VALID = ExceptionBuilderFactory.create(ManagementException.class, "0003", "存在不可用的用户角色");

}
