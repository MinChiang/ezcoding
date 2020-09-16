package com.ezcoding.common.foundation.core.exception.processor;

import java.util.HashMap;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2020-01-17 10:34
 */
public class ProcessContext extends HashMap<String, Object> {

    private static final long serialVersionUID = 7226279708779364868L;

    /**
     * 是否成功被处理标志
     */
    private boolean processed = false;

    public boolean isProcessed() {
        return processed;
    }

    public void setProcessed(boolean processed) {
        this.processed = processed;
    }

}
