package com.ezcoding.module.user.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ezcoding.common.core.user.model.UserStatusEnum;
import com.ezcoding.api.account.user.bean.dto.UserModifyDTO;
import com.ezcoding.module.user.bean.model.User;
import com.ezcoding.module.user.bean.model.VerificationInfo;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2018-11-16 11:31
 */
public interface IUserService extends IService<User> {

    /**
     * 更新或者插入用户信息
     *
     * @param userModifyDTO 用户信息
     */
    void modifyInfo(UserModifyDTO userModifyDTO);

    /**
     * 判断用户是否存在
     *
     * @param user 需要判断的用户实体
     * @return 用户是否存在
     */
    boolean exist(User user);

    /**
     * 发送验证码短信
     *
     * @param phone 手机号
     * @return 生成的验证码
     */
    VerificationInfo sendMessageCode(String phone);

    /**
     * 生成图形验证码
     *
     * @param tag 用户标志
     * @return 生成的验证码
     */
    VerificationInfo generateVerificationCode(String tag);

    /**
     * 判断用户能否正常使用
     *
     * @param userStatusEnum 用户状态
     * @return 能否正常使用
     */
    boolean isUserValid(UserStatusEnum userStatusEnum);

    /**
     * 踢出用户
     *
     * @param code 需要踢出的用户
     */
    void kickOut(String code);

}
