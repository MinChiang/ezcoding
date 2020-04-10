package com.ezcoding.module.management.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ezcoding.module.management.bean.model.Department;
import com.ezcoding.module.management.bean.model.Role;
import org.apache.ibatis.annotations.Param;

import java.util.Collection;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2019-09-24 16:16
 */
public interface DepartmentMapper extends BaseMapper<Department> {

    /**
     * 查找用户所属的部门
     *
     * @param user 待查找的用户
     * @return 用户所属部门
     */
    Department findDepartmentByUser(@Param("user") String user);

    /**
     * 根据用户所属部门群组查找所拥有的角色
     *
     * @param departmentIds 待查找的用户
     * @return 用户所拥有的角色
     */
    Collection<Role> listRolesByDepartment(@Param("departmentIds") Collection<Long> departmentIds);

}
