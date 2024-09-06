package com.university.enrollment.api.dto;

public class CreateEnrollmentRequest {
    private String studentId;
    private String courseId;

    public CreateEnrollmentRequest() {
        // Default constructor
    }

    public CreateEnrollmentRequest(String studentId, String courseId) {
        this.studentId = studentId;
        this.courseId = courseId;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public String getCourseId() {
        return courseId;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }
}