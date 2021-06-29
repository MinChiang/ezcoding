package com.test;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2021-06-28 11:01
 */
public class EnglishOralScoreInfo {

    Double oralScore;
    String studentName;

    public EnglishOralScoreInfo(Double oralScore, String studentName) {
        this.oralScore = oralScore;
        this.studentName = studentName;
    }

    @Override
    public String toString() {
        return "EnglishOralScoreInfo{" +
                "oralScore=" + oralScore +
                ", studentName='" + studentName + '\'' +
                '}';
    }

}
