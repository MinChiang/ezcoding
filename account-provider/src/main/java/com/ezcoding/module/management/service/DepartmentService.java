package com.ezcoding.module.management.service;

import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ezcoding.api.account.management.bean.model.DepartmentStatusEnum;
import com.ezcoding.api.account.management.bean.model.GroupStatusEnum;
import com.ezcoding.common.core.user.model.IUser;
import com.ezcoding.common.foundation.core.exception.processor.WebExceptionBuilderFactory;
import com.ezcoding.common.foundation.util.AssertUtils;
import com.ezcoding.module.management.bean.model.Department;
import com.ezcoding.module.management.bean.model.Group;
import com.ezcoding.module.management.bean.model.Role;
import com.ezcoding.module.management.dao.DepartmentMapper;
import org.bouncycastle.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

import static com.ezcoding.module.management.exception.AccountManagementExceptionConstants.GEN_MANAGEMENT_GROUP_NOT_VALID;
import static com.ezcoding.module.management.exception.AccountManagementExceptionConstants.GEN_MANAGEMENT_PARENT_DEPARTMENT_NOT_FOUND;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2019-09-24 16:16
 */
@Service
public class DepartmentService extends ServiceImpl<DepartmentMapper, Department> {

    private static final char PATH_SPLITTER = ',';

    @Autowired
    private GroupService groupService;
//    @Autowired
//    private UserDepartmentService userDepartmentService;

    /**
     * 根据部门id或code查找对应部门的上级部门
     *
     * @param department 查找的部门
     * @return 对应部门的上级部门
     */
    public Collection<Department> findParentDepartments(Department department) {
        //如果搜索不到结果或者对应的节点为根节点，则直接返回空列表
        if (department == null || isRootDepartment(department)) {
            return new ArrayList<>();
        }

        List<Long> parentIds = splitParentId(department.getPath());
        if (CollectionUtils.isEmpty(parentIds)) {
            return new ArrayList<>();
        }

        return listByIds(parentIds);
    }

    /**
     * 根据部门创建对应的组别
     *
     * @param department 创建的部门
     * @return 部门对应的组别
     */
    private Group createGroupByDepartment(Department department) {
        Group group = new Group();
        group.setName(department.getName());
        group.setStatus(GroupStatusEnum.ENABLE);
        group.setCreateTime(department.getCreateTime());
        group.setModifyTime(department.getModifyTime());
        group.setCreator(department.getCreator());
        group.setModifier(department.getModifier());
        return group;
    }

    /**
     * 创建部门
     *
     * @param department 待创建的部门
     * @param user       创建人
     * @return 创建的部门信息
     */
    @Transactional(rollbackFor = Exception.class)
    public Department createDepartment(Department department, IUser user) {
        int level = 0;
        String path = null;
        Date date = new Date();
        String creator = user.getCode();

        Long parentId = department.getParentId();
        if (parentId != null) {
            Department parentDepartment = getById(parentId);
            AssertUtils.mustNotNull(parentDepartment, () -> WebExceptionBuilderFactory.webExceptionBuilder(GEN_MANAGEMENT_PARENT_DEPARTMENT_NOT_FOUND).build());
            if (parentDepartment.getLeaf()) {
                //跟新父节点为非叶子节点
                parentDepartment.setLeaf(false);
                updateById(parentDepartment);
            }

            level = parentDepartment.getLevel() + 1;
            path = parentDepartment.getPath() + PATH_SPLITTER + parentDepartment.getId();
        }

        department.setLeaf(true);
        department.setLevel(level);
        department.setPath(path);
        department.setCreateTime(date);
        department.setModifyTime(date);
        department.setCreator(creator);
        department.setModifier(creator);

        Group group = createGroupByDepartment(department);
        groupService.save(group);
        department.setGroupId(group.getId());
        save(department);

        return department;
    }

    /**
     * 判断节点是否为部门根节点
     *
     * @param department 待判断的部门
     * @return 是否是根节点
     */
    private boolean isRootDepartment(Department department) {
        return Optional
                .ofNullable(department)
                .map(d -> d.getLevel() == 0)
                .orElse(false);
    }

    /**
     * 获取上级部门id
     *
     * @param path 待分割的路径
     * @return 上级部门id
     */
    private List<Long> splitParentId(String path) {
        if (StringUtils.isEmpty(path)) {
            return new ArrayList<>();
        }

        return Arrays
                .stream(Strings.split(path, PATH_SPLITTER))
                .map(Long::valueOf)
                .collect(Collectors.toList());
    }

    /**
     * 断言部门为可用状态
     *
     * @param department 需判断的部门
     */
    public void assertDepartmentValid(Department department) {
        AssertUtils.mustTrue(department != null && department.getStatus() == DepartmentStatusEnum.RUNNING, () -> WebExceptionBuilderFactory.webExceptionBuilder(GEN_MANAGEMENT_GROUP_NOT_VALID).build());
    }

    /**
     * 根据群组查找对应用户的角色
     *
     * @param user 待查找角色的用户
     */
    public Department findBelongDepartment(String user) {
        return baseMapper.findDepartmentByUser(user);
    }

    /**
     * 根据群组查找对应用户的角色
     *
     * @param user 待查找角色的用户
     */
    public Collection<Role> listDepartmentRoles(String user) {
        Department belongDepartment = findBelongDepartment(user);
        if (belongDepartment == null) {
            return new ArrayList<>();
        }

        Collection<Department> departments = findParentDepartments(belongDepartment);
        departments.add(belongDepartment);
        List<Long> departmentIds = departments.stream().map(Department::getId).collect(Collectors.toList());

        return baseMapper.listRolesByDepartment(departmentIds);
    }

}
