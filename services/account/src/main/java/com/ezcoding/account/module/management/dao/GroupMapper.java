package com.ezcoding.account.module.management.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ezcoding.account.module.management.bean.model.Group;
import com.ezcoding.account.module.management.bean.model.Role;
import org.apache.ibatis.annotations.Param;

import java.util.Collection;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2019-09-24 16:16
 */
public interface GroupMapper extends BaseMapper<Group> {

    /**
     * 根据群组查找对应用户的角色
     *
     * @param user 待查找角色的用户
     */
    Collection<Role> selectRolesByGroup(@Param("user") String user);

}
