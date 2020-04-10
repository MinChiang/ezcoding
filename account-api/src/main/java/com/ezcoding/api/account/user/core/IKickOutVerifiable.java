package com.ezcoding.api.account.user.core;

import java.util.Date;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2019-11-07 14:24
 */
public interface IKickOutVerifiable {

    /**
     * 判断用户是否被踢出
     *
     * @param userCode       用户唯一编号
     * @param tokenIssueTime 用户令牌签发时间
     * @return 用户是否被踢出
     */
    boolean isKickedOut(String userCode, Date tokenIssueTime);

}
