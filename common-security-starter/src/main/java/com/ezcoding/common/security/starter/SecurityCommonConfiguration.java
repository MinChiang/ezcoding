package com.ezcoding.common.security.starter;

import com.ezcoding.common.web.SecurityUserLoader;
import com.ezcoding.common.web.starter.IApplicationWebConfigurer;
import com.ezcoding.common.core.user.IUserLoadable;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2020-04-03 17:54
 */
@Configuration
public class SecurityCommonConfiguration implements IApplicationWebConfigurer {

    @Override
    public void registerUserLoaders(List<IUserLoadable> loaders) {
        SecurityUserLoader securityUserLoader = new SecurityUserLoader();
        loaders.add(securityUserLoader);
    }

}
