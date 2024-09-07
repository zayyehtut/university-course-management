package com.university.student.api.dto;

import java.util.List;

public class AcademicRecordDTO {
    private String studentId;
    private String studentName;
    private List<EnrollmentRecordDTO> enrollments;
    private double gpa;

    // Constructors
    public AcademicRecordDTO() {}

    public AcademicRecordDTO(String studentId, String studentName, List<EnrollmentRecordDTO> enrollments, double gpa) {
        this.studentId = studentId;
        this.studentName = studentName;
        this.enrollments = enrollments;
        this.gpa = gpa;
    }

    // Getters and setters
    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public List<EnrollmentRecordDTO> getEnrollments() {
        return enrollments;
    }

    public void setEnrollments(List<EnrollmentRecordDTO> enrollments) {
        this.enrollments = enrollments;
    }

    public double getGpa() {
        return gpa;
    }

    public void setGpa(double gpa) {
        this.gpa = gpa;
    }

    @Override
    public String toString() {
        return "AcademicRecordDTO{" +
                "studentId='" + studentId + '\'' +
                ", studentName='" + studentName + '\'' +
                ", enrollments=" + enrollments +
                ", gpa=" + gpa +
                '}';
    }

    public static class EnrollmentRecordDTO {
        private String courseId;
        private String courseName;
        private String grade;
        private String semester;

        // Constructors
        public EnrollmentRecordDTO() {}

        public EnrollmentRecordDTO(String courseId, String courseName, String grade, String semester) {
            this.courseId = courseId;
            this.courseName = courseName;
            this.grade = grade;
            this.semester = semester;
        }

        // Getters and setters
        public String getCourseId() {
            return courseId;
        }

        public void setCourseId(String courseId) {
            this.courseId = courseId;
        }

        public String getCourseName() {
            return courseName;
        }

        public void setCourseName(String courseName) {
            this.courseName = courseName;
        }

        public String getGrade() {
            return grade;
        }

        public void setGrade(String grade) {
            this.grade = grade;
        }

        public String getSemester() {
            return semester;
        }

        public void setSemester(String semester) {
            this.semester = semester;
        }

        @Override
        public String toString() {
            return "EnrollmentRecordDTO{" +
                    "courseId='" + courseId + '\'' +
                    ", courseName='" + courseName + '\'' +
                    ", grade='" + grade + '\'' +
                    ", semester='" + semester + '\'' +
                    '}';
        }
    }
}