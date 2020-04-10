package com.ezcoding.module.management.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ezcoding.common.mybatis.model.BaseModelUtils;
import com.ezcoding.module.management.bean.model.Group;
import com.ezcoding.module.management.bean.model.GroupUser;
import com.ezcoding.module.management.dao.GroupUserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2019-09-24 17:42
 */
@Service
public class GroupUserService extends ServiceImpl<GroupUserMapper, GroupUser> {

    @Autowired
    private GroupService groupService;

    /**
     * 添加用户
     *
     * @param group     所属群组
     * @param userCodes 被添加用户的编号
     * @param user      添加人
     */
    @Transactional(rollbackFor = Exception.class)
    public void addUsers(Group group,
                         Set<String> userCodes,
                         String user) {
        groupService.assertGroupValid(group);

        List<GroupUser> groupUsers = userCodes.stream().map(code -> {
            GroupUser groupUser = new GroupUser();
            BaseModelUtils.fillAllField(groupUser, user);

            groupUser.setGroupId(group.getId());
            groupUser.setUserCode(code);
            return groupUser;
        }).collect(Collectors.toList());
        saveBatch(groupUsers);
    }

}
