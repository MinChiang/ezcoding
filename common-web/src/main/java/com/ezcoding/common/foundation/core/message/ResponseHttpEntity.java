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

    ResponseHttpEntity(HttpStatus status) {
        super(status);
    }

    ResponseHttpEntity(ResponseMessage<T> body, HttpStatus status) {
        super(body, status);
    }

    ResponseHttpEntity(MultiValueMap<String, String> headers, HttpStatus status) {
        super(headers, status);
    }

    ResponseHttpEntity(ResponseMessage<T> body, MultiValueMap<String, String> headers, HttpStatus status) {
        super(body, headers, status);
    }

}
