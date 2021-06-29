package com.test;

import com.ezcoding.common.foundation.util.StreamUtils;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2021-06-28 10:51
 */
public class StreamUtilsTest {

    @Test
    public void associateTest() {
        List<Score> scores = new ArrayList<Score>() {{
            add(new EnglishScore(1L, 100D, "English", 0D, 100D));
            add(new EnglishScore(2L, 60D, "English", 30D, 30D));
            add(new EnglishScore(3L, 80D, "English", 40D, 40D));
            add(new EnglishScore(4L, 0D, "English", 0D, 0D));
            add(new Score(1L, 100D, "Chinese"));
            add(new Score(2L, 20D, "Chinese"));
            add(new Score(5L, 80D, "Chinese"));
        }};

        List<Student> students = new ArrayList<Student>() {{
            add(new Student(1L, "jiangmin"));
            add(new Student(2L, "zhangbiyan"));
            add(new Student(3L, "wenbaoyi"));
            add(new Student(5L, "liangjunli"));
        }};

        Map<Long, Student> mapping = students.stream().collect(Collectors.toMap(student -> student.id, Function.identity()));

        List<ScoreInfo> associate = StreamUtils.associateList(
                scores,
                score -> score.studentId,
                ids -> {
                    Map<Long, Student> map = new HashMap<>();
                    for (Long id : ids) {
                        Student student = mapping.get(id);
                        map.put(id, student);
                    }
                    return map;
                },
                (score, student) -> {
                    if (student == null) {
                        return new ScoreInfo(null, null, score.score, score.subject);
                    }
                    return new ScoreInfo(student.id, student.name, score.score, score.subject);
                });

        System.out.println(associate);
    }

}
