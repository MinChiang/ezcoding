package com.ezcoding.common.mybatis.model;

import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2019-09-25 11:34
 */
public class BaseModelUtils {

    /**
     * 填充创建的字段
     *
     * @param baseModel 被填充的对象
     * @param date      创建时间
     * @param userId      创建人
     * @param <T>       被填充的类型
     * @return 被填充的对象
     */
    public static <T extends BaseModel> T fillCreateField(@NotNull T baseModel, Date date, Long userId) {
        baseModel.setCreator(userId);
        baseModel.setCreateTime(date);
        return baseModel;
    }

    /**
     * 填充更新的字段
     *
     * @param baseModel 被填充的对象
     * @param date      更新时间
     * @param userId      更新人
     * @param <T>       被填充的类型
     * @return 被填充的对象
     */
    public static <T extends BaseModel> T fillModifyField(@NotNull T baseModel, Date date, Long userId) {
        baseModel.setModifier(userId);
        baseModel.setModifyTime(date);
        return baseModel;
    }

    /**
     * 填充所有字段
     *
     * @param baseModel 被填充的对象
     * @param date      创建时间
     * @param userId      创建人
     * @param <T>       被填充的类型
     * @return 被填充的对象
     */
    public static <T extends BaseModel> T fillAllField(@NotNull T baseModel, Date date, Long userId) {
        fillCreateField(baseModel, date, userId);
        fillModifyField(baseModel, date, userId);
        return baseModel;
    }

    /**
     * 填充创建的字段
     *
     * @param baseModel 被填充的对象
     * @param userId      创建人
     * @param <T>       被填充的类型
     * @return 被填充的对象
     */
    public static <T extends BaseModel> T fillCreateField(@NotNull T baseModel, Long userId) {
        baseModel.setCreator(userId);
        baseModel.setCreateTime(new Date());
        return baseModel;
    }

    /**
     * 填充更新的字段
     *
     * @param baseModel 被填充的对象
     * @param userId      更新人
     * @param <T>       被填充的类型
     * @return 被填充的对象
     */
    public static <T extends BaseModel> T fillModifyField(@NotNull T baseModel, Long userId) {
        baseModel.setModifier(userId);
        baseModel.setModifyTime(new Date());
        return baseModel;
    }

    /**
     * 填充创建的字段
     *
     * @param baseModel 被填充的对象
     * @param <T>       被填充的类型
     * @return 被填充的对象
     */
    public static <T extends BaseModel> T fillAllField(@NotNull T baseModel, Long userId) {
        Date now = new Date();
        baseModel.setCreator(userId);
        baseModel.setModifier(userId);
        baseModel.setCreateTime(now);
        baseModel.setModifyTime(now);
        return baseModel;
    }

}
