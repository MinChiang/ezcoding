package com.ezcoding.common.foundation.core.lock.impl;

import com.ezcoding.common.foundation.core.lock.LockIdentification;
import com.ezcoding.common.foundation.core.lock.LockMetadata;
import com.ezcoding.common.foundation.core.lock.LockRuntime;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2021-02-07 10:30
 */
public class SpelLockIdentification implements LockIdentification {

    private static final ExpressionParser PARSER = new SpelExpressionParser();
    private static final SimpleLockIdentification DEFAULT = new SimpleLockIdentification();

    @Override
    public String identify(LockMetadata lockMetadata, LockRuntime lockRuntime) {
        StandardEvaluationContext ctx = new StandardEvaluationContext();
        if (lockRuntime.args != null && lockRuntime.args.length > 0) {
            for (int i = 0; i < lockRuntime.args.length; i++) {
                ctx.setVariable(lockRuntime.parameterNames[i], lockRuntime.args[i]);
            }
        }
        Object value = PARSER.parseExpression(lockMetadata.key).getValue(ctx);
        //若为空则使用默认的处理方法
        if (value == null) {
            return DEFAULT.identify(lockMetadata, lockRuntime);
        }
        return value.toString();
    }

}
