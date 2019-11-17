package com.ezcoding.common.foundation.core.message.head;

import java.io.Serializable;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2018-07-14 17:08
 */
public class RequestAppHead extends AbstractAppHead implements Serializable {

    public RequestAppHead() {
    }

    public RequestAppHead(PageInfo pageInfo) {
        super(pageInfo);
    }
}
