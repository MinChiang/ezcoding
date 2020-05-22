package com.ezcoding.common.foundation.core.message.head;

import com.fasterxml.jackson.annotation.JsonUnwrapped;

import java.io.Serializable;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2018-07-14 17:08
 */
public abstract class AbstractAppHead implements Head, Serializable {

    private static final long serialVersionUID = -2411208071368843464L;

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
