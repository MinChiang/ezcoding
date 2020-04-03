package com.ezcoding.common.web;

import com.ezcoding.common.core.user.model.IUserIdentifiable;
import com.ezcoding.common.web.user.EmptyUserLoader;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2020-04-03 14:47
 */
public class SecurityUserLoader extends EmptyUserLoader {

    @Override
    public IUserIdentifiable load() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String code = authentication.getName();
        return new User(code);
    }

    private static class User implements IUserIdentifiable {

        private String code;

        public User(String code) {
            this.code = code;
        }

        @Override
        public String getCode() {
            return this.code;
        }

        @Override
        public String getAccount() {
            return null;
        }

        @Override
        public String getPhone() {
            return null;
        }

        @Override
        public String getEmail() {
            return null;
        }

    }

}
