package com.test;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2021-06-28 10:55
 */
public class Person {

    String name;

    public Person(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Person{" +
                "name='" + name + '\'' +
                '}';
    }

}
