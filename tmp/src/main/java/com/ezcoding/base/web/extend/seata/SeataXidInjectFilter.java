package com.ezcoding.web.filter;

import com.ezcoding.common.foundation.core.constant.GlobalConstants;
import io.seata.core.context.RootContext;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2019-08-29 9:54
 */
public class SeataXidInjectFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String shareXid = request.getHeader(GlobalConstants.Header.XID);
        if (StringUtils.isNotEmpty(shareXid)) {
            RootContext.bind(shareXid);
        }

        try {
            filterChain.doFilter(request, response);
        } finally {
            //解绑流程
            RootContext.unbind();
        }
    }

}
