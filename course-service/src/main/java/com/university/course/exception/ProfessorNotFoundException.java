package com.university.course.exception;

public class ProfessorNotFoundException extends RuntimeException {
    public ProfessorNotFoundException(String id) {
        super("Professor not found with id: " + id);
    }
}