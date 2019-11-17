package com.ezcoding.account.module.user.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ezcoding.account.module.user.bean.model.User;
import com.ezcoding.account.module.user.bean.model.VerificationInfo;
import com.ezcoding.sdk.account.user.bean.dto.UserDetailResultDTO;
import com.ezcoding.sdk.account.user.bean.dto.UserModifyDTO;
import com.ezcoding.sdk.account.user.bean.model.UserFieldDetailEnum;
import com.ezcoding.sdk.account.user.bean.model.UserStatusEnum;

import java.io.InputStream;
import java.util.List;
import java.util.Set;

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
     * 更换用户头像
     *
     * @param is   上传的头像输入流
     * @param code 更改头像的用户
     * @return 处理后的用户头像
     */
    byte[] changeProfilePhoto(InputStream is, String code);

    /**
     * 获取用户详细信息（包含隐私信息）
     *
     * @param userFieldDetails 请求字段信息
     * @param code             用户编号
     * @return 对应的用户信息
     */
    UserDetailResultDTO getUserDetailedInfo(Set<UserFieldDetailEnum> userFieldDetails, String code);

    /**
     * 批量查询用户的可见信息（不包含隐私信息）
     *
     * @param userFieldDetails 请求字段信息
     * @param codes            用户编号
     * @return 对应的用户信息
     */
    List<UserDetailResultDTO> getUsersBaseInfo(Set<UserFieldDetailEnum> userFieldDetails, Set<String> codes);

    /**
     * 踢出用户
     *
     * @param code 需要踢出的用户
     */
    void kickOut(String code);

}
