package com.ezcoding.common.foundation.core.message;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2020-04-13 13:53
 */
public class StandardResponseEntity<T> extends ResponseEntity<ResponseMessage<T>> {

    StandardResponseEntity(HttpStatus status) {
        super(status);
    }

    StandardResponseEntity(ResponseMessage<T> body, HttpStatus status) {
        super(body, status);
    }

    StandardResponseEntity(MultiValueMap<String, String> headers, HttpStatus status) {
        super(headers, status);
    }

    StandardResponseEntity(ResponseMessage<T> body, MultiValueMap<String, String> headers, HttpStatus status) {
        super(body, headers, status);
    }

}
