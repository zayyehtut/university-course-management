// File: enrollment-service/src/main/java/com/university/enrollment/domain/model/Grade.java
package com.university.enrollment.domain.model;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "grades")
public class Grade {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "enrollment_id", nullable = false)
    private Enrollment enrollment;

    @Column(nullable = false)
    private String letterGrade;

    @Column(nullable = false)
    private float numericGrade;

    @Column(nullable = false)
    private LocalDate dateRecorded;

    // Constructors
    public Grade() {}

    public Grade(Enrollment enrollment, String letterGrade, float numericGrade, LocalDate dateRecorded) {
        this.enrollment = enrollment;
        this.letterGrade = letterGrade;
        this.numericGrade = numericGrade;
        this.dateRecorded = dateRecorded;
    }

    // Getters and setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Enrollment getEnrollment() {
        return enrollment;
    }

    public void setEnrollment(Enrollment enrollment) {
        this.enrollment = enrollment;
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

    // Helper method to get enrollmentId (useful for DTOs)
    public String getEnrollmentId() {
        return enrollment != null ? enrollment.getId() : null;
    }

    public LocalDate getDateRecorded() {
        return dateRecorded;
    }

    public void setDateRecorded(LocalDate dateRecorded) {
        this.dateRecorded = dateRecorded;
    }



    @Override
    public String toString() {
        return "Grade{" +
                "id='" + id + '\'' +
                ", enrollmentId='" + getEnrollmentId() + '\'' +
                ", letterGrade='" + letterGrade + '\'' +
                ", numericGrade=" + numericGrade +
                ", dateRecorded=" + dateRecorded +
                '}';
    }
}