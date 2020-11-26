package com.test;

import java.lang.ref.WeakReference;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2020-11-25 18:11
 */
public class Test {

    public static void main(String[] args) {
        Salad salad = new Salad(new Apple("红富士"));
        //通过WeakReference的get()方法获取Apple
        System.out.println("Apple:" + salad.get());
        System.gc();
        try {
            //休眠一下，在运行的时候加上虚拟机参数-XX:+PrintGCDetails，输出gc信息，确定gc发生了。
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        //如果为空，代表被回收了
        if (salad.get() == null) {
            System.out.println("clear Apple。");
        }
    }

    private static final class Salad extends WeakReference<Apple> {

        public Salad(Apple apple) {
            super(apple);
        }

    }

    private static final class Apple {

        private String name;

        public Apple(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        @Override
        protected void finalize() throws Throwable {
            super.finalize();
            System.out.println("Apple: " + name + " finalize");
        }

    }

}
