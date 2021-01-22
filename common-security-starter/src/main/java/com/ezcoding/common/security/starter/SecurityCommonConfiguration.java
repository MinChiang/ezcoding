package com.ezcoding.common.security.starter;

import com.ezcoding.common.core.user.UserLoadable;
import com.ezcoding.common.web.LocalSecurityUserLoader;
import com.ezcoding.common.web.starter.ApplicationWebConfigurer;
import com.ezcoding.common.web.starter.WebCommonConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2020-04-03 17:54
 */
@Configuration
@ConditionalOnBean({WebCommonConfiguration.class})
public class SecurityCommonConfiguration implements ApplicationWebConfigurer {

    @Override
    public void registerUserLoaders(List<UserLoadable> loaders) {
        LocalSecurityUserLoader localSecurityUserLoader = new LocalSecurityUserLoader();
        loaders.add(localSecurityUserLoader);
    }

}
