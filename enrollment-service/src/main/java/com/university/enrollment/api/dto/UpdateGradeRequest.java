package com.university.enrollment.api.dto;

public class UpdateGradeRequest {
    private String letterGrade;
    private float numericGrade;

    // Constructors
    public UpdateGradeRequest() {
    }

    public UpdateGradeRequest(String letterGrade, float numericGrade) {
        this.letterGrade = letterGrade;
        this.numericGrade = numericGrade;
    }

    // Getters and Setters
    public String getLetterGrade() {
        return letterGrade;
    }

    public void setLetterGrade(String letterGrade) {
        this.letterGrade = letterGrade;
    }

    public float getNumericGrade() {
        return numericGrade;
    }

    public void setNumericGrade(float numericGrade) {
        this.numericGrade = numericGrade;
    }
}