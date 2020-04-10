package com.ezcoding.module.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ezcoding.common.core.user.model.UserStatusEnum;
import com.ezcoding.common.foundation.core.exception.processor.WebExceptionBuilder;
import com.ezcoding.common.foundation.core.exception.processor.WebExceptionBuilderFactory;
import com.ezcoding.common.foundation.util.AssertUtils;
import com.ezcoding.common.foundation.util.BeanUtils;
import com.github.qcloudsms.SmsMultiSender;
import com.github.qcloudsms.SmsMultiSenderResult;
import com.github.qcloudsms.httpclient.HTTPException;
import com.ezcoding.api.account.user.bean.dto.UserModifyDTO;
import com.ezcoding.config.SmsConfig;
import com.ezcoding.extend.user.KickOutHandler;
import com.ezcoding.module.user.bean.model.User;
import com.ezcoding.module.user.bean.model.VerificationInfo;
import com.ezcoding.module.user.core.verification.RedisVerificationServiceImpl;
import com.ezcoding.module.user.dao.UserMapper;
import com.ezcoding.module.user.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.io.IOException;

import static com.ezcoding.module.user.exception.AccountUserExceptionConstants.GEN_USER_NOT_EXIST_ERROR;
import static com.ezcoding.module.user.exception.AccountUserExceptionConstants.GEN_USER_SEND_MESSAGE_ERROR;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2018-11-16 11:31
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

    @Autowired
    private IUserService userService;
    @Autowired
    private SmsMultiSender smsMultiSender;
    @Autowired
    private SmsConfig smsConfig;
    @Resource(name = "imageVerificationService")
    private RedisVerificationServiceImpl imageVerificationService;
    @Resource(name = "numberVerificationService")
    private RedisVerificationServiceImpl numberVerificationService;
    @Autowired
    private KickOutHandler kickOutHandler;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void modifyInfo(UserModifyDTO userModifyDTO) {
        QueryWrapper<User> query = Wrappers.query(User.create().code(userModifyDTO.getCode()));
        //用户必须存在的情况下才能修改对应的用户信息
        AssertUtils.mustTrue(
                userService.getOne(query) != null,
                () -> WebExceptionBuilderFactory.webExceptionBuilder(GEN_USER_NOT_EXIST_ERROR).build()
        );
        User user = BeanUtils.copy(userModifyDTO, User.class);
        update(user, query);
    }

    @Override
    public boolean exist(User user) {
        User tmp = User.create();
        if (user.getId() != null) {
            tmp.setId(user.getId());
        } else if (user.getCode() != null) {
            tmp.setCode(user.getCode());
        } else if (user.getAccount() != null) {
            tmp.setAccount(user.getAccount());
        } else if (user.getPhone() != null) {
            tmp.setPhone(user.getPhone());
        } else if (user.getEmail() != null) {
            tmp.setEmail(user.getEmail());
        }
        return getOne(Wrappers.query(tmp)) != null;
    }

    @Override
    public VerificationInfo sendMessageCode(String phone) {
        VerificationInfo verificationInfo = numberVerificationService.generate(phone);
        try {
            SmsMultiSenderResult result = smsMultiSender.sendWithParam(
                    "86",
                    new String[]{phone}, smsConfig.getRegisterTemplateId(),
                    new String[]{verificationInfo.getVerificationCode().getCode(), "2"},
                    smsConfig.getSmsSign(),
                    "",
                    ""
            );
        } catch (HTTPException | IOException e) {
            WebExceptionBuilder webExceptionBuilder = WebExceptionBuilderFactory.webExceptionBuilder(GEN_USER_SEND_MESSAGE_ERROR);
            webExceptionBuilder.setCause(e);
            throw webExceptionBuilder.build();
        }
        return verificationInfo;
    }

    @Override
    public VerificationInfo generateVerificationCode(String tag) {
        return imageVerificationService.generate(tag);
    }

    @Override
    public boolean isUserValid(UserStatusEnum userStatusEnum) {
        return userStatusEnum == UserStatusEnum.NORMAL;
    }

    @Override
    public void kickOut(String code) {
        kickOutHandler.kickOut(code);
    }

}
