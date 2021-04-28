package com.ezcoding.test;

import org.junit.Test;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;

import java.time.LocalTime;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2021-02-08 11:27
 */
public class TestSpel {

    protected static class DefaultThreadFactory implements ThreadFactory {

        private static final AtomicLong POOL_NUMBER = new AtomicLong(1);
        private final AtomicLong threadNumber = new AtomicLong(1);
        private final ThreadGroup group;
        private final String namePrefix;

        DefaultThreadFactory() {
            group = new ThreadGroup(DefaultThreadFactory.class.getSimpleName());
            namePrefix = "pool-" + POOL_NUMBER.getAndIncrement() + "-thread-";
        }

        @Override
        public Thread newThread(Runnable runnable) {
            Thread t = new Thread(group, runnable, namePrefix + threadNumber.getAndIncrement(), 0);
            t.setDaemon(false);
            t.setPriority(Thread.MIN_PRIORITY);
            return t;
        }

    }

    private static final Executor executor = Executors.newFixedThreadPool(3, new DefaultThreadFactory());

    @Test
    public void testSpel() {
        ExpressionParser parser = new SpelExpressionParser();
        Expression expression = parser.parseExpression("'test' + #id");

        EvaluationContext ctx = new StandardEvaluationContext();
        ctx.setVariable("id", "1");

        String value = expression.getValue(ctx, String.class);
        System.out.println(value);
    }

    @Test
    public void testCompleteableFuture() {
        CompletableFuture<Void> voidCompletableFuture = CompletableFuture
                .runAsync(() -> performTask("first stage"), executor)
                .thenRun(() -> performTask("second stage"))
                .thenRunAsync(() -> performTask("third stage"), executor);

        System.out.println("main thread running! time"  + LocalTime.now());
        voidCompletableFuture.join();
    }

    private static void performTask(String stage) {
        System.out.println("---------");
        System.out.printf("stage: %s, time before task: %s, thread: %s%n",
                stage, LocalTime.now(), Thread.currentThread().getName());
        try {
            //simulating long task
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.printf("stage: %s, time after task: %s, thread: %s%n",
                stage, LocalTime.now(), Thread.currentThread().getName());
    }

}
