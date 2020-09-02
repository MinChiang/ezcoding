package com.ezcoding.common.web.filter;

import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2018-10-24 15:36
 */
public class ApplicationContextHolderFilter extends OncePerRequestFilter {

    private final Collection<ApplicationContextValueFetchable> settings = new ArrayList<>(0);

    public ApplicationContextHolderFilter() {
    }

    public ApplicationContextHolderFilter(Collection<ApplicationContextValueFetchable> settings) {
        if (settings != null && !settings.isEmpty()) {
            this.settings.addAll(settings);
        }
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        ApplicationContextHolder instance = ApplicationContextHolder.getInstance();
        try {
            this.settings.forEach(setting ->
                    Optional
                            .ofNullable(setting.fetch(request, response))
                            .ifPresent(obj -> instance.put(setting.key(), obj))
            );
            filterChain.doFilter(request, response);
        } finally {
            //清除上下文中的所有对象，重要
            instance.clear();
        }
    }

}