package com.ezcoding.module.user.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ezcoding.api.account.user.bean.dto.UserDetailResultDTO;
import com.ezcoding.api.account.user.bean.model.UserFieldDetailEnum;
import com.ezcoding.common.core.user.IUserIdentifiable;
import com.ezcoding.module.user.bean.model.User;
import org.apache.ibatis.annotations.Param;

import java.util.Collection;
import java.util.List;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2018-07-16 21:48
 */
public interface UserMapper extends BaseMapper<User> {

    /**
     * 根据用户编号搜索用户
     *
     * @param userFieldDetails 搜索的字段
     * @param codes            待搜索的用户编号
     * @return 搜索到的用户
     */
    List<UserDetailResultDTO> selectByCodes(@Param("userFieldDetails") List<UserFieldDetailEnum> userFieldDetails, @Param("codes") Collection<String> codes);

    /**
     * 根据用户编号搜索用户
     *
     * @param userFieldDetails 查询信息
     * @param code             待搜索的用户编号
     * @return 搜索到的用户
     */
    UserDetailResultDTO selectByCode(@Param("userFieldDetails") List<UserFieldDetailEnum> userFieldDetails, @Param("code") String code);

    /**
     * 根据给定的用户条件筛选出唯一用户
     *
     * @param user 用户可检索信息
     * @return 唯一的用户信息
     */
    User selectAuthenticationDetailByCondition(@Param("user") IUserIdentifiable user);

}
