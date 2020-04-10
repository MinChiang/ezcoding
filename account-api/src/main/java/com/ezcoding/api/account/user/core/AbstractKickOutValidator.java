package com.ezcoding.api.account.user.core;

import java.util.Date;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2019-11-07 14:28
 */
public abstract class AbstractKickOutValidator implements IKickOutVerifiable {

    @Override
    public boolean isKickedOut(String userCode, Date tokenIssueTime) {
        if (tokenIssueTime == null) {
            //没有token的签发时间，默认为未踢出
            return false;
        }

        Date kickOutTime = this.accquireKickOutTime(userCode);
        if (kickOutTime == null) {
            //获取不到踢出时间，默认为未踢出
            return false;
        }

        //用户存在踢出记录，并且踢出记录时间比token签发的时间还要大，说明该用户token被踢出
        return kickOutTime.compareTo(tokenIssueTime) >= 0;
    }

    /**
     * 获取用户踢出时间
     *
     * @param userCode 用户唯一编号
     * @return 用户踢出时间
     */
    public abstract Date accquireKickOutTime(String userCode);

}
