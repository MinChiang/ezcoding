package com.ezcoding.common.foundation.core.message;

import java.io.Serializable;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2018-07-14 17:08
 */
public class RequestAppHead extends AbstractAppHead implements Serializable {

    private static final long serialVersionUID = -6695279371779351920L;

    RequestAppHead() {
    }

    RequestAppHead(PageInfo pageInfo) {
        super(pageInfo);
    }

    @Override
    public String toString() {
        return "RequestAppHead{" +
                "pageInfo=" + pageInfo +
                '}';
    }

}
