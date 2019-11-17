package com.ezcoding.auth.module.resource.core.expression;

import com.google.common.base.Joiner;
import com.google.common.base.Splitter;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2019-10-06 23:27
 */
public class DefaultRoleExpressionMatcher implements IRoleExpressionMatchable {

    private static final String OR = "|";
//    private static final String AND = "&";

    @Override
    public boolean match(String expression, Collection<String> roles) {
        List<String> rs = new ArrayList<>(roles);
        rs.retainAll(Splitter.on(OR).splitToList(expression));
        return !rs.isEmpty();
    }

    @Override
    public String or(String srcExpression, Collection<String> roles) {
        String join = Joiner.on(OR).join(roles);
        return StringUtils.isEmpty(srcExpression) ? join : srcExpression + OR + join;
    }

    @Override
    public String and(String srcExpression, Collection<String> roles) {
//        String join = Joiner.on(AND).join(roles);
//        return StringUtils.isEmpty(srcExpression) ? join : srcExpression + AND + join;
        return srcExpression;
    }

}
