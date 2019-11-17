package com.ezcoding.base.web.extend.spring.aspect;

import com.ezcoding.base.web.extend.spring.aspect.impl.ParamLogInfo;
import com.ezcoding.base.web.extend.spring.aspect.impl.ServiceLogInfo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.annotation.AnnotationConfigurationException;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;

import java.util.List;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2018-08-04 14:10
 */
public abstract class AbstractSpelLogger implements IServiceLogger {

    @Override
    public void logBefore(Object target, ServiceLogInfo serviceLogInfo, List<ParamLogInfo> paramLogInfos) {
        log(target, serviceLogInfo, paramLogInfos, null);
    }

    @Override
    public void logAfter(Object target, ServiceLogInfo serviceLogInfo, List<ParamLogInfo> paramLogInfos, ParamLogInfo resultLogInfo) {
        log(target, serviceLogInfo, paramLogInfos, resultLogInfo);
    }

    private void log(Object target, ServiceLogInfo serviceLogInfo, List<ParamLogInfo> paramLogInfos, ParamLogInfo resultLogInfo) {
        ExpressionParser parser = new SpelExpressionParser();
        EvaluationContext context = new StandardEvaluationContext();

        //循环解析参数
        for (ParamLogInfo arg : paramLogInfos) {
            //校验name长度与expression长度需要相等
            if (arg.getName().length != arg.getExpression().length) {
                throw new AnnotationConfigurationException(target.getClass() + "中" + serviceLogInfo.getTarget().getName() + "方法注解name与expression的长度不一致");
            }

            for (int i = 0; i < arg.getExpression().length; i++) {
                String exp = arg.getExpression()[i];
                String name = arg.getName()[i];
                if (StringUtils.isEmpty(exp)) {
                    context.setVariable(name, arg.getObj());
                } else {
                    Object value = parser.parseExpression(exp).getValue(arg.getObj());
                    context.setVariable(name, value);
                }
            }
        }

        //加入返回参数
        if (resultLogInfo != null) {
            for (int i = 0; i < resultLogInfo.getExpression().length; i++) {
                String exp = resultLogInfo.getExpression()[i];
                String name = resultLogInfo.getName()[i];
                if (StringUtils.isEmpty(exp)) {
                    context.setVariable(name, resultLogInfo.getObj());
                } else {
                    Object value = parser.parseExpression(exp).getValue(resultLogInfo.getObj());
                    context.setVariable(name, value);
                }
            }
        }

        Object value = parser.parseExpression(serviceLogInfo.getExpression()).getValue(context);
        doLog(target, serviceLogInfo, paramLogInfos, resultLogInfo, value);
    }

    /**
     * 记录日志
     *
     * @param target         目标对象
     * @param serviceLogInfo 日志信息
     * @param paramLogInfos  参数信息
     * @param resultLogInfo  结果信息
     * @param message        记录内容
     */
    protected abstract void doLog(Object target, ServiceLogInfo serviceLogInfo, List<ParamLogInfo> paramLogInfos, ParamLogInfo resultLogInfo, Object message);
}
