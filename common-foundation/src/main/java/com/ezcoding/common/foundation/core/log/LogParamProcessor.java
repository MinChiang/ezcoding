package com.ezcoding.common.foundation.core.log;

import java.lang.reflect.AnnotatedElement;
import java.util.ArrayList;
import java.util.List;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2018-08-04 14:30
 */
public class LogParamProcessor {

    private final LogConfig logConfig;
    private final AnnotatedElement annotatedElement;
    private final ParamLogMetadata paramLogMetadata;
    private final LogParser parser;

    LogParamProcessor(LogConfig logConfig,
                      AnnotatedElement annotatedElement) {
        this.logConfig = logConfig;
        this.annotatedElement = annotatedElement;

        //元数据初始化
        StandardLogParam standardLogParam = annotatedElement.getAnnotation(StandardLogParam.class);
        this.paramLogMetadata = new ParamLogMetadata(
                standardLogParam.expressions(),
                standardLogParam.parseClass()
        );
        this.parser = logConfig.acquireLogParser(paramLogMetadata.parseClass);
    }

    /**
     * 解析对应对象
     *
     * @param target 目标对象
     * @return 解析后的对象
     */
    public List<Object> parse(Object target) {
        List<Object> result = new ArrayList<>(this.paramLogMetadata.expressions.length);
        for (int i = 0; i < this.paramLogMetadata.expressions.length; i++) {
            result.add(this.parser.parse(this.paramLogMetadata.expressions[i], this.paramLogMetadata, target));
        }
        return result;
    }

    public ParamLogMetadata getParamLogMetadata() {
        return paramLogMetadata;
    }

    public LogParser getParser() {
        return parser;
    }

    public LogConfig getLogConfig() {
        return logConfig;
    }

    public AnnotatedElement getAnnotatedElement() {
        return annotatedElement;
    }

}
