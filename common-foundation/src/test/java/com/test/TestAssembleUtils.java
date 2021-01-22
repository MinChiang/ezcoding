package com.test;

import com.ezcoding.common.foundation.util.AssembleUtils;
import org.junit.Test;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2020-12-25 14:28
 */
public class TestAssembleUtils {

    private static class User {

        private String name;
        private Integer age;
        private Integer gender;

        public User() {
        }

        public User(String name, Integer age, Integer gender) {
            this.name = name;
            this.age = age;
            this.gender = gender;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Integer getAge() {
            return age;
        }

        public void setAge(Integer age) {
            this.age = age;
        }

        public Integer getGender() {
            return gender;
        }

        public void setGender(Integer gender) {
            this.gender = gender;
        }

    }

    private static class UserDTO {

        private String name;
        private Integer age;
        private Integer gender;

        public UserDTO() {
        }

        public UserDTO(String name, Integer age, Integer gender) {
            this.name = name;
            this.age = age;
            this.gender = gender;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Integer getAge() {
            return age;
        }

        public void setAge(Integer age) {
            this.age = age;
        }

        public Integer getGender() {
            return gender;
        }

        public void setGender(Integer gender) {
            this.gender = gender;
        }

        @Override
        public String toString() {
            return "UserDTO{" +
                    "name='" + name + '\'' +
                    ", age=" + age +
                    ", gender=" + gender +
                    '}';
        }

    }

    @Test
    public void test() {
        User user = new User("jm", 27, 1);
        UserDTO assemble = AssembleUtils
                .instance(user, new UserDTO())
                .map(User::getAge, UserDTO::setAge)
                .map(User::getName, UserDTO::setName)
                .map(User::getGender, UserDTO::setGender)
                .assemble();
        System.out.println(assemble);

    }

}
