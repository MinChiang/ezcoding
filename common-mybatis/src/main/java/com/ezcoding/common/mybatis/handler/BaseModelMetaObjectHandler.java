package com.ezcoding.common.mybatis.handler;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.ezcoding.common.core.user.model.IUserIdentifiable;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.reflection.MetaObject;

import java.util.Date;

/**
 * 用户编码自动处理类
 *
 * @author MinChiang
 * @version 1.0.0
 * @date 2018-09-30 10:28
 */
public class BaseModelMetaObjectHandler implements MetaObjectHandler {

    private IUserIdentifiable userResolver;

    private static final String FIELD_NAME_CREATOR = "creator";
    private static final String FIELD_NAME_CREATE_TIME = "createTime";
    private static final String FIELD_NAME_MODIFIER = "modifier";
    private static final String FIELD_NAME_MODIFY_TIME = "modifyTime";

    public BaseModelMetaObjectHandler(IUserIdentifiable userResolver) {
        this.userResolver = userResolver;
    }

    @Override
    public void insertFill(MetaObject metaObject) {
        Object creator = getFieldValByName(FIELD_NAME_CREATOR, metaObject);
        if (creator == null) {
            String currentUserPrincipal = this.userResolver.getCode();
            if (StringUtils.isNotEmpty(currentUserPrincipal)) {
                setFieldValByName(FIELD_NAME_CREATOR, currentUserPrincipal, metaObject);
            }
        }

        Object createTime = getFieldValByName(FIELD_NAME_CREATE_TIME, metaObject);
        if (createTime == null) {
            setFieldValByName(FIELD_NAME_CREATE_TIME, new Date(), metaObject);
        }

        this.updateFill(metaObject);
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        Object updator = getFieldValByName(FIELD_NAME_MODIFIER, metaObject);
        if (updator == null) {
            String currentUserPrincipal = this.userResolver.getCode();
            if (StringUtils.isNotEmpty(currentUserPrincipal)) {
                setFieldValByName(FIELD_NAME_MODIFIER, currentUserPrincipal, metaObject);
            }
        }

        Object updateTime = getFieldValByName(FIELD_NAME_MODIFY_TIME, metaObject);
        if (updateTime == null) {
            setFieldValByName(FIELD_NAME_MODIFY_TIME, new Date(), metaObject);
        }
    }

}
