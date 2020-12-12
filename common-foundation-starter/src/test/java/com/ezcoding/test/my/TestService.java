package com.ezcoding.test.my;

import com.ezcoding.common.foundation.core.log.StandardLog;
import com.ezcoding.common.foundation.core.log.StandardLogParam;
import org.springframework.stereotype.Component;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2020-12-12 11:14
 */
@Component
public class TestService {

    @StandardLogParam(expressions = "name")
    @StandardLog(beforeExpression = "hello, {}, your age is: {}", afterExpression = "good bye {}")
    public Person service(@StandardLogParam(expressions = {"name", "age"}) Person person) {
        return person;
    }

    public static class Person {

        private String name;
        private Integer age;

        public Person(String name, Integer age) {
            this.name = name;
            this.age = age;
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

    }

}
