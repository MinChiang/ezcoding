package com.ezcoding.common.foundation.core.message;

import java.io.Serializable;
import java.util.Optional;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2018-07-14 17:08
 */
public class ResponseAppHead extends AbstractAppHead implements Serializable {

    private static final long serialVersionUID = 4369838938491033889L;

    protected String code;
    protected String message;

    public ResponseAppHead() {
    }

    public ResponseAppHead(PageInfo pageInfo) {
        super(pageInfo);
    }

    public ResponseAppHead(PageInfo pageInfo, String code, String message) {
        this.code = code;
        this.message = message;
        this.pageInfo = pageInfo;
    }

    public ResponseAppHead(String code, String message) {
        this(null, code, message);
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * 判断是否成功
     *
     * @return 是否成功
     */
    public boolean success() {
        return Optional.ofNullable(this.code).map(SuccessAppHead.getDefaultSuccessCode()::equals).orElse(false);
    }

    @Override
    public boolean valid() {
        return super.valid() && (code != null && !code.isEmpty()) && (message != null && !message.isEmpty());
    }

    @Override
    public String toString() {
        return "ResponseAppHead{" +
                "pageInfo=" + pageInfo +
                ", code='" + code + '\'' +
                ", message='" + message + '\'' +
                '}';
    }

}
