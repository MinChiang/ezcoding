package com.ezcoding.common.web.aspect;

import com.ezcoding.common.web.aspect.impl.ParamLogInfo;
import com.ezcoding.common.web.aspect.impl.ServiceLogInfo;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;

import java.util.Collections;
import java.util.List;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2018-08-04 14:10
 */
public abstract class AbstractSpelLogger implements ServiceLogger {

    private static final ExpressionParser PARSER = new SpelExpressionParser();

    @Override
    public void logBefore(Object target, ServiceLogInfo serviceLogInfo, List<ParamLogInfo> paramLogInfos) {
        EvaluationContext context = new StandardEvaluationContext();
        //循环解析参数
        for (ParamLogInfo arg : paramLogInfos) {
            doSetContextVariable(context, arg);
        }
        Object value = PARSER.parseExpression(serviceLogInfo.getExpression()).getValue(context);
        doLog(target, serviceLogInfo, paramLogInfos, value);
    }

    @Override
    public void logAfter(Object target, ServiceLogInfo serviceLogInfo, ParamLogInfo resultLogInfo) {
        EvaluationContext context = new StandardEvaluationContext();
        doSetContextVariable(context, resultLogInfo);
        Object value = PARSER.parseExpression(serviceLogInfo.getExpression()).getValue(context);
        doLog(target, serviceLogInfo, Collections.singletonList(resultLogInfo), value);
    }

    /**
     * 设置context总的数值
     *
     * @param context 上下文呢
     * @param arg     设置参数
     */
    private void doSetContextVariable(EvaluationContext context, ParamLogInfo arg) {
        for (int i = 0; i < arg.getExpression().length; i++) {
            String exp = arg.getExpression()[i];
            String name = arg.getName()[i];
            if (exp == null || exp.isEmpty()) {
                context.setVariable(name, arg.getOrginalTarget());
            } else {
                Object value = PARSER.parseExpression(exp).getValue(arg.getOrginalTarget());
                context.setVariable(name, value);
            }
        }
    }

    /**
     * 记录日志
     *
     * @param target         目标对象
     * @param serviceLogInfo 日志信息
     * @param paramLogInfos  参数信息
     * @param message        记录内容
     */
    protected abstract void doLog(Object target, ServiceLogInfo serviceLogInfo, List<ParamLogInfo> paramLogInfos, Object message);

}
