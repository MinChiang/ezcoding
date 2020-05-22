package com.ezcoding.common.mybatis.handler;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.ezcoding.common.core.user.UserIdentifiable;
import com.ezcoding.common.core.user.UserLoadable;
import com.ezcoding.common.mybatis.constant.TableFieldConstants;
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

    private UserLoadable loader;

    public BaseModelMetaObjectHandler() {
    }

    public BaseModelMetaObjectHandler(UserLoadable loader) {
        this.loader = loader;
    }

    protected String getCurrentUserPrincipal() {
        return Optional
                .ofNullable(loader)
                .map(UserLoadable::load)
                .map(UserIdentifiable::getAccount)
                .orElse(null);
    }

    @Override
    public void insertFill(MetaObject metaObject) {
        Object creator = getFieldValByName(TableFieldConstants.FIELD_NAME_CREATOR, metaObject);
        if (creator == null) {
            String currentUserPrincipal = getCurrentUserPrincipal();
            if (currentUserPrincipal != null) {
                setFieldValByName(TableFieldConstants.FIELD_NAME_CREATOR, currentUserPrincipal, metaObject);
            }
        }

        Object createTime = getFieldValByName(TableFieldConstants.FIELD_NAME_CREATE_TIME, metaObject);
        if (createTime == null) {
            setFieldValByName(TableFieldConstants.FIELD_NAME_CREATE_TIME, new Date(), metaObject);
        }

        this.updateFill(metaObject);
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        Object updator = getFieldValByName(TableFieldConstants.FIELD_NAME_MODIFIER, metaObject);
        if (updator == null) {
            String currentUserPrincipal = getCurrentUserPrincipal();
            if (currentUserPrincipal != null) {
                setFieldValByName(TableFieldConstants.FIELD_NAME_MODIFIER, currentUserPrincipal, metaObject);
            }
        }

        Object updateTime = getFieldValByName(TableFieldConstants.FIELD_NAME_MODIFY_TIME, metaObject);
        if (updateTime == null) {
            setFieldValByName(TableFieldConstants.FIELD_NAME_MODIFY_TIME, new Date(), metaObject);
        }
    }

}
