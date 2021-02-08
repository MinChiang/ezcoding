package com.ezcoding.test;

import org.junit.Test;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2021-02-08 11:27
 */
public class TestSpel {

    @Test
    public void testSpel() {
        ExpressionParser parser = new SpelExpressionParser();
        Expression expression = parser.parseExpression("'test' + #id");

        EvaluationContext ctx = new StandardEvaluationContext();
        ctx.setVariable("id", "1");

        String value = expression.getValue(ctx, String.class);
        System.out.println(value);
    }

}
