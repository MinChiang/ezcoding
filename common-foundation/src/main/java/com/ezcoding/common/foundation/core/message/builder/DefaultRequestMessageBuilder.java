package com.ezcoding.common.foundation.core.message.builder;

import com.ezcoding.common.foundation.core.message.RequestMessage;
import com.ezcoding.common.foundation.core.message.head.PageInfo;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2019-09-01 15:03
 */
public class DefaultRequestMessageBuilder<T> extends AbstractBodyBuilder<T> {

    private Integer pageSize;
    private Integer currentPage;

    DefaultRequestMessageBuilder(T body) {
        super(body);
    }

    DefaultRequestMessageBuilder() {
        this(null);
    }

    public DefaultRequestMessageBuilder<T> pageSize(Integer pageSize) {
        this.pageSize = pageSize;
        return this;
    }

    public DefaultRequestMessageBuilder<T> currentPage(Integer currentPage) {
        this.currentPage = currentPage;
        return this;
    }

    @Override
    public RequestMessage<T> build() {
        if (this.pageSize == null && this.currentPage == null) {
            return MessageBuilder.getInstance().buildRequestMessage(this.body);
        }
        return MessageBuilder.getInstance().buildRequestMessage(new PageInfo(this.currentPage, this.pageSize), this.body);
    }

}
