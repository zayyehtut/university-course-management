package com.university.enrollment.api.dto;

public class GradeDTO {
    private String id;
    private String enrollmentId;
    private String letterGrade;
    private float numericGrade;

    public GradeDTO() {
        // Default constructor
    }

    public GradeDTO(String id, String enrollmentId, String letterGrade, float numericGrade) {
        this.id = id;
        this.enrollmentId = enrollmentId;
        this.letterGrade = letterGrade;
        this.numericGrade = numericGrade;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEnrollmentId() {
        return enrollmentId;
    }

    public void setEnrollmentId(String enrollmentId) {
        this.enrollmentId = enrollmentId;
    }

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
        
    @Override
    public String toString() {
        return "GradeDTO{" +
                "id='" + id + '\'' +
                ", enrollmentId='" + enrollmentId + '\'' +
                ", letterGrade='" + letterGrade + '\'' +
                ", numericGrade=" + numericGrade +
                '}';
    }
}
