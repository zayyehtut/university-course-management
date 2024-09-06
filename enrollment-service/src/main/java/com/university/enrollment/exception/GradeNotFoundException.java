package com.university.enrollment.exception;

public class GradeNotFoundException extends RuntimeException {
    public GradeNotFoundException(String id) {
        super("Grade not found with id: " + id);
    }
}