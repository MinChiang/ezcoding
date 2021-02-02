package com.test;

import com.ezcoding.common.foundation.util.AssembleUtils;
import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2020-12-25 14:28
 */
public class TestAssembleUtils {

    private enum Gender {

        FEMALE(0),

        MALE(1);

        public final int id;

        private static final Map<Integer, Gender> ALL = Arrays.stream(Gender.class.getEnumConstants()).collect(Collectors.toMap(value -> value.id, value -> value));

        /**
         * 转换
         *
         * @param id id
         * @return 对应类别
         */
        public static Gender from(int id) {
            return ALL.get(id);
        }

        Gender(int id) {
            this.id = id;
        }

    }

    private static class User {

        private String name;
        private Integer age;
        private Integer gender;
        private Long id;

        public User(String name, Integer age, Integer gender, Long id) {
            this.name = name;
            this.age = age;
            this.gender = gender;
            this.id = id;
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

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        @Override
        public String toString() {
            return "User{" +
                    "name='" + name + '\'' +
                    ", age=" + age +
                    ", gender=" + gender +
                    ", id=" + id +
                    '}';
        }

    }

    private static class UserDTO {

        private String name;
        private Integer age;
        private Gender gender;
        private String id;

        public UserDTO() {
        }

        public UserDTO(String name, Integer age, Gender gender, String id) {
            this.name = name;
            this.age = age;
            this.gender = gender;
            this.id = id;
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

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public Gender getGender() {
            return gender;
        }

        public void setGender(Gender gender) {
            this.gender = gender;
        }

        @Override
        public String toString() {
            return "UserDTO{" +
                    "name='" + name + '\'' +
                    ", age=" + age +
                    ", gender=" + gender +
                    ", id='" + id + '\'' +
                    '}';
        }

    }

    @Test
    public void test() {
        String name = "jm";
        Integer age = 27;
        Integer gender = 1;
        Long id = 15581L;

        User user = new User(name, age, gender, id);
        UserDTO userDTO = AssembleUtils
                .of(user, new UserDTO())
                .with(User::getAge, UserDTO::setAge)
                .with(User::getName, UserDTO::setName)
                .with(User::getGender, (d, g) -> d.setGender(Gender.from(g)))
                .with(u -> "G" + u.getId(), UserDTO::setId)
                .assemble();
        System.out.println(userDTO);

        Assert.assertEquals(userDTO.getName(), name);
        Assert.assertEquals(userDTO.getAge(), age);
        Assert.assertEquals(userDTO.getGender(), Gender.from(gender));
        Assert.assertEquals(userDTO.getId(), "G" + id);
    }

}
