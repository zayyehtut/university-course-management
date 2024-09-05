package com.university.course.exception;

public class DuplicateCourseCodeException extends RuntimeException {
    public DuplicateCourseCodeException(String code) {
        super("Course with code already exists: " + code);
    }
}