package com.ezcoding.account.module.user.controller;

import com.ezcoding.account.module.user.bean.model.User;
import com.ezcoding.account.module.user.constant.UserConstants;
import com.ezcoding.account.module.user.exception.UserExceptionConstants;
import com.ezcoding.account.module.user.service.IUserService;
import com.ezcoding.base.web.extend.spring.resolver.CurrentUser;
import com.ezcoding.base.web.extend.spring.resolver.JsonParam;
import com.ezcoding.base.web.extend.spring.resolver.JsonResult;
import com.ezcoding.common.foundation.util.BeanUtils;
import com.ezcoding.sdk.account.user.api.IUser;
import com.ezcoding.sdk.account.user.bean.dto.UserDTO;
import com.ezcoding.sdk.account.user.bean.dto.UserDetailResultDTO;
import com.ezcoding.sdk.account.user.bean.model.UserFieldDetailEnum;
import com.ezcoding.sdk.account.user.constant.AccountUserApiConstants;
import com.google.common.collect.Sets;
import org.apache.commons.collections.CollectionUtils;
import org.redisson.Redisson;
import org.redisson.api.RLock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2018-09-05 23:05
 */
@Validated
@RestController
@RequestMapping(AccountUserApiConstants.USER_API)
public class UserController {

    @Autowired
    private IUserService userService;

    @Autowired
    private Redisson redisson;
//    @Autowired
//    private IAreaService areaService;

    /**
     * 校验用户是否存在(失焦自动触发)
     *
     * @param userDTO 用户信息
     * @return 校验用户是否存在
     */
    @PostMapping(AccountUserApiConstants.USER_IS_EXIST)
    @JsonResult
    public boolean exist(@JsonParam UserDTO userDTO) {
        User user = BeanUtils.copy(userDTO, User.class);
        // 校验唯一信息
        return userService.exist(user);
    }

//    /**
//     * 更新用户基本信息
//     *
//     * @param userModifyDTO 更改的信息
//     * @param user          当前登陆的用户
//     */
//    @PostMapping(UserApiConstants.MODIFY_INFO)
//    @JsonResult
//    public void modifyInfo(@JsonParam @Valid UserModifyDTO userModifyDTO,
//                           @CurrentUser IUser user) {
//        if (userModifyDTO.getCity() != null) {
//            //校验区域是否满足地域要求
//            AssertUtils.mustTrue(
//                    areaService.checkLevel(userModifyDTO.getCity(), LevelEnum.CITY),
//                    AreaExceptionConstants.AREA_NOT_CORRECT_LEVEL_ERROR
//            );
//        }
//        if (userModifyDTO.getProvince() != null) {
//            AssertUtils.mustTrue(
//                    areaService.checkLevel(userModifyDTO.getProvince(), LevelEnum.PROVINCE),
//                    AreaExceptionConstants.AREA_NOT_CORRECT_LEVEL_ERROR
//            );
//        }
//        if (userModifyDTO.getCity() != null && userModifyDTO.getProvince() != null) {
//            AssertUtils.mustTrue(
//                    areaService.checkDirectAffiliation(userModifyDTO.getProvince(), userModifyDTO.getCity()),
//                    AreaExceptionConstants.AREA_NOT_DIRECT_AFFILIATION_ERROR
//            );
//        }
//        userModifyDTO.setCode(user.getCode());
//        userService.modifyInfo(userModifyDTO);
//    }

    /**
     * 用户上传、变更头像
     *
     * @param file 上传的头像文件
     * @param user 当前用户
     */
    @PostMapping(AccountUserApiConstants.CHANGE_PROFILE_PHOTO)
    @JsonResult
    public void changeProfilePhoto(@RequestParam("file") MultipartFile file,
                                   @CurrentUser IUser user) {
        try {
            userService.changeProfilePhoto(file.getInputStream(), user.getCode());
        } catch (IOException e) {
            throw UserExceptionConstants.USER_PROFILE_PHOTO_UPLOAD_ERROR.instance().cause(e).build();
        }
    }

    /**
     * 根据code查找对应的用户
     * 若当前登录的用户为被查询的用户，则返回私密信息
     * 否则返回基本查询信息
     *
     * @param userFieldDetails 请求字段信息
     * @param code             查询的用户code
     * @param user             请求信息的用户，可以为空
     * @return 对应的用户信息
     */
    @PostMapping(AccountUserApiConstants.GET_USER_INFO)
    @JsonResult
    public UserDetailResultDTO getUserInfo(@JsonParam("details") @NotNull(message = "{user.userDetail}") Set<UserFieldDetailEnum> userFieldDetails,
                                           @JsonParam("code") @NotNull(message = "{user.code}") String code,
                                           @CurrentUser(required = false) IUser user) {
        if (user == null || !Objects.equals(user.getCode(), code)) {
            //只能查询非私密的信息
            List<UserDetailResultDTO> usersBaseInfo = userService.getUsersBaseInfo(userFieldDetails, Sets.newHashSet(code));
            if (CollectionUtils.isEmpty(usersBaseInfo)) {
                return null;
            }
            return usersBaseInfo.get(0);
        } else {
            //当前登陆人是被查询的用户，则可以查询对应的详细信息
            return userService.getUserDetailedInfo(userFieldDetails, code);
        }
    }

    /**
     * 根据code查找对应的用户
     * 仅查询用户的非私密信息
     *
     * @param userFieldDetails 请求字段信息
     * @param codes            用户编号
     * @return 对应的用户信息
     */
    @PostMapping(AccountUserApiConstants.LIST_USERS_INFO)
    @JsonResult
    public List<UserDetailResultDTO> listUsersInfo(@JsonParam("details") @NotNull(message = "{user.userDetail}") Set<UserFieldDetailEnum> userFieldDetails,
                                                   @JsonParam("codes") @NotNull(message = "{user.code}") @Size(message = "{user.code}", min = UserConstants.USER_CODE_SIZE_MIN, max = UserConstants.USER_CODE_SIZE_MAX) Set<String> codes) {
        return userService.getUsersBaseInfo(userFieldDetails, codes);
    }

    /**
     * 踢出用户
     *
     * @param user    被踢出的用户
     * @param handler 操作人
     */
    @PostMapping(AccountUserApiConstants.KICK_OUT)
    @JsonResult
    public void kickOut(@JsonParam("codes") String user,
                        @CurrentUser @NotNull(message = "{user.code}") IUser handler) {
        userService.kickOut(user);
    }

    @PostMapping("test")
    @JsonResult
//    @Secured({"LOGIN_TYPE_ACCOUNT_PASSWORD", "SCOPE_APP"})
//    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
//    @LoginInfo(loginTypes = {ACCOUNT_PASSWORD, EMAIL_PASSWORD})
    public Long test() {
        RLock lock = redisson.getLock("test::aaa");
        lock.lock(100L, TimeUnit.SECONDS);
        System.out.println("OK");
        lock.unlock();
        return 1L;
    }

    @ResponseBody
    @PostMapping("test2")
    public Map<String, Object> test(@RequestBody Map<String, Object> map) {
        System.out.println(map);
        Map<String, Object> m = new HashMap<>();
        m.put("test", 1L);
        return m;
    }

}
