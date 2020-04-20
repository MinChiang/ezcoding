package com.ezcoding.module.user.controller;

import com.ezcoding.common.core.user.model.DeviceTypeEnum;
import com.ezcoding.common.core.user.model.LoginRegisterTypeEnum;
import com.ezcoding.common.foundation.core.exception.processor.WebExceptionBuilderFactory;
import com.ezcoding.common.foundation.core.message.ResponseMessage;
import com.ezcoding.common.foundation.core.message.builder.ResponseMessageBuilder;
import com.ezcoding.common.foundation.util.AssertUtils;
import com.ezcoding.common.web.resolver.JsonParam;
import com.ezcoding.common.web.resolver.JsonResult;
import com.ezcoding.common.web.util.UserUtils;
import com.ezcoding.module.user.bean.model.User;
import com.ezcoding.module.user.bean.model.VerificationInfo;
import com.ezcoding.module.user.core.authentication.IAuthenticationService;
import com.ezcoding.module.user.service.IUserService;
import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.NotNull;
import java.util.HashMap;
import java.util.Map;

import static com.ezcoding.module.user.exception.AccountUserExceptionConstants.GEN_USER_EXIST_ERROR;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2019-04-01 19:37
 */
@Validated
@Controller
@RequestMapping("oauth")
public class AuthenticationController {

    @Autowired
    private IAuthenticationService authenticationService;
    @Autowired
    private IUserService userService;

    /**
     * 生成验证码
     *
     * @return 图形验证码和对应的回执
     */
    @PostMapping("verification")
    @JsonResult
    public ResponseMessage<?> generateImageVerificationCode() {
        VerificationInfo verificationInfo = userService.generateVerificationCode(null);
        String image = Base64.encodeBase64String(verificationInfo.getVerificationCode().getData());
        String receipt = verificationInfo.getReceipt();

        Map<String, Object> map = new HashMap<>();
        map.put("image", image);
        map.put("receipt", receipt);

        return ResponseMessageBuilder.success(map).build();
    }

    /**
     * 生成短信验证码
     *
     * @param tag 用户标志
     * @return 图形验证码和对应的回执
     */
    @PostMapping("message")
    @JsonResult
    public ResponseMessage<?> generateMessageCode(@JsonParam("tag") @NotNull(message = "{user.tag}") String tag) {
        // 校验手机号是否已注册
        AssertUtils.mustFalse(userService.exist(User.create().phone(tag)), () -> WebExceptionBuilderFactory.webExceptionBuilder(GEN_USER_EXIST_ERROR).build());

        VerificationInfo verificationInfo = userService.sendMessageCode(tag);
        String receipt = verificationInfo.getReceipt();

        Map<String, Object> map = new HashMap<>();
        map.put("tag", tag);
        map.put("receipt", receipt);

        return ResponseMessageBuilder.success(map).build();
    }

    /**
     * 用户统一注册入口
     *
     * @param context 请求上下文
     * @param request 请求
     * @return 返回用户编号
     */
    @PostMapping("register")
    @JsonResult
    public String register(@JsonParam Map<String, ?> context,
                           HttpServletRequest request) {

        //获取对应的登陆设备类型
        DeviceTypeEnum deviceType = UserUtils.distinguishDeviceType(request);
        //获取对应的登陆类型
        LoginRegisterTypeEnum loginType = UserUtils.distinguishLoginType(context.get(IAuthenticationService.REGISTER_TYPE_KEY));

        Map<String, Object> map = new HashMap<>(context);
        map.put(IAuthenticationService.DEVICE_TYPE, deviceType);
        map.put(IAuthenticationService.REGISTER_TYPE_KEY, loginType);

        //统一注册入口
        User user = authenticationService.register(map);
        return user.getCode();
    }
}
