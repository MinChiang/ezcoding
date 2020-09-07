package com.ezcoding.common.foundation.core.message;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2020-04-13 13:53
 */
public class ResponseHttpEntity<T> extends ResponseEntity<ResponseMessage<T>> {

    public ResponseHttpEntity(HttpStatus status) {
        super(status);
    }

    public ResponseHttpEntity(ResponseMessage<T> body, HttpStatus status) {
        super(body, status);
    }

    public ResponseHttpEntity(MultiValueMap<String, String> headers, HttpStatus status) {
        super(headers, status);
    }

    public ResponseHttpEntity(ResponseMessage<T> body, MultiValueMap<String, String> headers, HttpStatus status) {
        super(body, headers, status);
    }

}
