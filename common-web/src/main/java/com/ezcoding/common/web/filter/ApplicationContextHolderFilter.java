package com.ezcoding.common.web.filter;

import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2018-10-24 15:36
 */
public class ApplicationContextHolderFilter extends OncePerRequestFilter {

    private Collection<IApplicationContextValueFetchable> settings = new ArrayList<>(0);

    public ApplicationContextHolderFilter() {
    }

    public ApplicationContextHolderFilter(Collection<IApplicationContextValueFetchable> settings) {
        this.settings = settings;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        ApplicationContextHolder instance = ApplicationContextHolder.getInstance();
        try {
            for (IApplicationContextValueFetchable setting : settings) {
                Object fetch = setting.fetch(request, response);
                if (fetch != null) {
                    instance.put(setting.key(), fetch);
                }
            }
            filterChain.doFilter(request, response);
        } finally {
            //清除上下文中的所有对象，重要
            instance.clear();
        }
    }

}