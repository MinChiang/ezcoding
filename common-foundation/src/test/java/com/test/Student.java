package com.test;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2021-06-28 10:50
 */
public class Student extends Person {

    Long id;

    public Student(Long id, String name) {
        super(name);
        this.id = id;
    }

    @Override
    public String toString() {
        return "Student{" +
                "name='" + name + '\'' +
                ", id=" + id +
                '}';
    }

}
