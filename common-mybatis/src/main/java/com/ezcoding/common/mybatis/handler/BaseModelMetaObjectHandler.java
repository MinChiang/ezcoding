package com.ezcoding.common.mybatis.handler;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.ezcoding.common.core.user.EmptyUserLoader;
import com.ezcoding.common.core.user.UserBasicIdentifiable;
import com.ezcoding.common.core.user.UserLoadable;
import org.apache.ibatis.reflection.MetaObject;

import java.util.Date;
import java.util.Optional;

/**
 * 用户编码自动处理类
 *
 * @author MinChiang
 * @version 1.0.0
 * @date 2020-04-06 13:28
 */
public class BaseModelMetaObjectHandler implements MetaObjectHandler {

    public static final String FIELD_NAME_CREATOR = "creator";
    public static final String FIELD_NAME_CREATE_TIME = "createTime";
    public static final String FIELD_NAME_MODIFIER = "modifier";
    public static final String FIELD_NAME_MODIFY_TIME = "modifyTime";

    private UserLoadable loader = new EmptyUserLoader();

    public BaseModelMetaObjectHandler() {
    }

    public BaseModelMetaObjectHandler(UserLoadable loader) {
        if (loader != null) {
            this.loader = loader;
        }
    }

    protected Long getCurrentUserPrincipal() {
        return Optional
                .ofNullable(loader)
                .map(UserLoadable::load)
                .map(UserBasicIdentifiable::getId)
                .orElse(null);
    }

    @Override
    public void insertFill(MetaObject metaObject) {
        Object creator = getFieldValByName(FIELD_NAME_CREATOR, metaObject);
        if (creator == null) {
            Long currentUserPrincipal = getCurrentUserPrincipal();
            if (currentUserPrincipal != null) {
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
            Long currentUserPrincipal = getCurrentUserPrincipal();
            if (currentUserPrincipal != null) {
                setFieldValByName(FIELD_NAME_MODIFIER, currentUserPrincipal, metaObject);
            }
        }

        Object updateTime = getFieldValByName(FIELD_NAME_MODIFY_TIME, metaObject);
        if (updateTime == null) {
            setFieldValByName(FIELD_NAME_MODIFY_TIME, new Date(), metaObject);
        }
    }

}
