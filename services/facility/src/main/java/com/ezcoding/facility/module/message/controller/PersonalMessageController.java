package com.ezcoding.facility.module.message.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ezcoding.base.web.extend.spring.resolver.CurrentUser;
import com.ezcoding.base.web.extend.spring.resolver.JsonPage;
import com.ezcoding.base.web.extend.spring.resolver.JsonParam;
import com.ezcoding.base.web.extend.spring.resolver.JsonResult;
import com.ezcoding.facility.module.message.bean.model.PersonalMessage;
import com.ezcoding.facility.module.message.bean.model.PersonalMessageSearchCondition;
import com.ezcoding.facility.module.message.service.IPersonalMessageService;
import com.ezcoding.sdk.facility.message.constant.FacilityPersonalMessageApiConstants;
import com.ezcoding.sdk.account.user.api.IUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;
import java.util.Set;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2019-02-23 15:01
 */
@Validated
@RestController
@RequestMapping(FacilityPersonalMessageApiConstants.PERSONAL_MESSAGE_API)
public class PersonalMessageController {

    @Autowired
    private IPersonalMessageService personalMessageService;

    /**
     * 发送私信
     *
     * @param content   发送内容
     * @param receivers 收信人
     * @param user      当前登录用户
     * @return 用户消息分页
     */
    @PostMapping(FacilityPersonalMessageApiConstants.SEND)
    @JsonResult
    public List<PersonalMessage> send(@JsonParam("content") @NotEmpty(message = "{personalMessage.content}") String content,
                                      @JsonParam("receivers") @NotNull(message = "{personalMessage.receivers}") @Size(message = "{personalMessage.receivers}") List<String> receivers,
                                      @CurrentUser IUser user) {
        return personalMessageService.send(content, user.getCode(), receivers);
    }

    /**
     * 获取未读私信数量
     *
     * @param personalMessageSearchCondition 筛选条件
     * @param user                           当前登录用户
     * @return 用户未读信息数量
     */
    @PostMapping(FacilityPersonalMessageApiConstants.COUNT_UNREAD)
    @JsonResult
    public Integer countUnread(@JsonParam PersonalMessageSearchCondition personalMessageSearchCondition,
                               @CurrentUser IUser user) {
        if (personalMessageSearchCondition == null) {
            personalMessageSearchCondition = new PersonalMessageSearchCondition();
        }
        personalMessageSearchCondition.setReceiver(user.getCode());
        return personalMessageService.countUnread(personalMessageSearchCondition);
    }

    /**
     * 获取用户私信
     *
     * @param personalMessageSearchCondition 筛选条件
     * @param page                           分页信息
     * @param user                           当前登录用户
     * @return 用户私信
     */
    @PostMapping(FacilityPersonalMessageApiConstants.FETCH)
    @JsonResult
    public Page<PersonalMessage> fetch(@JsonParam PersonalMessageSearchCondition personalMessageSearchCondition,
                                       @JsonPage Page<PersonalMessage> page,
                                       @CurrentUser IUser user) {
        if (personalMessageSearchCondition == null) {
            personalMessageSearchCondition = new PersonalMessageSearchCondition();
        }
        personalMessageSearchCondition.setReceiver(user.getCode());
        return personalMessageService.fetch(personalMessageSearchCondition, page);
    }

    /**
     * 读取私信
     *
     * @param ids  私信id
     * @param user 当前登录用户
     */
    @PostMapping(FacilityPersonalMessageApiConstants.READ)
    @JsonResult
    public void read(@JsonParam @NotNull(message = "{personalMessage.ids}") Set<Long> ids,
                     @CurrentUser IUser user) {
        personalMessageService.read(user.getCode(), ids);
    }

    /**
     * 读取所有未读私信
     *
     * @param user 当前登录用户
     */
    @PostMapping(FacilityPersonalMessageApiConstants.READ_ALL)
    @JsonResult
    public void readAll(@CurrentUser IUser user) {
        personalMessageService.readAll(user.getCode());
    }

    /**
     * 删除私信
     *
     * @param ids  私信id
     * @param user 当前登录用户
     */
    @PostMapping(FacilityPersonalMessageApiConstants.DELETE)
    @JsonResult
    public void delete(@JsonParam @NotNull(message = "{personalMessage.ids}") Set<Long> ids,
                       @CurrentUser IUser user) {
        personalMessageService.delete(user.getCode(), ids);
    }

    /**
     * 删除所有私信
     *
     * @param user 当前登录用户
     */
    @PostMapping(FacilityPersonalMessageApiConstants.DELETE_ALL)
    @JsonResult
    public void deleteAll(@CurrentUser IUser user) {
        personalMessageService.deleteAll(user.getCode());
    }

}
