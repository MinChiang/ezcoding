package com.ezcoding.module.management.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ezcoding.common.mybatis.model.BaseModelUtils;
import com.ezcoding.module.management.bean.model.Department;
import com.ezcoding.module.management.bean.model.UserDepartment;
import com.ezcoding.module.management.dao.UserDepartmentMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2019-09-24 17:42
 */
@Service
public class UserDepartmentService extends ServiceImpl<UserDepartmentMapper, UserDepartment> {

    @Autowired
    private DepartmentService departmentService;

    /**
     * 删除对应的用户所在部门映射关系
     *
     * @param users 需要删除映射关系的用户
     */
    private void deleteUserDepartmentRelations(Collection<String> users) {
        baseMapper.deleteByUserCode(users);
    }

    /**
     * 添加用户
     *
     * @param department 所属部门
     * @param userCodes  被添加用户的编号
     * @param user       添加人
     */
    @Transactional(rollbackFor = Exception.class)
    public void addUsers(Department department,
                         Set<String> userCodes,
                         String user) {
        departmentService.assertDepartmentValid(department);

        //删除对应的用户所在部门映射关系
        deleteUserDepartmentRelations(userCodes);

        List<UserDepartment> userDepartments = userCodes.stream().map(code -> {
            UserDepartment userDepartment = new UserDepartment();
            BaseModelUtils.fillAllField(userDepartment, user);

            userDepartment.setDepartmentId(department.getId());
            userDepartment.setUserCode(code);
            return userDepartment;
        }).collect(Collectors.toList());
        saveBatch(userDepartments);
    }

}
