package com.test;

import com.fasterxml.jackson.annotation.JsonValue;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import org.junit.Test;

import java.awt.image.SampleModel;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2020-01-14 14:55
 */
public class TestObjectMapper {

    @Test
    public void test() throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        Student student = new Student();
        student.setGender(Gender.MALE);
        student.setName("jm");
        String s = objectMapper.writeValueAsString(student);
        System.out.println(s);
    }

    class Student {

        private Gender gender;

        private String name;

        public Gender getGender() {
            return gender;
        }

        public void setGender(Gender gender) {
            this.gender = gender;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

    }


    enum  Gender {

        FEMALE(0),

        MALE(1);

        @JsonValue
        private final int id;

        Gender(int id) {
            this.id = id;
        }

        public int getId() {
            return id;
        }

    }

}
