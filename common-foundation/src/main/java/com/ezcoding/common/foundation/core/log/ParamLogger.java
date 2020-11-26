package com.ezcoding.common.foundation.core.log;

import java.lang.reflect.AnnotatedElement;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2018-08-04 14:30
 */
public class ParamLogger {

    private final LogConfig logConfig;
    private final AnnotatedElement annotatedElement;

    private final ParamLogMetadata paramLogMetadata;
    private final LogParser parser;

    ParamLogger(LogConfig logConfig,
                AnnotatedElement annotatedElement) {
        this.logConfig = logConfig;
        this.annotatedElement = annotatedElement;

        //元数据初始化
        ParamLog paramLog = annotatedElement.getAnnotation(ParamLog.class);
        this.paramLogMetadata = new ParamLogMetadata(
                paramLog.expressions(),
                paramLog.parseClass()
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
        return Arrays
                .stream(this.paramLogMetadata.expressions)
                .map(exp -> this.parser.parse(this, target))
                .collect(Collectors.toList());
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
