package com.university.student.util;

import com.university.common.dto.EnrollmentDTO;
import com.university.student.api.dto.AcademicRecordDTO;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class AcademicRecordUtils {

    public static List<AcademicRecordDTO.EnrollmentRecordDTO> fetchEnrollmentRecords(String studentId, RestTemplate restTemplate) {
        String enrollmentServiceUrl = "http://localhost:8082/api/enrollments/student/" + studentId;
        try {
            EnrollmentDTO[] enrollments = restTemplate.getForObject(enrollmentServiceUrl, EnrollmentDTO[].class);
            if (enrollments != null) {
                return Arrays.stream(enrollments)
                        .map(AcademicRecordUtils::convertToEnrollmentRecordDTO)
                        .collect(Collectors.toList());
            }
        } catch (Exception e) {
            // Log the error
        }
        return Collections.emptyList();
    }

    public static AcademicRecordDTO.EnrollmentRecordDTO convertToEnrollmentRecordDTO(EnrollmentDTO enrollmentDTO) {
        AcademicRecordDTO.EnrollmentRecordDTO recordDTO = new AcademicRecordDTO.EnrollmentRecordDTO();
        recordDTO.setCourseId(enrollmentDTO.getCourseId());
        recordDTO.setCourseName(enrollmentDTO.getCourseName());
        recordDTO.setGrade(enrollmentDTO.getGrade());
        recordDTO.setSemester(enrollmentDTO.getSemester());
        return recordDTO;
    }

    public static double calculateGPA(List<String> grades) {
        if (grades.isEmpty()) {
            return 0.0;
        }
        double totalGradePoints = grades.stream()
                .filter(grade -> grade != null && !grade.isEmpty())
                .mapToDouble(AcademicRecordUtils::convertGradeToPoints)
                .sum();
        long validGradeCount = grades.stream()
                .filter(grade -> grade != null && !grade.isEmpty())
                .count();
        return validGradeCount > 0 ? totalGradePoints / validGradeCount : 0.0;
    }

    private static double convertGradeToPoints(String grade) {
        switch (grade.toUpperCase()) {
            case "A": return 4.0;
            case "B": return 3.0;
            case "C": return 2.0;
            case "D": return 1.0;
            default: return 0.0;
        }
    }
}