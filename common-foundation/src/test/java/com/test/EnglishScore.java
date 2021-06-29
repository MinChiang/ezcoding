package com.test;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2021-06-28 10:53
 */
public class EnglishScore extends Score {

    Double oralScore;
    Double writtenScore;

    public EnglishScore(Long studentId, Double score, String subject, Double oralScore, Double writtenScore) {
        super(studentId, score, subject);
        this.oralScore = oralScore;
        this.writtenScore = writtenScore;
    }

    @Override
    public String toString() {
        return "EnglishScore{" +
                "oralScore=" + oralScore +
                ", writtenScore=" + writtenScore +
                ", studentId=" + studentId +
                ", score=" + score +
                ", subject='" + subject + '\'' +
                '}';
    }

}
