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
     * @param user      创建人
     * @param <T>       被填充的类型
     * @return 被填充的对象
     */
    public static <T extends BaseModel> T fillCreateField(@NotNull T baseModel, Date date, String user) {
        baseModel.setCreator(user);
        baseModel.setCreateTime(date);
        return baseModel;
    }

    /**
     * 填充更新的字段
     *
     * @param baseModel 被填充的对象
     * @param date      更新时间
     * @param user      更新人
     * @param <T>       被填充的类型
     * @return 被填充的对象
     */
    public static <T extends BaseModel> T fillModifyField(@NotNull T baseModel, Date date, String user) {
        baseModel.setModifier(user);
        baseModel.setModifyTime(date);
        return baseModel;
    }

    /**
     * 填充所有字段
     *
     * @param baseModel 被填充的对象
     * @param date      创建时间
     * @param user      创建人
     * @param <T>       被填充的类型
     * @return 被填充的对象
     */
    public static <T extends BaseModel> T fillAllField(@NotNull T baseModel, Date date, String user) {
        fillCreateField(baseModel, date, user);
        fillModifyField(baseModel, date, user);
        return baseModel;
    }

//    /**
//     * 填充创建的字段
//     *
//     * @param baseModel 被填充的对象
//     * @param <T>       被填充的类型
//     * @return 被填充的对象
//     */
//    public static <T extends BaseModel> T fillCreateField(@NotNull T baseModel) {
//        IUser user = UserResolver.getInstance().currentUser();
//        if (user != null) {
//            baseModel.setCreator(user.getCode());
//        }
//        baseModel.setCreateTime(new Date());
//        return baseModel;
//    }
//
//    /**
//     * 填充更新的字段
//     *
//     * @param baseModel 被填充的对象
//     * @param <T>       被填充的类型
//     * @return 被填充的对象
//     */
//    public static <T extends BaseModel> T fillModifyField(@NotNull T baseModel) {
//        IUser user = UserResolver.getInstance().currentUser();
//        if (user != null) {
//            baseModel.setModifier(user.getCode());
//        }
//        baseModel.setModifyTime(new Date());
//        return baseModel;
//    }
//
//    /**
//     * 填充创建的字段
//     *
//     * @param baseModel 被填充的对象
//     * @param <T>       被填充的类型
//     * @return 被填充的对象
//     */
//    public static <T extends BaseModel> T fillAllField(@NotNull T baseModel) {
//        IUser user = UserResolver.getInstance().currentUser();
//        Date now = new Date();
//        if (user != null) {
//            baseModel.setCreator(user.getCode());
//            baseModel.setModifier(user.getCode());
//        }
//        baseModel.setCreateTime(now);
//        baseModel.setModifyTime(now);
//        return baseModel;
//    }

    /**
     * 填充创建的字段
     *
     * @param baseModel 被填充的对象
     * @param user      创建人
     * @param <T>       被填充的类型
     * @return 被填充的对象
     */
    public static <T extends BaseModel> T fillCreateField(@NotNull T baseModel, String user) {
        baseModel.setCreator(user);
        baseModel.setCreateTime(new Date());
        return baseModel;
    }

    /**
     * 填充更新的字段
     *
     * @param baseModel 被填充的对象
     * @param user      更新人
     * @param <T>       被填充的类型
     * @return 被填充的对象
     */
    public static <T extends BaseModel> T fillModifyField(@NotNull T baseModel, String user) {
        baseModel.setModifier(user);
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
    public static <T extends BaseModel> T fillAllField(@NotNull T baseModel, String user) {
        Date now = new Date();
        baseModel.setCreator(user);
        baseModel.setModifier(user);
        baseModel.setCreateTime(now);
        baseModel.setModifyTime(now);
        return baseModel;
    }

}
