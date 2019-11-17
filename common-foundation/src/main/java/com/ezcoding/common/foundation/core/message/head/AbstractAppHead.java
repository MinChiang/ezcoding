package com.ezcoding.common.foundation.core.message.head;

import com.fasterxml.jackson.annotation.JsonUnwrapped;

import java.io.Serializable;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2018-07-14 17:08
 */
public abstract class AbstractAppHead implements IHead, Serializable {

    @JsonUnwrapped
    protected PageInfo pageInfo;

    public AbstractAppHead() {
    }

    public AbstractAppHead(PageInfo pageInfo) {
        this.pageInfo = pageInfo;
    }

    public PageInfo getPageInfo() {
        return pageInfo;
    }

    public void setPageInfo(PageInfo pageInfo) {
        this.pageInfo = pageInfo;
    }
}
