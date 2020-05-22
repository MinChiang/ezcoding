package com.ezcoding.account.module.management.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ezcoding.account.module.management.bean.model.UserDepartment;
import org.apache.ibatis.annotations.Param;

import java.util.Collection;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2019-09-24 16:16
 */
public interface UserDepartmentMapper extends BaseMapper<UserDepartment> {

    /**
     * 删除对应用户——部门绑定关系
     *
     * @param users 需要删除绑定关系的用户主键
     */
    void deleteByUserCode(@Param("users") Collection<String> users);

}
