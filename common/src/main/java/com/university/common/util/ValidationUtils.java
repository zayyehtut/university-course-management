package com.university.common.util;

import com.university.common.exception.ValidationException;

import java.time.LocalDate;
import java.util.Arrays;

public class ValidationUtils {

    public static void validateEmail(String email) {
        if (email == null || !email.matches("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$")) {
            throw new ValidationException("Valid email is required");
        }
    }

    public static void validatePhoneNumber(String phoneNumber) {
        if (phoneNumber == null || !phoneNumber.matches("\\d{10}")) {
            throw new ValidationException("Phone number must be 10 digits");
        }
    }

    public static void validateNonEmptyString(String value, String fieldName) {
        if (value == null || value.trim().isEmpty()) {
            throw new ValidationException(fieldName + " is required");
        }
    }

    public static void validateDateInPast(LocalDate date, String fieldName) {
        if (date == null || date.isAfter(LocalDate.now())) {
            throw new ValidationException("Valid " + fieldName + " is required and must be in the past");
        }
    }

    public static void validateType(String type, String[] validTypes, String fieldName) {
        if (type == null || !Arrays.asList(validTypes).contains(type)) {
            throw new ValidationException(fieldName + " must be one of " + String.join(", ", validTypes));
        }
    }
}