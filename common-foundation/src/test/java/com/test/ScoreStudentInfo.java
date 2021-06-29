package com.test;

import com.ezcoding.common.foundation.util.StreamUtils;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2021-06-28 10:50
 */
public class ScoreStudentInfo {

    Score score;
    Student student;

    public ScoreStudentInfo(Score score, Student student) {
        this.score = score;
        this.student = student;
    }

    @Override
    public String toString() {
        return "ScoreStudentInfo{" +
                "score=" + score +
                ", student=" + student +
                '}';
    }

}
