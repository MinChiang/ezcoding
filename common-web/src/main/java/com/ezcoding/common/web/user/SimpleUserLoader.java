package com.ezcoding.common.web.user;

import com.ezcoding.common.core.user.UserBasicIdentifiable;
import com.ezcoding.common.core.user.UserLoadable;
import com.ezcoding.common.core.user.model.UserBasicLoginInfo;
import org.springframework.web.client.RestOperations;
import org.springframework.web.client.RestTemplate;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2021-03-09 17:55
 */
public class SimpleUserLoader implements UserLoadable {

    private RestOperations operations = new RestTemplate();
    private final String url;

    public SimpleUserLoader(RestOperations operations, String url) {
        if (url == null || url.isEmpty()) {
            throw new RuntimeException("url can't be null or empty!");
        }
        if (operations != null) {
            this.operations = operations;
        }
        this.url = url;
    }

    public SimpleUserLoader(String url) {
        this(null, url);
    }

    @Override
    public UserBasicIdentifiable load() {
        UserBasicLoginInfo userBasicLoginInfo = new UserBasicLoginInfo();
//        MessageFactory.buildRequestMessage()
//        operations.postForEntity(url, )
        return userBasicLoginInfo;
    }

}
