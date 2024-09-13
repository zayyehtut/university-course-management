package com.university.student.util;

import com.university.common.util.ValidationUtils;
import com.university.student.api.dto.CreateStudentRequest;
import com.university.student.api.dto.UpdateStudentRequest;
import com.university.common.exception.ValidationException;

import java.util.ArrayList;
import java.util.List;

public class StudentValidationUtils {

    public static void validateCreateStudentRequest(CreateStudentRequest request) {
        ValidationUtils.validateNonEmptyString(request.getName(), "Name");
        ValidationUtils.validateEmail(request.getEmail());
        ValidationUtils.validateType(request.getType(), new String[]{"UNDERGRADUATE", "POSTGRADUATE"}, "Type");
        ValidationUtils.validateNonEmptyString(request.getAddress(), "Address");
        ValidationUtils.validatePhoneNumber(request.getPhoneNumber());
        ValidationUtils.validateDateInPast(request.getDateOfBirth(), "Date of birth");
    }

    public static void validateUpdateStudentRequest(UpdateStudentRequest request) {
        List<String> errors = new ArrayList<>();

        if (request.getName() != null) {
            ValidationUtils.validateNonEmptyString(request.getName(), "Name");
        }
        if (request.getEmail() != null) {
            ValidationUtils.validateEmail(request.getEmail());
        }
        if (request.getType() != null) {
            ValidationUtils.validateType(request.getType(), new String[]{"UNDERGRADUATE", "POSTGRADUATE"}, "Type");
        }
        if (request.getAddress() != null) {
            ValidationUtils.validateNonEmptyString(request.getAddress(), "Address");
        }
        if (request.getPhoneNumber() != null) {
            ValidationUtils.validatePhoneNumber(request.getPhoneNumber());
        }
        if (request.getDateOfBirth() != null) {
            ValidationUtils.validateDateInPast(request.getDateOfBirth(), "Date of birth");
        }

        if (!errors.isEmpty()) {
            throw new ValidationException(String.join(", ", errors));
        }
    }
}