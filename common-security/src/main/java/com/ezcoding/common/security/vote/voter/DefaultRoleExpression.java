package com.ezcoding.common.security.vote.voter;

import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;
import java.util.List;
import java.util.Stack;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2020-04-18 13:00
 */
public class DefaultRoleExpression implements IRoleExpression {

    private List<Object> expression;

    @Override
    public boolean match(Collection<? extends GrantedAuthority> authorities) {
        return false;
    }

    public DefaultRoleExpression(String originalExpression) {
        this.expression = this.parse(expression);
    }

    /**
     * 构建表达式
     * (admin) || test
     * (admin && test) || leader
     * admin || leader && test
     *
     * @param originalExpression 原生表达式
     * @return 输出的表达式
     */
    private List<Object> parse(String originalExpression) {
        String expression = StringUtils.deleteWhitespace(originalExpression);
        Stack<Object> stack = new Stack<>();

        boolean lastOr = false;
        boolean lastAnd = false;
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < expression.length(); i++) {
            char c = expression.charAt(i);
            switch (c) {
                case '(':
                    break;
                case ')':
                    break;
                case '|':
                    if (lastOr) {
                        lastOr = false;
                        stack.push(sb.capacity());
                        stack.push(OperatorEnum.OR);
                    } else {
                        lastOr = true;
                    }
                    break;
                case '&':
                    if (lastAnd) {
                        lastAnd = false;
                        stack.push(OperatorEnum.AND);
                    } else {
                        lastAnd = true;
                    }
                    break;
                default:
                    sb.append(c);
                    break;
            }
        }
    }

    private static enum OperatorEnum {

        /**
         * 无操作
         */
        NONE(null),

        /**
         * 与操作
         */
        AND("&&"),

        /**
         * 或操作
         */
        OR("||");

        private String operator;

        OperatorEnum(String operator) {
            this.operator = operator;
        }

        public String getOperator() {
            return operator;
        }

    }

}
