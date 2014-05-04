package com.tegon.facul.app;

import java.io.Serializable;

public class Grade implements Serializable {
    private String name;
    private Integer absences;
    private Float absencesPercentage;
    private Float firstGrade;
    private Float secondGrade;
    private Float average;
    private Float exam;
    private String status;

    public Grade(String name, Integer absences, Float absencesPercentage, Float firstGrade,
                 Float secondGrade, Float average, Float exam, String status, String situation) {
        this.name = name;
        this.absences = absences;
        this.absencesPercentage = absencesPercentage;
        this.firstGrade = firstGrade;
        this.secondGrade = secondGrade;
        this.average = average;
        this.exam = exam;
        this.status = status;
        this.situation = situation;
    }

    private String situation;

    public String getSituation() {
        return situation;
    }

    public void setSituation(String situation) {
        this.situation = situation;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAbsences() {
        return absences;
    }

    public void setAbsences(Integer absences) {
        this.absences = absences;
    }

    public Float getAbsencesPercentage() {
        return absencesPercentage;
    }

    public void setAbsencesPercentage(Float absencesPercentage) {
        this.absencesPercentage = absencesPercentage;
    }

    public Float getFirstGrade() {
        return firstGrade;
    }

    public void setFirstGrade(Float firstGrade) {
        this.firstGrade = firstGrade;
    }

    public Float getSecondGrade() {
        return secondGrade;
    }

    public void setSecondGrade(Float secondGrade) {
        this.secondGrade = secondGrade;
    }

    public Float getAverage() {
        return average;
    }

    public void setAverage(Float average) {
        this.average = average;
    }

    public Float getExam() {
        return exam;
    }

    public void setExam(Float exam) {
        this.exam = exam;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
