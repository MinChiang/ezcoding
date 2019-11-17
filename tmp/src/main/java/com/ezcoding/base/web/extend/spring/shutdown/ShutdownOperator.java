package com.ezcoding.base.web.extend.spring.shutdown;

import org.apache.catalina.connector.Connector;
import org.springframework.boot.web.embedded.tomcat.TomcatConnectorCustomizer;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextClosedEvent;

import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2018-12-19 10:09
 */
public class ShutdownOperator implements TomcatConnectorCustomizer, ApplicationListener<ContextClosedEvent> {

    private final int WAIT_TIME = 30;

    private volatile Connector connector;

    @Override
    public void customize(Connector connector) {
        this.connector = connector;
    }

    @Override
    public void onApplicationEvent(ContextClosedEvent contextClosedEvent) {
        System.out.println("--------------------开始停止应用--------------------");
        this.connector.pause();
        Executor executor = this.connector.getProtocolHandler().getExecutor();
        if (executor instanceof ThreadPoolExecutor) {
            try {
                ThreadPoolExecutor threadPoolExecutor = (ThreadPoolExecutor) executor;
                threadPoolExecutor.shutdown();
                if (!threadPoolExecutor.awaitTermination(WAIT_TIME, TimeUnit.SECONDS)) {
                    System.out.println("--------------------" + WAIT_TIME + "秒内无法停止应用，请手动停止--------------------");
                }
            } catch (InterruptedException ex) {
                Thread.currentThread().interrupt();
            }
        }
        System.out.println("--------------------应用停止完毕--------------------");
    }

}
