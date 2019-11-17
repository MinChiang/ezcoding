package com.ezcoding.account.module.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ezcoding.account.config.SmsConfig;
import com.ezcoding.account.extend.cos.CosSettings;
import com.ezcoding.account.extend.user.KickOutHandler;
import com.ezcoding.account.module.user.bean.model.User;
import com.ezcoding.account.module.user.bean.model.VerificationInfo;
import com.ezcoding.account.module.user.constant.UserConstants;
import com.ezcoding.account.module.user.core.verification.RedisVerificationServiceImpl;
import com.ezcoding.account.module.user.dao.UserMapper;
import com.ezcoding.account.module.user.exception.UserExceptionConstants;
import com.ezcoding.account.module.user.service.IUserService;
import com.ezcoding.common.foundation.core.message.RequestMessage;
import com.ezcoding.common.foundation.core.message.ResponseMessage;
import com.ezcoding.common.foundation.core.message.builder.RequestMessageBuilder;
import com.ezcoding.common.foundation.util.AssertUtils;
import com.ezcoding.common.foundation.util.BeanUtils;
import com.ezcoding.common.foundation.util.ImageUtils;
import com.ezcoding.sdk.account.user.bean.dto.UserDetailResultDTO;
import com.ezcoding.sdk.account.user.bean.dto.UserModifyDTO;
import com.ezcoding.sdk.account.user.bean.model.UserFieldDetailEnum;
import com.ezcoding.sdk.account.user.bean.model.UserStatusEnum;
import com.ezcoding.sdk.facility.area.bean.dto.AreaDTO;
import com.ezcoding.sdk.facility.area.bean.model.LevelEnum;
import com.ezcoding.sdk.facility.message.api.AreaFeignClient;
import com.github.qcloudsms.SmsMultiSender;
import com.github.qcloudsms.SmsMultiSenderResult;
import com.github.qcloudsms.httpclient.HTTPException;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.qcloud.cos.model.PutObjectRequest;
import com.qcloud.cos.transfer.TransferManager;
import com.qcloud.cos.transfer.Upload;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import static com.ezcoding.account.module.user.exception.UserExceptionConstants.USER_PROFILE_PHOTO_UPLOAD_ERROR;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2018-11-16 11:31
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

    private static final List<UserFieldDetailEnum> PRIVATE_DETAILS;
    private static final List<UserFieldDetailEnum> NORMAL_DETAILS;
    private static final List<UserFieldDetailEnum> EXTRA_DETAILS;
    private static final List<UserFieldDetailEnum> BASIC_DETAILS;

    static {
        PRIVATE_DETAILS = ImmutableList.of(
                UserFieldDetailEnum.ACCOUNT,
                UserFieldDetailEnum.PHONE,
                UserFieldDetailEnum.EMAIL,
                UserFieldDetailEnum.ADDRESS,
                UserFieldDetailEnum.BIRTHDAY,
                UserFieldDetailEnum.HIRE_DATE
        );

        List<UserFieldDetailEnum> normalDetails = Lists.newArrayList(UserFieldDetailEnum.values());
        normalDetails.removeAll(PRIVATE_DETAILS);
        NORMAL_DETAILS = ImmutableList.copyOf(normalDetails);

        EXTRA_DETAILS = ImmutableList.of(
                UserFieldDetailEnum.AREA
        );

        List<UserFieldDetailEnum> basicDetails = Lists.newArrayList(UserFieldDetailEnum.values());
        basicDetails.removeAll(EXTRA_DETAILS);
        BASIC_DETAILS = ImmutableList.copyOf(basicDetails);
    }

    @Autowired
    private TransferManager transferManager;
    @Autowired
    private IUserService userService;
    @Autowired
    private SmsMultiSender smsMultiSender;
    @Autowired
    private SmsConfig smsConfig;
    @Autowired
    private AreaFeignClient areaFeignClient;
    @Resource(name = "headerCosSettings")
    private CosSettings headerCosSettings;
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
                UserExceptionConstants.USER_NOT_EXIST_ERROR
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
            throw UserExceptionConstants.USER_SEND_MESSAGE_ERROR.instance().cause(e).build();
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
    public byte[] changeProfilePhoto(InputStream is, String code) {
        byte[] result = null;
        try {
            //将图片转为合适的大小以及格式
            result = ImageUtils.formatImageSize(is, UserConstants.HEADER_SUFFIX, UserConstants.HEADER_WIDTH, UserConstants.HEADER_HEIGHT);
            //上传图片
            String url = uploadProfilePhoto(result, code);
            //更改用户头像
            update(User.create().profilePhoto(url),
                    Wrappers.query(User.create().code(code)));
        } catch (IOException e) {
            throw USER_PROFILE_PHOTO_UPLOAD_ERROR.instance().cause(e).build();
        }
        return result;
    }

    /**
     * 上传用户头像
     *
     * @param file 用户头像
     * @param code 更换头像的用户编号
     */
    private String uploadProfilePhoto(byte[] file, String code) {
        String key = headerCosSettings.getBasePath() + code + "." + UserConstants.HEADER_SUFFIX;
        PutObjectRequest putObjectRequest = new PutObjectRequest(headerCosSettings.getBucketName(), key, new ByteArrayInputStream(file), null);
        Upload upload = transferManager.upload(putObjectRequest);
        try {
            upload.waitForUploadResult();
            return headerCosSettings.getWholeBasePath() + code + "." + UserConstants.HEADER_SUFFIX;
        } catch (InterruptedException e) {
            throw USER_PROFILE_PHOTO_UPLOAD_ERROR.instance().cause(e).build();
        }
    }

    @Override
    public UserDetailResultDTO getUserDetailedInfo(Set<UserFieldDetailEnum> userFieldDetails, String code) {
        //处理简单基本信息
        List<UserFieldDetailEnum> basicDetails = Lists.newArrayList(BASIC_DETAILS);
        basicDetails.retainAll(userFieldDetails);
        UserDetailResultDTO userDetailResultDTO = baseMapper.selectByCode(basicDetails, code);

        if (userDetailResultDTO == null) {
            return userDetailResultDTO;
        }

        List<UserFieldDetailEnum> extraDetails = Lists.newArrayList(EXTRA_DETAILS);
        extraDetails.retainAll(userFieldDetails);
        for (UserFieldDetailEnum extraDetail : extraDetails) {
            switch (extraDetail) {
                case AREA:
                    Set<Long> ids = Sets.newHashSet(userDetailResultDTO.getProvince(), userDetailResultDTO.getCity());
                    RequestMessage<Set<Long>> build = RequestMessageBuilder.create(ids).build();
                    ResponseMessage<Collection<AreaDTO>> responseMessage = areaFeignClient.searchAreaByIds(build);
                    Collection<AreaDTO> payload = responseMessage.getPayload();
                    for (AreaDTO areaDTO : payload) {
                        if (LevelEnum.PROVINCE.equals(areaDTO.getLevel())) {
                            userDetailResultDTO.setProvinceName(areaDTO.getName());
                        } else if (LevelEnum.CITY.equals(areaDTO.getLevel())) {
                            userDetailResultDTO.setCityName(areaDTO.getName());
                        }
                    }
                    break;
                default:
                    break;
            }
        }
        return userDetailResultDTO;
    }

    @Override
    public List<UserDetailResultDTO> getUsersBaseInfo(Set<UserFieldDetailEnum> userFieldDetails, Set<String> codes) {
        List<UserFieldDetailEnum> normalDetails = Lists.newArrayList(NORMAL_DETAILS);
        normalDetails.retainAll(userFieldDetails);
        List<UserFieldDetailEnum> basicDetails = Lists.newArrayList(BASIC_DETAILS);
        basicDetails.retainAll(normalDetails);

        List<UserDetailResultDTO> userDetailResultDTOs = baseMapper.selectByCodes(basicDetails, codes);

        if (userDetailResultDTOs.size() == 0) {
            return userDetailResultDTOs;
        }

        List<UserFieldDetailEnum> extraDetails = Lists.newArrayList(EXTRA_DETAILS);
        extraDetails.retainAll(userFieldDetails);
        for (UserFieldDetailEnum extraDetail : extraDetails) {
            switch (extraDetail) {
                case AREA:
                    Set<Long> ids = Sets.newHashSet();
                    userDetailResultDTOs.forEach(u -> {
                        ids.add(u.getProvince());
                        ids.add(u.getCity());
                    });
                    RequestMessage<Set<Long>> build = RequestMessageBuilder.create(ids).build();
                    ResponseMessage<Collection<AreaDTO>> responseMessage = areaFeignClient.searchAreaByIds(build);
                    Collection<AreaDTO> payload = responseMessage.getPayload();
                    Map<Long, AreaDTO> mapping = payload.stream().collect(Collectors.toMap(AreaDTO::getId, p -> p));
                    for (UserDetailResultDTO userDetailResultDTO : userDetailResultDTOs) {
                        Long province = userDetailResultDTO.getProvince();
                        Long city = userDetailResultDTO.getCity();
                        if (province != null) {
                            userDetailResultDTO.setProvinceName(mapping.get(province).getName());
                        }
                        if (city != null) {
                            userDetailResultDTO.setCityName(mapping.get(city).getName());
                        }
                    }
                    break;
                default:
                    break;
            }
        }
        return userDetailResultDTOs;
    }

    @Override
    public void kickOut(String code) {
        kickOutHandler.kickOut(code);
    }

}
