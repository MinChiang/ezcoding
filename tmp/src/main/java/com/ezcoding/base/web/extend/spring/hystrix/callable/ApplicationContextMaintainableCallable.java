package com.ezcoding.base.web.extend.spring.hystrix.callable;

import com.ezcoding.common.foundation.core.context.ApplicationContextHolder;

import java.util.Map;
import java.util.concurrent.Callable;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2019-10-31 21:57
 */
public class ApplicationContextMaintainableCallable<T> implements Callable<T> {

    private final Callable<T> delegate;

    private final Map<String, Object> context;

    public ApplicationContextMaintainableCallable(Callable<T> delegate) {
        this.delegate = delegate;
        context = ApplicationContextHolder.getInstance().getContext();
    }

    @Override
    public T call() throws Exception {
        try {
            ApplicationContextHolder.getInstance().setContext(context);
            return delegate.call();
        } finally {
            ApplicationContextHolder.getInstance().clear();
        }
    }

}
