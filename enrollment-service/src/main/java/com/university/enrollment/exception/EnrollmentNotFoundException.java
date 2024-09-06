package com.university.enrollment.exception;

public class EnrollmentNotFoundException extends RuntimeException {
    public EnrollmentNotFoundException(String id) {
        super("Enrollment not found with id: " + id);
    }
}