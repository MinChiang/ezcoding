package com.ezcoding.account.config;

import com.ezcoding.account.extend.user.LocalUserLoader;
import com.ezcoding.account.module.user.service.IUserService;
import com.ezcoding.base.web.extend.spring.security.authentication.IUserLoadable;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2019-08-30 23:52
 */
@Configuration
public class ExtraConfig {

    @Bean
    public IUserLoadable localUserLoader(IUserService userService) {
        return new LocalUserLoader(userService);
    }

}
