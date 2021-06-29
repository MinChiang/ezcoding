package com.test;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2021-06-28 14:28
 */
public class ScoreInfo {

    Long studentId;
    String studentName;
    Double score;
    String subject;

    public ScoreInfo(Long studentId, String studentName, Double score, String subject) {
        this.studentId = studentId;
        this.studentName = studentName;
        this.score = score;
        this.subject = subject;
    }

    @Override
    public String toString() {
        return "ScoreInfo{" +
                "studentId=" + studentId +
                ", studentName='" + studentName + '\'' +
                ", score=" + score +
                ", subject='" + subject + '\'' +
                '}';
    }

}
