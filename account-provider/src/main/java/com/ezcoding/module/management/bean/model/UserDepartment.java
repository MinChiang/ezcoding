package com.ezcoding.module.management.bean.model;

import com.baomidou.mybatisplus.annotation.TableName;
import com.ezcoding.common.mybatis.model.BaseModel;

import java.io.Serializable;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2019-09-25 11:03
 */
@TableName("account_user_department")
public class UserDepartment extends BaseModel implements Serializable {

    /**
     * 用户主键
     */
    private String userCode;

    /**
     * 部门主键
     */
    private Long departmentId;

    public String getUserCode() {
        return userCode;
    }

    public void setUserCode(String userCode) {
        this.userCode = userCode;
    }

    public Long getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(Long departmentId) {
        this.departmentId = departmentId;
    }

}
