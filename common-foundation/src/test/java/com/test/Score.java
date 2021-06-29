package com.test;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2021-06-28 10:50
 */
public class Score {

    Long studentId;
    Double score;
    String subject;

    public Score(Long studentId, Double score, String subject) {
        this.studentId = studentId;
        this.score = score;
        this.subject = subject;
    }

    @Override
    public String toString() {
        return "Score{" +
                "studentId=" + studentId +
                ", score=" + score +
                ", subject='" + subject + '\'' +
                '}';
    }

}
