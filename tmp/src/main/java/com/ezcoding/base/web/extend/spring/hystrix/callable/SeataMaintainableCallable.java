package com.ezcoding.base.web.extend.spring.hystrix.callable;

import com.ezcoding.common.foundation.core.context.ApplicationContextHolder;
import io.seata.core.context.RootContext;

import java.util.concurrent.Callable;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2019-10-31 21:57
 */
public class SeataMaintainableCallable<T> implements Callable<T> {

    private final Callable<T> delegate;

    private final String xid;

    public SeataMaintainableCallable(Callable<T> delegate) {
        this.delegate = delegate;
        xid = RootContext.getXID();
    }

    @Override
    public T call() throws Exception {
        try {
            RootContext.bind(xid);
            return delegate.call();
        } finally {
            ApplicationContextHolder.getInstance().clear();
        }
    }

}
