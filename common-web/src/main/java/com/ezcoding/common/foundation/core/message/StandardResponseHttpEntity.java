package com.ezcoding.common.foundation.core.message;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2020-04-13 13:53
 */
public class StandardResponseHttpEntity<T> extends ResponseEntity<ResponseMessage<T>> {

    public StandardResponseHttpEntity(HttpStatus status) {
        super(status);
    }

    public StandardResponseHttpEntity(ResponseMessage<T> body, HttpStatus status) {
        super(body, status);
    }

    public StandardResponseHttpEntity(MultiValueMap<String, String> headers, HttpStatus status) {
        super(headers, status);
    }

    public StandardResponseHttpEntity(ResponseMessage<T> body, MultiValueMap<String, String> headers, HttpStatus status) {
        super(body, headers, status);
    }

}
