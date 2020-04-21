package com.ezcoding.common.security.vote.voter;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Stack;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2020-04-18 13:00
 */
public class ExpressionMatcher implements IRoleExpression {

    /**
     * 执行内容
     */
    private List<Object> execution;

    public ExpressionMatcher(String originalExpression) {
        this.execution = parse(originalExpression);
    }

    /**
     * 将对应的中序表达式转换为后续表达式
     * admin || develop && test                     ==> admin develop || test &&
     * admin || (develop && test)                   ==> admin develop test && ||
     * test || (develop && (admin || manager))      ==> test develop admin manager || && ||
     *
     * @param originalExpression 需要解析的内容
     * @return 解析后的执行内容
     */
    private List<Object> parse(String originalExpression) {
        String expression = StringUtils.deleteWhitespace(originalExpression);

        List<Object> result = new LinkedList<>();
        Stack<Object> expressionStack = new Stack<>();
        boolean lastAndFlag = false;
        boolean lastOrFlag = false;
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < expression.length(); i++) {
            char c = expression.charAt(i);
            switch (c) {
                case '&':
                    if (lastAndFlag) {
                        lastAndFlag = false;
                        //处理之前已经存在的字符串表达式
                        if (sb.length() != 0) {
                            result.add(sb.toString());
                            sb = new StringBuilder();
                        }

                        while (!expressionStack.isEmpty()) {
                            CalculateRegularEnum peek = (CalculateRegularEnum) expressionStack.peek();
                            if (peek == CalculateRegularEnum.OPEN || peek.priority < CalculateRegularEnum.AND.priority) {
                                break;
                            }
                            result.add(expressionStack.pop());
                        }
                        expressionStack.push(CalculateRegularEnum.AND);
                    } else {
                        lastAndFlag = true;
                    }
                    break;
                case '|':
                    if (lastOrFlag) {
                        lastOrFlag = false;
                        //处理之前已经存在的字符串表达式
                        if (sb.length() != 0) {
                            result.add(sb.toString());
                            sb = new StringBuilder();
                        }

                        while (!expressionStack.isEmpty()) {
                            CalculateRegularEnum peek = (CalculateRegularEnum) expressionStack.peek();
                            if (peek == CalculateRegularEnum.OPEN || peek.priority < CalculateRegularEnum.OR.priority) {
                                break;
                            }
                            result.add(expressionStack.pop());
                        }
                        expressionStack.push(CalculateRegularEnum.OR);
                    } else {
                        lastOrFlag = true;
                    }
                    break;
                case '(':
                    expressionStack.push(CalculateRegularEnum.OPEN);
                    break;
                case ')':
                    if (sb.length() != 0) {
                        result.add(sb.toString());
                        sb = new StringBuilder();
                    }

                    Object pop;
                    while ((pop = expressionStack.pop()) != CalculateRegularEnum.OPEN) {
                        result.add(pop);
                    }
                    break;
                default:
                    //普通表达式字符串
                    sb.append(c);
                    break;
            }
        }

        if (sb.length() != 0) {
            result.add(sb.toString());
        }
        while (!expressionStack.isEmpty()) {
            result.add(expressionStack.pop());
        }

        return result;
    }

    @Override
    public boolean match(Collection<? extends GrantedAuthority> authorities) {
        if (CollectionUtils.isEmpty(this.execution)) {
            return true;
        }

        List<String> auths = convert(authorities);
        Stack<Object> calcStack = new Stack<>();
        for (Object o : this.execution) {
            if (o instanceof String) {
                calcStack.push(auths.contains(o));
            } else if (o instanceof CalculateRegularEnum) {
                boolean p1 = (boolean) calcStack.pop();
                boolean p2 = (boolean) calcStack.pop();
                switch ((CalculateRegularEnum) o) {
                    case OR:
                        calcStack.push(p1 || p2);
                        break;
                    case AND:
                        calcStack.push(p1 && p2);
                        break;
                    default:
                        break;
                }
            }
        }
        return (boolean) calcStack.pop();
    }

    /**
     * 将权限转换为对应的字符串列表
     *
     * @param authorities 需要转换的权限内容
     * @return 转换后的权限列表
     */
    private List<String> convert(Collection<? extends GrantedAuthority> authorities) {
        List<String> result = new LinkedList<>();
        authorities.forEach(authority -> result.add(authority.getAuthority()));
        return result;
    }

    public static void main(String[] args) {
        ExpressionMatcher expressionMatcher = new ExpressionMatcher("test || (develop && (admin || manager))");
        System.out.println(expressionMatcher.execution);
        List<SimpleGrantedAuthority> collect = Stream.of("test", "admin").map(SimpleGrantedAuthority::new).collect(Collectors.toList());
        boolean match = expressionMatcher.match(collect);
        System.out.println(match);
    }

    private enum CalculateRegularEnum {

        /**
         * 与操作
         */
        AND("&&", 0),

        /**
         * 或操作
         */
        OR("||", 0),

        /**
         * 左括号
         */
        OPEN("(", 10),

        /**
         * 右括号
         */
        CLOSE(")", 10);

        /**
         * 表达式
         */
        public final String expression;

        /**
         * 优先级
         */
        public final int priority;

        CalculateRegularEnum(String expression, int priority) {
            this.expression = expression;
            this.priority = priority;
        }

    }

}
