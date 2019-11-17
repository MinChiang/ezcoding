package com.ezcoding.common.foundation.core.message.builder;

import com.ezcoding.common.foundation.core.message.ResponseMessage;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2019-09-01 14:41
 */
public class SuccessResponseBuilder<T> extends AbstractBodyBuilder<T> {

    private Long totalItem;

    public SuccessResponseBuilder<T> totalItem(Long totalItem) {
        this.totalItem = totalItem;
        return this;
    }

    SuccessResponseBuilder(T body) {
        super(body);
    }

    @Override
    public ResponseMessage<T> build() {
        if (this.totalItem == null) {
            return MessageBuilder.getInstance().buildSuccessResponseMessage(this.body);
        }
        return MessageBuilder.getInstance().buildSuccessResponseMessage(this.totalItem, this.body);
    }

}
